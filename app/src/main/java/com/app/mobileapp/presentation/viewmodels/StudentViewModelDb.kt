package com.app.mobileapp.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.mobileapp.data.database.StudentDatabase
import com.app.mobileapp.data.models.StudentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModelDb(application: Application) : AndroidViewModel(application) {

    private val studentDao = StudentDatabase.getInstance(application).studentDao()
    private val _students = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> get() = _students

    init {
        loadStudents()
    }

    private fun loadStudents() {
        CoroutineScope(Dispatchers.IO).launch {
            val studentList = studentDao.getAllStudents()
            _students.postValue(studentList)
        }
    }

    fun addStudent(student: StudentModel) {
        CoroutineScope(Dispatchers.IO).launch {
            studentDao.insertStudent(student)
            loadStudents()
        }
    }

    fun editStudent(student: StudentModel) {
        CoroutineScope(Dispatchers.IO).launch {
            studentDao.updateStudent(student)
            Log.d("editStudent", "ok")
            loadStudents()
        }
    }

    fun deleteStudent(student: StudentModel) {
        CoroutineScope(Dispatchers.IO).launch {
            studentDao.deleteStudent(student)
            loadStudents()
        }
    }

    fun addAllStudents(students: List<StudentModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            studentDao.insertAllStudents(students)
            loadStudents()
        }
    }

    // Hàm mới: Update chỉ tên sinh viên
    fun updateStudentNameAndId(id: String, name: String, newId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            studentDao.updateStudentNameAndId(id, name, newId)
            loadStudents()
        }
    }
}
