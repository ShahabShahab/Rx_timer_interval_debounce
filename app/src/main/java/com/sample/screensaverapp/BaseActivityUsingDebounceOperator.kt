package com.sample.screensaverapp

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


open class BaseActivityUsingDebounceOperator : AppCompatActivity() {

    private val TAG = "BaseDebounceOperator"
    private var debounceDisposable: Disposable? = null

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        startTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(TAG, "onUserInteraction: ")
        if (debounceDisposable != null) {
            debounceDisposable?.dispose()
        }
        startTimer()
    }

    private fun startTimer() {
        Log.d(TAG, "startTimer: ")
        getStringObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(5L, TimeUnit.SECONDS)
            .subscribe {
                getStringObserver()
            }
    }

    private fun getStringObservable(): Observable<String> {
        Log.d(TAG, "getStringObservable: ")
        return Observable.create{emitter ->
               emitter.onNext("shahab")
        }
    }

    private fun getStringObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onComplete() {
                Log.d(TAG, "onComplete: ")
            }

            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe: ")
                debounceDisposable = d
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

    private fun startScreenSaverActivity() {
        startActivity(
            Intent(
                this,
                ScreenSaverActivity::class.java
            )
        )
    }

}
