package com.sample.screensaverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ScreenSaverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_saver)
    }

    override fun onUserInteraction() {
        finish()
    }
}
