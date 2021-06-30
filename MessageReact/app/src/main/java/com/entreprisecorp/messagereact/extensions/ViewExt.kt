package com.entreprisecorp.messagereact.extensions

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.entreprisecorp.messagereact.R

fun View.addRipple(): Unit = with(TypedValue()) {
    context.theme.resolveAttribute(R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun NavController.safeNavigate(@IdRes fromId: Int, directions: NavDirections, navOptions: NavOptions? = null, extraArgs: Bundle? = null) {
    if (currentDestination?.id == fromId) {
        val arguments = directions.arguments
        extraArgs?.let { arguments.putAll(it) }
        navigate(directions.actionId, arguments, navOptions)
    }
}