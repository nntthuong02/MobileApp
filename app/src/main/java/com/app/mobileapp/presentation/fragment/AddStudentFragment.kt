package com.app.mobileapp.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.app.mobileapp.R
import com.app.mobileapp.common.Constants
import com.app.mobileapp.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {
    private var binding: FragmentAddStudentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Screen", "AddStudentFragment")

        binding?.apply {

            btnAdd.setOnClickListener{
                val updateName = etStudentName.text.toString()
                val updateId = etStudentId.text.toString()
                val result = Bundle().apply {
                    putString(Constants.ADD_STUDENT_ID, updateId)
                    putString(Constants.ADD_STUDENT_NAME, updateName)
                }
                parentFragmentManager.setFragmentResult(Constants.ADD_STUDENT_RESULT, result)
                findNavController().navigate(R.id.action_global_listStudentFragment)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddStudentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

}