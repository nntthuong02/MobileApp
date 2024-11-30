package com.app.mobileapp.presentation.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.app.mobileapp.data.models.StudentModel
import com.app.mobileapp.databinding.DialogStudentBinding


class StudentDialogFragment : DialogFragment() {

    private var student: StudentModel? = null
    private var onStudentAdded: ((StudentModel) -> Unit)? = null
    private var onStudentEdited: ((StudentModel) -> Unit)? = null

    fun setOnStudentAdded(callback: (StudentModel) -> Unit) {
        onStudentAdded = callback
    }

    fun setOnStudentEdited(callback: (StudentModel) -> Unit) {
        onStudentEdited = callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogStudentBinding.inflate(LayoutInflater.from(context))
        student?.let {
            binding.editStudentName.setText(it.studentName)
            binding.editStudentId.setText(it.studentId)
        }
        Log.d("Screen", "StudentDialogFragment")

        return AlertDialog.Builder(requireContext())
            .setTitle(if (student == null) "Add Student" else "Edit Student")
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                val name = binding.editStudentName.text.toString()
                val id = binding.editStudentId.text.toString()
                val newStudent = StudentModel(studentName = name, studentId = id)

                if (student == null) onStudentAdded?.invoke(newStudent)
                else onStudentEdited?.invoke(newStudent)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }

    companion object {
        fun newInstance(student: StudentModel?): StudentDialogFragment {
            val dialog = StudentDialogFragment()
            dialog.student = student
            return dialog
        }
    }
}
