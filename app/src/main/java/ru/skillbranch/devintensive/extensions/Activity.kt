package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.Math.abs

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = this.currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen():Boolean {
    val rect = Rect()
    this.window.decorView.getWindowVisibleDisplayFrame(rect)
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    return abs(displayMetrics.heightPixels-rect.height()) > 128
}

fun Activity.isKeyboardClosed():Boolean {
    return !this.isKeyboardOpen()
}