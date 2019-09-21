package com.sample.screensaverapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseScreenSaverActivityUsingTimeTask : AppCompatActivity(),
    ScreenSaver.OnUserInteractionListener {

    private val TAG = "BaseSaverUsingTimeTask"

    override fun onUserInactivityDetected() {
        Log.d(TAG, "onUserInactivityDetected: ")
        startActivity(Intent(this,
            ScreenSaverActivity::class.java))
    }

    private var screenSaverInstance: ScreenSaver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_screen_saver_using_time_task)
        screenSaverInstance = ScreenSaver.getInstance()
        screenSaverInstance?.setOnUserInteractionListener(this)
        screenSaverInstance?.startUserSessionTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(TAG, "onUserInteraction: ")
        screenSaverInstance?.startUserSessionTimer()
    }
}
