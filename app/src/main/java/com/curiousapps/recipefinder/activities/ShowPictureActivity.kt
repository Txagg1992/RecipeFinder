package com.curiousapps.recipefinder.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.curiousapps.recipefinder.R
import kotlinx.android.synthetic.main.activity_show_picture.*

class ShowPictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_picture)

        var extras = intent.extras

        if (extras != null){
            var link = extras.get("link")

            webView_id.loadUrl(link.toString())
        }
    }
}
