package com.yunusbedir.cryptocurrencypricetrackerapp.util

import android.content.Context
import android.util.Patterns
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import com.yunusbedir.cryptocurrencypricetrackerapp.R

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.emailCheck(email: String): Boolean {
    if (email.isNullOrEmpty()) {
        showLongToast("Please enter your email address")
        return false
    } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
        showLongToast("Please enter a correct email address")
        return false
    }
    return true
}

fun Context.passwordCheck(password: String): Boolean {
    if (password.isNullOrEmpty()) {
        showLongToast("Please enter your password")
        return false
    }
    return true
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun ImageView.loadImageWithResource(@RawRes @DrawableRes resourceId: Int) {
    Glide.with(this).load(resourceId).into(this)
}