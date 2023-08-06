package com.ishmit.aisleassignment.ui.fragments.notesScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ishmit.aisleassignment.R
import com.ishmit.aisleassignment.data.models.NotesResponse
import com.ishmit.aisleassignment.databinding.FragmentNotesBinding
import com.ishmit.aisleassignment.ui.adapters.NotesAdapter
import com.ishmit.aisleassignment.ui.adapters.ProfileAdapter
import com.ishmit.aisleassignment.utils.viewState.ResponseRequest
import com.ishmit.aisleassignment.utils.gone
import com.ishmit.aisleassignment.utils.visible
import com.ishmit.aisleassignment.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : Fragment(R.layout.fragment_notes) {

    private lateinit var binding: FragmentNotesBinding
    private val notesViewModel: NotesViewModel by viewModel()
    private val invitesAdapter = NotesAdapter()
    private val likesAdapter = ProfileAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)

        // Initialize and set up recycler views
        setupRecyclerViews()

        // Set up bottom navigation view with navigation controller
        setupBottomNavigationView()

        // Retrieve token from arguments
        val args = NotesFragmentArgs.fromBundle(requireArguments())
        val authToken = args.token

        // Observe notes response
        notesViewModel.notesResponse.observe(viewLifecycleOwner) { response ->
            handleNotesResponse(response)
        }

        // Request notes data using the provided auth token
        notesViewModel.getNotes(authToken)
    }

    // Initialize and set up recycler views for invites and likes
    private fun setupRecyclerViews() {
        // Set up horizontal recycler view for invites
        binding.notesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = invitesAdapter
        }

        // Set up grid recycler view for likes
        binding.profileRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = likesAdapter
        }
    }

    // Set up bottom navigation view with navigation controller
    private fun setupBottomNavigationView() {
        val navView = binding.bottomNavigation
        val navController = findNavController()
        navView.setupWithNavController(navController)
    }

    // Handle the response state of fetching notes
    private fun handleNotesResponse(uiState: ResponseRequest<NotesResponse>) {
        when (uiState) {
            is ResponseRequest.Loading -> {
                binding.notesProgressBar.visible()
                binding.profileProgressBar.visible()
            }
            is ResponseRequest.Success -> {
                binding.notesProgressBar.gone()
                binding.profileProgressBar.gone()
                uiState.data?.let { notesResponse ->
                    handleInvitesResponse(notesResponse.invites)
                    handleLikesResponse(notesResponse.likes)
                }
            }
            is ResponseRequest.Failure -> {
                showToast(uiState.error)
                binding.notesProgressBar.gone()
                binding.profileProgressBar.gone()
            }
        }
    }

    // Handle the invites response and update the invites adapter
    private fun handleInvitesResponse(invites: Map<String, Any>?) {
        val profiles = invites?.get("profiles") as? List<Map<String, Any>>
        profiles?.let { invitesAdapter.submitProfiles(it) }
    }

    // Handle the likes response and update the likes adapter
    private fun handleLikesResponse(likes: Map<String, Any>?) {
        val profiles = likes?.get("profiles") as? List<Map<String, Any>>
        profiles?.let { likesAdapter.submitList(it) }
    }
}
