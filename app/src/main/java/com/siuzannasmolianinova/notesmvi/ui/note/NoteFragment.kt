package com.siuzannasmolianinova.notesmvi.ui.note

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.siuzannasmolianinova.notesmvi.MainActivity
import com.siuzannasmolianinova.notesmvi.MainActivity.TextChangeListener
import com.siuzannasmolianinova.notesmvi.R
import com.siuzannasmolianinova.notesmvi.databinding.FragmentNoteBinding
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.ui.base.BaseFragment
import com.siuzannasmolianinova.notesmvi.ui.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class NoteFragment :
    BaseFragment<FragmentNoteBinding>(FragmentNoteBinding::inflate),
    TextChangeListener {

    private val viewModel: NoteViewModel by viewModels()
    private val args: NoteFragmentArgs by navArgs()

    override fun setupViews() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
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
        // TODO: setup back button & save button on toolbar menu & editable title
        (activity as MainActivity).apply {
            supportActionBar?.title = ""
        }
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
                                        title = (activity as MainActivity).title.toString(),
                                        text = binding.textField.text.toString(),
                                        date = LocalDateTime.now()
                                    )
                                )
                            )
                            findNavController().popBackStack()
                        }
                        android.R.id.home -> findNavController().popBackStack()
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
                showError(it)
                binding.loader.isVisible = false
            }
            ?: if (!state.isLoading) {
                binding.loader.isVisible = false
                binding.textField.setText(state.note?.text)
                // TODO: set title into toolbar
            } else {
                binding.loader.isVisible = true
            }
    }

    override fun doEditableTitleVisible(): Boolean {
        return true
    }

    override fun doAfterTextChanged(text: String) {
        viewModel.sendEvent(NoteScreenEvent.OnUpdateTitle(text))
    }
}
