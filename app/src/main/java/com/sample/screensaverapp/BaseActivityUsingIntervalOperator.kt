package com.sample.screensaverapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseActivityUsingIntervalOperator : AppCompatActivity() {

    private val TAG = "BaseIntervalOperator"
    private var intervalDisposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        startTimer()
    }

    private fun startTimer() {
        getObservable().subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    private fun getObservable(): Observable<out Long> {
        return Observable.interval(Constants.SCREEN_SAVER_TIMEOUT,
            TimeUnit.MILLISECONDS)
    }

    private fun getObserver(): Observer<Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
                intervalDisposable = d
            }

            override fun onNext(value: Long) {
                Log.d(TAG, "onNext: ")
                startScreenSaverActivity()
                intervalDisposable?.dispose()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }
        }
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
