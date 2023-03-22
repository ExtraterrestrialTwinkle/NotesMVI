package com.siuzannasmolianinova.notesmvi.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun showError(view: View, anchor: View?, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        .apply { anchorView = anchor }
        .show()
}

fun LocalDateTime.format(): String {
    val pattern = "dd.MM.yyyy HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}

fun <T> Fragment.setNavigationResult(key: String, value: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
}
