package com.sample.screensaverapp

import android.content.Intent
import android.os.Bundle
import android.view.View

class SecondScreenActivity : BaseScreenSaverActivityUsingRxOperator() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
    }

    fun goToFirstScreen(view: View) {
        startActivity(
            Intent(
                this,
                FirstScreenActivity::class.java
            )
        )
        finish()
    }
}
