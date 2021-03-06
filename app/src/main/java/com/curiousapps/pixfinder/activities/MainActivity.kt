package com.curiousapps.pixfinder.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.curiousapps.pixfinder.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews(){
        search_button.setOnClickListener {

            val intent = Intent(this, RecipeList::class.java)

            val photoTags = photo_tag.text.toString().trim()
            val photoSearch = photo_search.text.toString().trim()

            intent.putExtra("photoTags", photoTags)
            intent.putExtra("photoSearch", photoSearch)
            startActivity(intent)
            //startActivity(Intent(this, RecipeList::class.java))
        }
    }
}
