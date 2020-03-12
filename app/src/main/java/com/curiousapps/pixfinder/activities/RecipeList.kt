package com.curiousapps.pixfinder.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.curiousapps.pixfinder.R
import com.curiousapps.pixfinder.data.AppController
import com.curiousapps.pixfinder.data.RecipeListAdapter
import com.curiousapps.pixfinder.model.*
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class RecipeList : AppCompatActivity() {

    private lateinit var urlJet: String
    private var volleyRequest: RequestQueue? = null
    private var recipeArray: ArrayList<Recipe>? = null
    private var mRecipeAdapter: RecipeListAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        val url: String?
        val extras = intent.extras
        val mPhotoTags = extras?.get("photoTags")
        val mPhotoSearch = extras?.get("photoSearch")
        if (extras != null && !mPhotoTags?.equals("")!!
            && !mPhotoSearch?.equals("")!!
        ) {

            val tempUrl = LEFT_URL + KEY + QUERY + mPhotoTags + IMAGE_TYPE + mPhotoSearch

            url = tempUrl
        } else {
            url = urlPix
        }
        volleyRequest = Volley.newRequestQueue(this)

        getRecipe(url)
        if (!isOnline()) {
            Toast.makeText(
                this, "This device is not connected to a network", Toast.LENGTH_LONG
            ).show()
        } else {
            checkCache(url)
        }
        checkCache(url)
    }

    private fun checkCache(Url: String) {
        val extras = intent.extras
        val mPhotoTags = extras?.get("photoTags")
        val mPhotoSearch = extras?.get("photoSearch")
        if (extras != null && !mPhotoTags?.equals("")!!
            && !mPhotoSearch?.equals("")!!
        ) {
            val tempUrl = LEFT_URL + KEY + QUERY + mPhotoTags + IMAGE_TYPE + mPhotoSearch

            urlJet = tempUrl
        } else {
            urlJet = urlPix
        }

        val cache: Cache? = AppController.instance?.requestQueue?.cache
        val imageLoader = AppController.instance?.imageLoader
        val entry = cache?.get(urlJet);
        if (entry != null) {
            try {
                val data = entry.data.let { String(entry.data) }
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace();
            }
        } else {
            getRecipe(urlJet)
            // Cached response doesn't exists. Make network call here
        }

    }

    private fun getRecipe(Url: String) {
        recipeArray = ArrayList<Recipe>()
        mRecipeAdapter = RecipeListAdapter(recipeArray!!, this)
        mLayoutManager = LinearLayoutManager(this)

        val recipeRequest = JsonObjectRequest(Request.Method.GET, Url, null,
            Response.Listener { response: JSONObject ->
                try {

                    val resultArray: JSONArray = response.getJSONArray("hits")
                    for (i in 0 until resultArray.length()) {
                        val recipeObj: JSONObject = resultArray.optJSONObject(i)

                        val title: String = recipeObj.getString("user")
                        val ingredients: String = recipeObj.getString("tags")
                        val thumbnail: String = recipeObj.getString("webformatURL")
                        val linkUrl: String = recipeObj.getString("pageURL")

                        Log.d("<===Result===>", title)
                        Log.d("<***Tags***>", ingredients)

                        val recipe = Recipe()
                        recipe.mTitle = "Photographer: \n$title"
                        recipe.mIngredients = "Photo Tags: $ingredients"
                        recipe.mThumbnail = thumbnail
                        recipe.mLinkAddress = linkUrl

                        //val recipeArray1 = recipeArray
                        recipeArray?.add(recipe)
                        setupRecyclerView()
                    }

                    mRecipeAdapter!!.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                try {
                    Log.d("**Volley Error**", error.toString())
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            })

        volleyRequest?.add(recipeRequest)
    }

    private fun setupRecyclerView() {
        recipeRecyclerView.layoutManager = mLayoutManager
        recipeRecyclerView.adapter = mRecipeAdapter
    }

    private fun isOnline(): Boolean {
        val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}
