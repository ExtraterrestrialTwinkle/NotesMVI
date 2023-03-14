package com.siuzannasmolianinova.notesmvi.ui.main

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.siuzannasmolianinova.notesmvi.R
import com.siuzannasmolianinova.notesmvi.databinding.FragmentMainBinding
import com.siuzannasmolianinova.notesmvi.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var notesAdapter: NotesListAdapter

    override fun setupViews() {
        setupMenu()
        initAdapter()
    }

    override fun setupViewModels() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    render(it)
                }
            }
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.toolbar_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.add -> findNavController().navigate(MainFragmentDirections.actionMainFragmentToNoteFragment())
                    }
                    return true
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun initAdapter() {
        notesAdapter = NotesListAdapter { id ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToNoteFragment(id)
            )
        }
        binding.list.adapter = notesAdapter
    }

    private fun render(state: MainScreenState) {
        state.error
            ?.let {
                showError(it)
            }
            ?: if (!state.isLoading) {
                notesAdapter.submitList(state.data)
                binding.loader.isVisible = false
            } else {
                binding.loader.isVisible = true
            }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
