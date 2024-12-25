package com.app.filemanager

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_file_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = Environment.getExternalStorageDirectory()
        loadFiles(root)
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
}
