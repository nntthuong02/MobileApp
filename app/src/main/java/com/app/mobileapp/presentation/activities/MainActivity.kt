package com.app.mobileapp.presentation.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.app.mobileapp.R
import com.app.mobileapp.databinding.ActivityMainBinding
import com.app.mobileapp.presentation.viewmodels.StudentViewModelDb
import com.app.mobileapp.utils.StudentDataSource

class MainActivity : AppCompatActivity() {
  private val viewModel: StudentViewModelDb by viewModels()

  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  }


  // Tạo Option Menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  // Xử lý sự kiện khi chọn OptionMenu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController

    when (item.itemId) {
      R.id.add_new_student -> {
        // Điều hướng tới AddStudentFragment
        navController.navigate(R.id.action_global_addStudentFragment)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }
}
