package com.app.mobileapp.presentation.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.app.mobileapp.R

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        Log.d("Screen", "AddStudentActivity")

        val studentNameEditText = findViewById<EditText>(R.id.etStudentName)
        val studentIdEditText = findViewById<EditText>(R.id.etStudentId)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            val studentName = studentNameEditText.text.toString()
            val studentId = studentIdEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("student_name", studentName)
            resultIntent.putExtra("student_id", studentId)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()  // Kết thúc Activity và trả kết quả về MainActivity
        }
    }
}
