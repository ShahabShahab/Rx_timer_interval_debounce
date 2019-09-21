package com.sample.screensaverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class FirstScreenActivity : BaseScreenSaverActivityUsingRxOperator() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
    }

    fun goToSecondScreen(view : View){
        startActivity(Intent(this,
            SecondScreenActivity::class.java))
        finish()
    }
}
