package com.karunesh.imageloaderx.core

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class MainExecutorImpl : Executor {

    private val mainHandler = Handler(Looper.getMainLooper())

    private fun isMainThread(): Boolean {
        return mainHandler.looper.thread === Thread.currentThread()
    }

    override fun execute(runnable: Runnable) {
        if (isMainThread()) {
            runnable.run()
        } else {
            mainHandler.post(runnable)
        }
    }

}