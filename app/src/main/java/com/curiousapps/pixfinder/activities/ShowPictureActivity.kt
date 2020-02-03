package com.curiousapps.pixfinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.curiousapps.pixfinder.R
import kotlinx.android.synthetic.main.activity_show_picture.*

class ShowPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_picture)

        val extras = intent.extras

        if (extras != null){
            val link = extras.get("link")

            webView_id.loadUrl(link.toString())
        }
    }
}
