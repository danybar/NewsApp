package com.example.newsapp.ui.notifications

import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        notificationsViewModel.loadSavedArticles(requireContext())

        notificationsViewModel.savedArticles.observe(viewLifecycleOwner) { savedArticles ->
            binding.textNotifications.text = savedArticles
            binding.textNotifications.movementMethod = ScrollingMovementMethod() // Nastavení scrollování
        }

        binding.clearMemoryButton.setOnClickListener {
            clearSharedPreferences()
        }

        return root
    }

    private fun clearSharedPreferences() {
        val sharedPrefs = requireActivity().getSharedPreferences("MyAppSharedPreferences", Context.MODE_PRIVATE)
        sharedPrefs.edit().clear().apply()
        // Aktualizace zobrazení nebo zobrazení zprávy uživateli
        binding.textNotifications.text = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
