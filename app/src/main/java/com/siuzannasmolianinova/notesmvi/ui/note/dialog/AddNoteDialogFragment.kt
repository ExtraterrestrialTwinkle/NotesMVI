package com.siuzannasmolianinova.notesmvi.ui.note.dialog

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.siuzannasmolianinova.notesmvi.databinding.DialogAddNoteBinding
import com.siuzannasmolianinova.notesmvi.ui.base.BaseBottomSheetDialog
import com.siuzannasmolianinova.notesmvi.ui.base.BindingInflater
import com.siuzannasmolianinova.notesmvi.ui.note.NoteScreenEvent
import com.siuzannasmolianinova.notesmvi.ui.note.NoteViewModel
import com.siuzannasmolianinova.notesmvi.ui.utils.Constants.TITLE
import com.siuzannasmolianinova.notesmvi.ui.utils.setNavigationResult
import com.siuzannasmolianinova.notesmvi.ui.utils.showError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddNoteDialogFragment : BaseBottomSheetDialog<DialogAddNoteBinding>() {

    private val viewModel: NoteViewModel by viewModels()
    override val bindingInflater: BindingInflater = DialogAddNoteBinding::inflate
//    private val args: AddNoteDialogFragmentArgs by navArgs() // Понадобится когда будет реализация изменения тайтла

    override fun setupViews() {
        isCancelable = false
        binding.editText.editText?.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, i, _ ->
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (binding.editText.editText?.text.toString().isBlank()) {
                        viewModel.sendEvent(NoteScreenEvent.ShowError("The title must contain at least one character"))
                    } else {
                        setNavigationResult(TITLE, binding.editText.editText?.text.toString())
                        findNavController().popBackStack()
                    }
                    return@OnEditorActionListener true
                }
                false
            }
        )
    }

    override fun setupViewModels() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    state.error?.let { showError(binding.root, null, it) }
                }
            }
        }
    }
}
