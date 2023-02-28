package com.siuzannasmolianinova.notesmvi.ui.main

import com.siuzannasmolianinova.notesmvi.databinding.FragmentMainBinding
import com.siuzannasmolianinova.notesmvi.ui.base.BaseFragment
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    private lateinit var viewModel: MainViewModel

    override fun render() {
        TODO("Not yet implemented")
    }
}
