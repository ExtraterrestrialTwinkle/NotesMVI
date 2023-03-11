package com.siuzannasmolianinova.notesmvi.ui.main

import androidx.navigation.fragment.findNavController
import com.siuzannasmolianinova.notesmvi.databinding.FragmentMainBinding
import com.siuzannasmolianinova.notesmvi.ui.base.BaseFragment
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    private lateinit var viewModel: MainViewModel
    private lateinit var notesAdapter: NotesListAdapter

    override fun setupViews() {
        initAdapter()
    }

    private fun initAdapter() {
        notesAdapter = NotesListAdapter { id ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToNoteFragment(id)
            )
        }
        binding.list.adapter = notesAdapter
    }

    override fun render() {
        TODO("Not yet implemented")
    }
}
