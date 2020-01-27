package com.example.recipefinder.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.recipefinder.R
import com.example.recipefinder.model.Recipe
import com.example.recipefinder.model.recipeUrl
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RecipeList : AppCompatActivity() {

    private var volleyRequest: RequestQueue? = null
    private var recipeArray: ArrayList<Recipe>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        volleyRequest = Volley.newRequestQueue(this)

        getRecipe(recipeUrl)
        //getRecipe(url)
    }

    private fun getRecipe(Url: String){
        val recipeRequest =JsonObjectRequest(Request.Method.GET, Url, null,
            Response.Listener {response: JSONObject ->
                try {

//                    val jsonArray: JSONArray = response.getJSONArray("hits")
//                    for (i in 0 until jsonArray.length()) {
//                        val hit: JSONObject = jsonArray.optJSONObject(i)
//                        var creatorName: String = hit.getString("user")
//                        var imageUrl: String = hit.getString("webformatURL")
//                        var likecount: Int = hit.getInt("likes")
//                        var imageTags: String = hit.getString("tags")
//
//                        //Log.d("Array Entry " + i, jsonArray.toString())
//                        Log.d("Array Entry $i", creatorName + "\n" + imageTags + "\n" + imageUrl)
//                    }
                    val resultArray: JSONArray = response.getJSONArray("results")
                    for (i in 0 until resultArray.length()){
                        val recipeObj: JSONObject = resultArray.optJSONObject(i)

                        var title: String = recipeObj.getString("title")
                        var ingredients: String = recipeObj.getString("ingredients")
                        var thumbnail: String = recipeObj.getString("thumbnail")
                        var linkUrl: String = recipeObj.getString("href")

                        Log.d("<===Result===>", title)

                        var recipe = Recipe()
                        recipe.mTitle = title
                        recipe.mIngredients = ingredients
                        recipe.mThumbnail = thumbnail
                        recipe.mLinkAddress = linkUrl

                        recipeArray!!.add(recipe)
                    }
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
}
