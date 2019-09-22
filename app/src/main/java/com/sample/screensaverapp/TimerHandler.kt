package com.faranegar.realandrodthings.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TimerHandler {

    private val TAG = "TimerHandler"
    private lateinit var timerDisposable: Disposable

    var timerLiveData = MutableLiveData<String>()

    private fun getObservable(): Observable<out Long> {
        return Observable.interval(1L, TimeUnit.SECONDS)
    }

    private fun getObserver(): Observer<String> {
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
                timerLiveData.value = t
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: ")
                timerLiveData.value = "Currently Unavailable!"
            }

        }
    }

    fun start(){
        getObservable()
            .subscribeOn(Schedulers.single())
            .map<String> { currentTime ->
                createTimerString(currentTime)
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    private fun createTimerString(currentTime: Long): String {
        Log.d(TAG, "threadName: ${Thread.currentThread().name}")
        val currentTime = 20 - currentTime
        val timerStringBuilder = StringBuilder()
        val minute = currentTime / 60
        val second = currentTime % 60
        if (minute < 10) {
            timerStringBuilder.append("0$minute:")
        } else {
            timerStringBuilder.append("$minute:")
        }
        if (second < 10) {
            timerStringBuilder.append("0$second")
        } else {
            timerStringBuilder.append("$second")
        }
        return timerStringBuilder.toString()
    }

    fun cancel() {
        timerLiveData.value= "--:--"
        if (!timerDisposable.isDisposed)
            timerDisposable.dispose()
    }

}