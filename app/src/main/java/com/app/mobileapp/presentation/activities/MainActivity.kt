package com.app.mobileapp.presentation.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.app.mobileapp.R
import com.app.mobileapp.data.models.StudentModel
import com.app.mobileapp.databinding.ActivityMainBinding
import com.app.mobileapp.presentation.fragment.ListStudentFragmentDirections
import com.app.mobileapp.presentation.viewmodels.StudentViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private val binding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onResume() {
    super.onResume()
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController


    // Thêm sự kiện khi click vào nút Add New
    binding.btnAddNew.setOnClickListener {
      navController.navigate(R.id.action_global_addStudentFragment)
    }
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

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.add_new_student -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, ADD_STUDENT_REQUEST)
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }





  companion object {
    const val ADD_STUDENT_REQUEST = 1
    const val EDIT_STUDENT_REQUEST = 2
  }
}
