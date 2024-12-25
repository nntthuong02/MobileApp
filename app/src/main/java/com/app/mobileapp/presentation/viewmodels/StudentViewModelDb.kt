package com.app.mobileapp.presentation.viewmodels

import android.app.Application
import android.content.ContentValues
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.mobileapp.data.database.StudentDatabaseHelper
import com.app.mobileapp.data.models.StudentModel

class StudentViewModelDb(application: Application) : AndroidViewModel(application) {

    private val dbHelper = StudentDatabaseHelper(application)
    private val _students = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> get() = _students

    init {
        loadStudents()
    }

    private fun loadStudents() {
        val studentList = mutableListOf<StudentModel>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            StudentDatabaseHelper.TABLE_NAME,
            arrayOf(StudentDatabaseHelper.COLUMN_ID, StudentDatabaseHelper.COLUMN_NAME),
            null, null, null, null, null
        )
        while (cursor.moveToNext()) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(StudentDatabaseHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(StudentDatabaseHelper.COLUMN_NAME))
            studentList.add(StudentModel(name, id))
        }
        cursor.close()
        _students.value = studentList
    }

    fun addStudent(student: StudentModel) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(StudentDatabaseHelper.COLUMN_ID, student.studentId)
            put(StudentDatabaseHelper.COLUMN_NAME, student.studentName)
        }
        db.insert(StudentDatabaseHelper.TABLE_NAME, null, values)
        loadStudents()
    }

    fun editStudent(student: StudentModel, oldStudentId: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(StudentDatabaseHelper.COLUMN_ID, student.studentId)
            put(StudentDatabaseHelper.COLUMN_NAME, student.studentName)
        }
        db.update(
            StudentDatabaseHelper.TABLE_NAME,
            values,
            "${StudentDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(oldStudentId)
        )
        loadStudents()
    }

    fun deleteStudent(student: StudentModel) {
        val db = dbHelper.writableDatabase
        db.delete(
            StudentDatabaseHelper.TABLE_NAME,
            "${StudentDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(student.studentId)
        )
        loadStudents()
    }

    fun undoDelete(student: StudentModel, position: Int) {
        addStudent(student)
    }
}
