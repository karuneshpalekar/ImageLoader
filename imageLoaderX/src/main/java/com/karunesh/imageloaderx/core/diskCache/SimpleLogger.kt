package com.karunesh.imageloaderx.core.diskCache


class SimpleLogger(private val isLoggingEnabled: Boolean) : Logger {

    override fun log(format: String?, vararg args: Any?) {
        if (isLoggingEnabled) {
            System.out.printf("$format%n", *args)
        }
    }
}