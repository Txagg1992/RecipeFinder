package com.curiousapps.pixfinder.activities

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.curiousapps.pixfinder.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val detailExtras = intent.extras
        if (detailExtras != null) {
            val imageUrl = detailExtras.getString("thumbnail")
            val photographer = detailExtras.getString("photographer")
            val photoTags = detailExtras.getString("tags")
            text_view_creator_detail.text = photographer
            text_view_photo_tags.text = photoTags

            if (!TextUtils.isEmpty(imageUrl)) {
                Picasso.get()
                    .load(imageUrl)
                    .into(image_view_detail)

            }
        }
    }
}
