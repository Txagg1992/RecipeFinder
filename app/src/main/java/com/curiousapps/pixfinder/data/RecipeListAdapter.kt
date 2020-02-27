package com.curiousapps.pixfinder.data

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.curiousapps.pixfinder.R
import com.curiousapps.pixfinder.activities.DetailActivity
import com.curiousapps.pixfinder.activities.ShowPictureActivity
import com.curiousapps.pixfinder.model.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(
    private val list: ArrayList<Recipe>,
    private val context: Context) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    private val EXTRA_URL: String = "thumbnail"
    private val EXTRA_PHOTOGRAPHER: String = "photographer"
    private val EXTRA_TAGS: String = "tags"

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.list_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindView(list[position])
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
            if (!TextUtils.isEmpty(recipe.mThumbnail)){
                Picasso.get()
                    .load(recipe.mThumbnail)
                    .placeholder(android.R.drawable.ic_menu_report_image)
                    .error(android.R.drawable.ic_menu_report_image)
                    .into(thumbnail)
            }else{
                Picasso.get()
                    .load(R.mipmap.ic_launcher)
                    .into(thumbnail)
            }

            linkButton.setOnClickListener {
                val intent = Intent(context, ShowPictureActivity::class.java)
                intent.putExtra("link", recipe.mLinkAddress.toString())
                context.startActivity(intent)
            }

            thumbnail.setOnClickListener {
                val dIntent = Intent(context, DetailActivity::class.java)
                val clickedItem: Recipe = list[adapterPosition]

                dIntent.putExtra(EXTRA_URL, clickedItem.mThumbnail)
                dIntent.putExtra(EXTRA_PHOTOGRAPHER, clickedItem.mTitle)
                dIntent.putExtra(EXTRA_TAGS, clickedItem.mIngredients)

                context.startActivity(dIntent)
            }
        }
    }
}