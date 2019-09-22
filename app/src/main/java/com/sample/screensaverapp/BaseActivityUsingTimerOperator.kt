package com.sample.screensaverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseActivityUsingTimerOperator : AppCompatActivity() {

    private val TAG = "BaseTimerOperator"
    private var timerDisposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    private fun startTimer() {
        getObservable().subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    private fun getObserver(): Observer<Long> {
        return object : Observer<Long> {
            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
                timerDisposable = d
            }

            override fun onNext(t: Long) {
                Log.d(TAG, "onNext: ")
                startScreenSaverActivity()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: " + e.message)
            }

        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(TAG, "onUserInteraction: ")
        if (timerDisposable != null) {
            timerDisposable?.dispose()
        }
        startTimer()
    }

    private fun getObservable(): Observable<out Long> {
        return Observable.timer(Constants.SCREEN_SAVER_TIMEOUT,
            TimeUnit.MILLISECONDS)
    }

    private fun startScreenSaverActivity() {
        startActivity(
            Intent(
                this,
                ScreenSaverActivity::class.java
            )
        )
    }

}
