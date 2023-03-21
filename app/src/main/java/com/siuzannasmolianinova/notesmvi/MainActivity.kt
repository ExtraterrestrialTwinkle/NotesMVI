package com.siuzannasmolianinova.notesmvi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.siuzannasmolianinova.notesmvi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var textChangeListener: TextChangeListener? = null

    interface TextChangeListener {
        fun doEditableTitleVisible(): Boolean
        fun doAfterTextChanged(text: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        binding.editableTitle.apply {
            isVisible = textChangeListener?.doEditableTitleVisible() ?: false
            setText(
                if (textChangeListener?.doEditableTitleVisible() == true) {
                    ""
                } else {
                    getString(R.string.app_name)
                }
            )
            doAfterTextChanged {
                textChangeListener?.doAfterTextChanged(it.toString())
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
