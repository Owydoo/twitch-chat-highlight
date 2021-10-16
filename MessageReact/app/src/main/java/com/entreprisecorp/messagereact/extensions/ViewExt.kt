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

fun NavController.safeNavigate(
    @IdRes fromId: Int,
    directions: NavDirections,
    navOptions: NavOptions? = null,
    extraArgs: Bundle? = null
) {
    if (currentDestination?.id == fromId) {
        val arguments = directions.arguments
        extraArgs?.let { arguments.putAll(it) }
        navigate(directions.actionId, arguments, navOptions)
    }
}


fun codingGame() {
    val w = 10
    val h = 10


    val string = "............X..........X."
    val charArray = string.toCharArray().map {
        if (it.equals(".")) {
            0
        } else {
            -1
        }
    }.toMutableList()

    charArray.forEachIndexed { index, char ->
        if (char == -1) {
            charArray.getOrNull(index - w)?.let {
                charArray[index - w] += 1
            }
            charArray.getOrNull(index - w - 1)?.let {
                charArray[index - w - 1] += 1
            }
            charArray.getOrNull(index - w + 1)?.let {
                charArray[index - w + 1] += 1
            }
            charArray.getOrNull(index + w)?.let {
                charArray[index - w] += 1
            }
            charArray.getOrNull(index + w - 1)?.let {
                charArray[index - w - 1] += 1
            }
            charArray.getOrNull(index + w + 1)?.let {
                charArray[index - w + 1] += 1
            }
            charArray.getOrNull(index - 1)?.let {
                charArray[index - 1] += 1
            }
            charArray.getOrNull(index + 1)?.let {
                charArray[index + 1] += 1
            }
        }
    }

    var result = ""
    charArray.forEach {
        result += if (it == -1) {
            "x"
        } else {
            it.toString()
        }
    }

    val array = result.chunked(w)
    array.forEach {
        println(it)
    }
}