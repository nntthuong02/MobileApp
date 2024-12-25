package com.app.mobileapp.presentation.viewmodels
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.mobileapp.data.models.StudentModel
import com.app.mobileapp.utils.StudentDataSource

class StudentViewModel : ViewModel() {

    private val _students = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> get() = _students

    private val studentList = mutableListOf<StudentModel>()

    init {
        studentList.addAll(StudentDataSource().getStudentData())
        _students.value = studentList
    }

    fun addStudent(student: StudentModel) {
        studentList.add(student)
        _students.value = studentList
    }

    fun editStudent(editedStudent: StudentModel, checkId: String) {
        val index = studentList.indexOfFirst { it.studentId == checkId }
        if (index != -1) {
            studentList[index] = editedStudent
            _students.value = studentList
            Log.d("editStudent", "ok3")

        }
    }

    fun deleteStudent(student: StudentModel) {
        studentList.remove(student)
        _students.value = studentList
    }

    fun undoDelete(student: StudentModel, position: Int) {
        studentList.add(position, student)
        _students.value = studentList
    }
}
