package com.app.mobileapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.mobileapp.R
import com.app.mobileapp.common.Constants
import com.app.mobileapp.data.models.StudentModel
import com.app.mobileapp.databinding.FragmentListStudentBinding
import com.app.mobileapp.presentation.adapter.StudentAdapter2
import com.app.mobileapp.presentation.viewmodels.StudentViewModel
import com.google.android.material.snackbar.Snackbar

class ListStudentFragment : Fragment() {
    private var binding: FragmentListStudentBinding? = null
    private val viewModel: StudentViewModel by viewModels()
    private lateinit var studentAdapter: ArrayAdapter<StudentModel>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("Screen", "ListStudentFragment")
        binding?.apply {

        }
        parentFragmentManager.setFragmentResultListener(
            Constants.EDIT_STUDENT_RESULT,
            viewLifecycleOwner
        ) { requestKey, result ->
            Log.d("requestKey", requestKey)
            Log.d("ListStudentFragment", "Result sent: $result")
            // Xử lý kết quả từ Fragment B
            val studentName = result.getString(Constants.EDIT_STUDENT_NAME)
            val studentId = result.getString(Constants.EDIT_STUDENT_ID)
            val checkStudentId = result.getString(Constants.EDIT_STUDENT_RESULT)
            if (studentName != null && studentId != null) {
                val student = StudentModel(studentName, studentId)
                Log.d("editStudent", "ok1")
                viewModel.editStudent(student, checkStudentId?:"")
                Log.d("editStudent", "ok2")

            }
        }

        parentFragmentManager.setFragmentResultListener(
            Constants.ADD_STUDENT_RESULT,
            viewLifecycleOwner
        ) { requestKey, result ->
            Log.d("requestKey", requestKey)
            val studentName = result.getString(Constants.ADD_STUDENT_NAME)
            val studentId = result.getString(Constants.ADD_STUDENT_ID)
            if (studentName != null && studentId != null) {
                val student = StudentModel(studentName, studentId)
                viewModel.addStudent(student)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListStudentBinding.inflate(inflater, container, false)
        setupListView()
        observeViewModel()
        return binding?.root
    }

    private fun setupListView() {
        studentAdapter =
            StudentAdapter2(requireContext(), R.layout.layout_student_item, mutableListOf())
        binding?.listViewStudents?.adapter = studentAdapter

        // Đăng ký ContextMenu cho ListView
        binding?.let { registerForContextMenu(it.listViewStudents) }
    }

    private fun observeViewModel() {
        viewModel.students.observe(requireActivity()) { students ->
            studentAdapter.clear()
            studentAdapter.addAll(students)
            studentAdapter.notifyDataSetChanged()
        }
    }


    // Tạo Context Menu cho từng item trong ListView
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu_student, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val selectedStudent = studentAdapter.getItem(info.position)

        return when (item.itemId) {
            R.id.context_edit -> {
                selectedStudent?.let { showEditStudentActivity(it) }
                true
            }

            R.id.context_remove -> {
                selectedStudent?.let { showDeleteConfirmation(it) }
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    // Hàm mở Activity Edit để sửa thông tin sinh viên
    private fun showEditStudentActivity(student: StudentModel) {
        val action = ListStudentFragmentDirections.actionListStudentFragmentToEditStudentFragment(
            studentId = student.studentId,
            studentName = student.studentName
        )
        findNavController().navigate(action)
    }

    // Hàm xác nhận xóa sinh viên
    private fun showDeleteConfirmation(student: StudentModel) {
        val position = viewModel.students.value?.indexOf(student) ?: -1
        if (position != -1) {
            viewModel.deleteStudent(student)

            binding?.let {
                Snackbar.make(it.root, "Student deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        viewModel.undoDelete(student, position)
                    }
                    .show()
            }
        } else {
            Toast.makeText(requireContext(), "Student not found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}