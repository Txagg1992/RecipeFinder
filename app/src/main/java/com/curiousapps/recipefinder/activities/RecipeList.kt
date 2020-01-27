package com.curiousapps.recipefinder.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.curiousapps.recipefinder.R
import com.curiousapps.recipefinder.data.RecipeListAdapter
import com.curiousapps.recipefinder.model.Recipe
import com.curiousapps.recipefinder.model.url
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private var recipeArray: ArrayList<Recipe>? = null
    private var mRecipeAdapter: RecipeListAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        volleyRequest = Volley.newRequestQueue(this)

        //getRecipe(recipeUrl)
        getRecipe(url)
    }

    private fun getRecipe(Url: String){

        recipeArray = ArrayList<Recipe>()
        mRecipeAdapter = RecipeListAdapter(recipeArray!!, this)
        mLayoutManager = LinearLayoutManager(this)

        val recipeRequest =JsonObjectRequest(Request.Method.GET, Url, null,
            Response.Listener {response: JSONObject ->
                try {

                    val resultArray: JSONArray = response.getJSONArray("hits")
                    for (i in 0 until resultArray.length()){
                        val recipeObj: JSONObject = resultArray.optJSONObject(i)

                        var title: String = recipeObj.getString("user")
                        var ingredients: String = recipeObj.getString("tags")
                        var thumbnail: String = recipeObj.getString("webformatURL")
                        var linkUrl: String = recipeObj.getString("pageURL")

                        Log.d("<===Result===>", title)
                        Log.d("<***Tags***>", ingredients)

                        var recipe = Recipe()
                        recipe.mTitle = title
                        recipe.mIngredients = "Photo Tags: " + ingredients
                        recipe.mThumbnail = thumbnail
                        recipe.mLinkAddress = linkUrl

                        //val recipeArray1 = recipeArray
                        recipeArray?.add(recipe)
                        setupRecyclerView()
                    }

                    mRecipeAdapter!!.notifyDataSetChanged()

                }catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {error: VolleyError ->
                try {
                    Log.d("**Volley Error**", error.toString())
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            })

        volleyRequest?.add(recipeRequest)
    }

    private fun setupRecyclerView(){
        recipeRecyclerView.layoutManager = mLayoutManager
        recipeRecyclerView.adapter = mRecipeAdapter
    }
}
