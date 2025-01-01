package com.app.mobileapp.data.database

import android.util.Log
import androidx.room.*
import com.app.mobileapp.data.models.StudentModel

@Dao
interface StudentDao {
    @Query("SELECT * FROM students_table")
    fun getAllStudents(): List<StudentModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: StudentModel)

    @Update
    fun updateStudent(student: StudentModel){
        Log.d("StudentDao", "Updating student" + student.toString())
    }

    @Delete
    fun deleteStudent(student: StudentModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStudents(students: List<StudentModel>)

    @Query("UPDATE students_table SET name = :name, id = :newId WHERE id = :id")
    fun updateStudentNameAndId(id: String, name: String, newId: String)
}
