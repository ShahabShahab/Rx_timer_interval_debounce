package com.sample.screensaverapp

import android.content.Context
import android.content.Intent
import android.util.Log
import com.sample.screensaverapp.Constants.SCREEN_SAVER_TIMEOUT
import java.util.*

class ScreenSaver {

    val TAG = "ScreenSaver"
    private var hasTimerAlreadySetUp: Boolean = false
    private var timer: Timer
    private lateinit var onUserInteractionChangedListener: OnUserInteractionListener

    private constructor() {
        timer = Timer()
    }

    fun setOnUserInteractionListener(onUserInteractionListener: OnUserInteractionListener) {
        this.onUserInteractionChangedListener = onUserInteractionListener
    }

    fun startUserSessionTimer() {
        Log.d(TAG, "startUserSessionTimer ")
        val timerTask = object : TimerTask() {
            override fun run() {
                onUserInteractionChangedListener?.onUserInactivityDetected()
            }
        }
        if (hasTimerAlreadySetUp) {
            timer.cancel()
            timer = Timer()
        }
        timer.schedule(timerTask, SCREEN_SAVER_TIMEOUT)
        hasTimerAlreadySetUp = true
    }

    companion object {
        @Volatile
        private var INSTANCE: ScreenSaver? = null
        @JvmStatic
        fun getInstance(): ScreenSaver? {
            if (INSTANCE == null) {
                INSTANCE = ScreenSaver()
            }
            return INSTANCE
        }
    }

    interface OnUserInteractionListener {
        fun onUserInactivityDetected()
    }
}