package com.sample.screensaverapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.faranegar.realandrodthings.utils.TimerHandler
import kotlinx.android.synthetic.main.activity_first_screen.*

class FirstScreenActivity : BaseActivityUsingDebounceOperator() {

    lateinit var timerHandler : TimerHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        timerHandler.cancel()
        timerHandler.start()
    }

    override fun onResume() {
        super.onResume()
        timerHandler = TimerHandler()
        timerHandler.start()
        timerHandler.timerLiveData.observe(this ,
            Observer {timerString ->
                timer.text = timerString })
    }

    override fun onPause() {
        super.onPause()
        timerHandler.cancel()
    }
}