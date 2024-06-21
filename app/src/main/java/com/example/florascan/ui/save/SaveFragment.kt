package com.example.florascan.ui.save

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florascan.databinding.FragmentSaveBinding
import com.example.florascan.factory.AuthViewModelFactory
import com.example.florascan.result.Result

class SaveFragment : Fragment() {

    private var _binding: FragmentSaveBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: SaveViewModel by viewModels() {
        AuthViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSave.layoutManager = layoutManager

        val adapter = SaveAdapter(viewModel)
        binding.recyclerViewSave.adapter = adapter

        viewModel.getHistory().observe(viewLifecycleOwner) { save ->
            if (save != null) {
                when (save) {
                    is Result.Error -> {
                        binding.progressBar2.visibility = View.GONE
                        Toast.makeText(requireContext(), save.error, Toast.LENGTH_SHORT).show()
                    }

                    Result.Loading -> binding.progressBar2.visibility = View.VISIBLE
                    is Result.Success -> {
                        binding.progressBar2.visibility = View.GONE
                        adapter.submitList(save.data.predictions)
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}