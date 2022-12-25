package com.example.rickandmortywiki.util

import android.content.res.Resources

object NumberUtils {
    val Int.toDp: Int
        get() =
            ( this / Resources.getSystem().displayMetrics.density ).toInt()


    val Int.toPx: Int
        get() =
            ( this * Resources.getSystem().displayMetrics.density ).toInt()
}
