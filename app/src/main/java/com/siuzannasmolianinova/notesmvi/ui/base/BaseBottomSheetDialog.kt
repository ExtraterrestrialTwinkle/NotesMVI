package com.siuzannasmolianinova.notesmvi.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias BindingInflater = ((LayoutInflater, ViewGroup?, Boolean) -> ViewBinding)

abstract class BaseBottomSheetDialog<Binding : ViewBinding> : BottomSheetDialogFragment() {

    private var _binding: Binding? = null
    protected val binding: Binding get() = _binding as Binding
    protected abstract val bindingInflater: BindingInflater

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        @Suppress("UNCHECKED_CAST")
        _binding = (bindingInflater(inflater, container, false) as Binding)
        setupViews()
        setupViewModels()
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun setupViewModels() {}

    protected open fun setupViews() {}
}
