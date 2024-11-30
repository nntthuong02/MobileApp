package com.app.mobileapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.mobileapp.R
import com.app.mobileapp.common.Constants
import com.app.mobileapp.databinding.FragmentEditStudentBinding

class EditStudentFragment : Fragment() {
    private var binding: FragmentEditStudentBinding? = null
    private val args: EditStudentFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("Screen", "EditStudentFragment")

        super.onViewCreated(view, savedInstanceState)
        val studentId = args.studentId
        val studentName = args.studentName

        Log.d("studentId", studentName)
        binding?.apply {
            Log.d("EditStudentFragment", "binding")
            etStudentId.setText(studentId)
            etStudentName.setText(studentName)

            btnSave.setOnClickListener {
                Log.d("EditStudentFragment", "btnSave")

                val updateId = etStudentId.text.toString()
                val updateName = etStudentName.text.toString()

                val result = Bundle().apply {
                    putString(Constants.EDIT_STUDENT_ID, updateId)
                    putString(Constants.EDIT_STUDENT_NAME, updateName)
                }
                Log.d("EditStudentFragment", "Result sent: $result")
                parentFragmentManager.setFragmentResult(Constants.EDIT_STUDENT_RESULT, result)
                findNavController().navigate(R.id.action_global_listStudentFragment)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}