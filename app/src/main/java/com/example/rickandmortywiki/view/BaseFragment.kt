package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.FirebaseCrashlytics

abstract class BaseFragment(private val TAG: String, @LayoutRes contentLayoutId: Int = 0): Fragment(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().log("Fragment: $TAG")
    }
}