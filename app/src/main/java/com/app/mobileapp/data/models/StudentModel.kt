package com.app.mobileapp.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students_table")
data class StudentModel(
    @ColumnInfo(name = "name")
    val studentName: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val studentId: String
)
