package com.example.newsapp.ui.dashboard
import ArticlesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aidata.ui.dashboard.DashboardViewModel
import com.example.newsapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()

        binding.searchButton.setOnClickListener {
            val searchQuery = binding.searchEditText.text.toString()
            if (searchQuery.isNotEmpty()) {
                dashboardViewModel.loadNews(searchQuery)
            }
        }

        dashboardViewModel.articles.observe(viewLifecycleOwner) { articles ->
            articlesAdapter.submitList(articles)
        }

        return root
    }

    private fun setupRecyclerView() {
        articlesAdapter = ArticlesAdapter()
        binding.recyclerView.apply {
            adapter = articlesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}