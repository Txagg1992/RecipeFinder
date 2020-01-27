package com.example.recipefinder.data

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipefinder.R
import com.example.recipefinder.model.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(
    private val list: ArrayList<Recipe>,
    private val context: Context) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.bindView(list[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title = itemView.findViewById<TextView>(R.id.recipe_title)
        var ingredients = itemView.findViewById<TextView>(R.id.recipe_ingredients)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var linkButton = itemView.findViewById<Button>(R.id.link_button)

        fun bindView(recipe: Recipe){
            title.text = recipe.mTitle
            ingredients.text = recipe.mIngredients
            linkButton.setOnClickListener {

            }
            if (TextUtils.isEmpty(recipe.mThumbnail)){
                Picasso.with(context)
                    .load(recipe.mThumbnail)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(thumbnail)
            }else{
                Picasso.with(context)
                    .load(R.mipmap.ic_launcher)
                    .into(thumbnail)
            }
        }
    }
}