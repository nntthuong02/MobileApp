package com.app.filemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class DirectoryContentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_directory_content, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = arguments?.getString(ARG_PATH) ?: return
        val directory = File(path)
        loadFiles(directory)
    }

    private fun loadFiles(directory: File) {
        val files = directory.listFiles()?.toList() ?: emptyList()
        fileAdapter = FileAdapter(files) { file ->
            if (file.isDirectory) {
                val fragment = DirectoryContentFragment.newInstance(file.path)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(), "Selected file: ${file.name}", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = fileAdapter
    }

    companion object {
        private const val ARG_PATH = "path"

        fun newInstance(path: String): DirectoryContentFragment {
            val fragment = DirectoryContentFragment()
            val args = Bundle()
            args.putString(ARG_PATH, path)
            fragment.arguments = args
            return fragment
        }
    }
}
