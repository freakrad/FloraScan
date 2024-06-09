package com.example.florascan.ui.news

import android.os.Bundle
<<<<<<< HEAD
import android.util.Log
=======
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.florascan.databinding.FragmentNewsBinding
import com.example.florascan.utils.Result

class NewsFragment : Fragment() {

    private var tabName: String? = null

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding

<<<<<<< HEAD
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
=======
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel by viewModels {
            factory
        }

<<<<<<< HEAD
        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked){
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }


//        if (tabName == TAB_NEWS) {
            Log.d("NewsFragment", "running")
=======
        val newsAdapter = NewsAdapter()

        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter 
        }

        if (tabName == TAB_NEWS) {
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
            viewModel.getHeadlineNews().observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
<<<<<<< HEAD
                            Log.d("NewsFragment", "Loading")
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            Log.d("NewsFragment", "Success")
=======
                            binding?.progressBar?.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }
<<<<<<< HEAD
=======

>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
<<<<<<< HEAD
//        } else if (tabName == TAB_BOOKMARK) {
//            viewModel.getBookmarkedNews().observe(viewLifecycleOwner) { bookmarkedNews ->
//                binding?.progressBar?.visibility = View.GONE
//                newsAdapter.submitList(bookmarkedNews)
//            }
//        }

        binding?.rvNews?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = newsAdapter
=======
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
<<<<<<< HEAD
        const val TAB_BOOKMARK = "bookmark7"
=======
        const val TAB_BOOKMARK = "bookmark"
>>>>>>> 3d54716ebe7a44730cc6494df9f3f362dbb386c9
    }
}