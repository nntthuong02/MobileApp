package com.app.mobileapp.presentation.activities


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.app.mobileapp.R

class EditStudentActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val studentNameEditText = findViewById<EditText>(R.id.etStudentName)
        val studentIdEditText = findViewById<EditText>(R.id.etStudentId)

        val studentName = intent.getStringExtra("student_name")
        val studentId = intent.getStringExtra("student_id")

        studentNameEditText.setText(studentName)
        studentIdEditText.setText(studentId)

        // Giả sử bạn có một Button để lưu thông tin sinh viên
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val updatedStudentName = studentNameEditText.text.toString()
            val updatedStudentId = studentIdEditText.text.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("updated_student_name", updatedStudentName)
            resultIntent.putExtra("updated_student_id", updatedStudentId)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()  // Kết thúc Activity và trả kết quả về MainActivity
        }
    }
}
