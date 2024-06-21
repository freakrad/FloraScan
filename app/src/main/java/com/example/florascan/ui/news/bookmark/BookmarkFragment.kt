package com.example.florascan.ui.news.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florascan.databinding.FragmentBookmarkBinding
import com.example.florascan.ui.news.NewsAdapter
import com.example.florascan.ui.news.NewsViewModel
import com.example.florascan.ui.news.ViewModelFactory

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel and RecyclerView setup for Bookmarked News
        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel by viewModels { factory }
        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked) {
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
            binding?.progressBar?.visibility = View.GONE
            newsAdapter.submitList(bookmarkedNews)
        }

        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

