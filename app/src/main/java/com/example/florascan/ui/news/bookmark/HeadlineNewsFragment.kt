package com.example.florascan.ui.news.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florascan.databinding.FragmentHeadlineNewsBinding
import com.example.florascan.ui.news.NewsAdapter
import com.example.florascan.ui.news.NewsViewModel
import com.example.florascan.ui.news.ViewModelFactory
import com.example.florascan.utils.Result

class HeadlineNewsFragment : Fragment() {

    private var _binding: FragmentHeadlineNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHeadlineNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel and RecyclerView setup for Headline News
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: NewsViewModel by viewModels { factory }
        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked) {
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.submitList(result.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
