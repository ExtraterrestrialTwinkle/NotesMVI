package com.siuzannasmolianinova.notesmvi.ui.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.siuzannasmolianinova.notesmvi.MainActivity
import com.siuzannasmolianinova.notesmvi.R
import com.siuzannasmolianinova.notesmvi.databinding.FragmentNoteBinding
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.base.BaseFragment
import com.siuzannasmolianinova.notesmvi.ui.utils.Constants.TITLE
import com.siuzannasmolianinova.notesmvi.ui.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class NoteFragment :
    BaseFragment<FragmentNoteBinding>(FragmentNoteBinding::inflate) {

    private val viewModel: NoteViewModel by viewModels()
    private val args: NoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.title.isBlank()) {
            findNavController().navigate(
                NoteFragmentDirections.actionNoteFragmentToAddNoteDialogFragment()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val navBackStackEntry = navController.getBackStackEntry(R.id.noteFragment)
        val observer = LifecycleEventObserver { _, event ->
            if (event == ON_RESUME &&
                navBackStackEntry.savedStateHandle.contains(TITLE)
            ) {
                val result = navBackStackEntry.savedStateHandle.get<String>(TITLE)
                (activity as MainActivity).supportActionBar?.title = result
            }
        }
        navBackStackEntry.lifecycle.addObserver(observer)
        viewLifecycleOwner.lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    navBackStackEntry.lifecycle.removeObserver(observer)
                }
            }
        )
    }

    override fun setupViews() {
        setupToolbar(args.id)
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

    private fun setupToolbar(id: Long) {
        (requireActivity() as MenuHost).addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.note_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.save -> {
                            viewModel.sendEvent(
                                NoteScreenEvent.SaveNote(
                                    NoteModel(
                                        id = id,
                                        title = (activity as MainActivity).supportActionBar?.title.toString(),
                                        text = binding.textField.text.toString(),
                                        date = LocalDateTime.now()
                                    )
                                )
                            )
                            popBackStack()
                        }
                        android.R.id.home -> popBackStack()
                    }
                    return true
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    private fun render(state: NoteScreenState) {
        state.error
            ?.let {
                showError(binding.root, null, it)
                binding.loader.isVisible = false
            }
            ?: if (!state.isLoading) {
                binding.loader.isVisible = false
                binding.textField.setText(state.note?.text)
            } else {
                binding.loader.isVisible = true
            }
    }

    private fun popBackStack() {
        findNavController().popBackStack(R.id.mainFragment, false)
    }
}
