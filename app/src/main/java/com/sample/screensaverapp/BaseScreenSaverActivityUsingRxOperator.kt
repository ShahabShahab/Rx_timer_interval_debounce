package com.sample.screensaverapp

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sample.screensaverapp.Constants.SCREEN_SAVER_TIMEOUT
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class BaseScreenSaverActivityUsingRxOperator : AppCompatActivity() {

    private val TAG = "BaseUsingRxOperator"
    private var timerDisposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        startTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(TAG, "onUserInteraction: ")
        if (timerDisposable != null) {
            timerDisposable?.dispose()
        }
        startTimer()
    }

    private fun startTimer() {
        getStringObservable().subscribeOn(Schedulers.single())
            .debounce(SCREEN_SAVER_TIMEOUT, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getStringObserver())
    }

    private fun getStringObserver(): Observer<String> {
        return object : Observer<String> {

            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
                timerDisposable = d
            }

            override fun onNext(t: String) {
                Log.d(TAG, "onNext: ")
                startScreenSaverActivity()
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: " + e.message)
            }

        }
    }

    private fun getStringObservable(): Observable<String> {
        return Observable.create(object : ObservableOnSubscribe<String> {
            override fun subscribe(emitter: ObservableEmitter<String>) {
                emitter.onNext("Please Start Emitting!")
            }
        })
    }

    private fun getObservable(): Observable<out Long> {
        return Observable.timer(SCREEN_SAVER_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    private fun getIntervalObservable(): Observable<out Long> {
        return Observable.interval(SCREEN_SAVER_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    private fun getObserver(): Observer<Long> {
        return object : Observer<Long> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
                timerDisposable = d
            }

            override fun onNext(value: Long) {
                Log.d(TAG, "onNext: ")
                startScreenSaverActivity()
                timerDisposable?.dispose()
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

    override fun onDestroy() {
        super.onDestroy()
        timerDisposable?.dispose()
    }
}
