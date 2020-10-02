package com.hamzasharuf.runningtracker.utils.extensions

import android.view.View

fun View.visibile(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}