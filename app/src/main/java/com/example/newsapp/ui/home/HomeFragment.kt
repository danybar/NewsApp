package com.example.newsapp.ui.home

import com.example.newsapp.ui.dashboard.ArticlesAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var articlesAdapter: ArticlesAdapter
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()

        homeViewModel.articles.observe(viewLifecycleOwner) { articles ->
            articlesAdapter.submitList(articles)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter()
        binding.homeRecyclerView.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}