package com.hamzasharuf.runningtracker.utils

import com.hamzasharuf.runningtracker.utils.enums.StateStatus

class Resource<out T>(val status: StateStatus, val data: T?, val message: String?) {
    companion object{
        fun<T> success(data: T)
                = Resource(StateStatus.SUCCESS, data, null)
        fun error(message: String? = null)
                = Resource(StateStatus.ERROR, null, message)
        fun<T> loading(data: T? = null)
                = Resource(StateStatus.LOADING, data, null)
    }
}