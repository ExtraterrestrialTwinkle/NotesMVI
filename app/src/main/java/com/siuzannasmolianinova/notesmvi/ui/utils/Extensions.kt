package com.siuzannasmolianinova.notesmvi.ui.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showError(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}