package com.ishmit.aisleassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishmit.aisleassignment.databinding.ProfileItemBinding

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileHolder>() {

    private var profilesList: List<Map<String, Any>> = emptyList()

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder {
        // Inflate the item layout using data binding
        val binding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileHolder(binding)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        val profile = profilesList[position]
        holder.bindProfile(profile)
    }

    // Get item count
    override fun getItemCount(): Int = profilesList.size

    // Submit new list of profiles
    fun submitList(profiles: List<Map<String, Any>>) {
        // Filter and update the profiles list
        profilesList = profiles.filterIsInstance<Map<String, Any>>()
        notifyDataSetChanged()
    }

    inner class ProfileHolder(private val binding: ProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind profile data to the view holder
        fun bindProfile(profile: Map<String, Any>) {
            val firstName = profile.getFirstName()
            val avatarUrl = profile.getAvatarUrl()

            // Load profile avatar using Glide
            loadProfileAvatar(avatarUrl)
            // Set the profile name
            setProfileName(firstName)
        }

        // Get first name from profile data
        private fun Map<String, Any>.getFirstName(): String? =
            this["first_name"] as? String

        // Get avatar URL from profile data
        private fun Map<String, Any>.getAvatarUrl(): String? =
            this["avatar"] as? String

        // Load profile avatar using Glide
        private fun loadProfileAvatar(avatarUrl: String?) {
            Glide.with(binding.imageProfile)
                .load(avatarUrl)
                .into(binding.imageProfile)
        }

        // Set profile name
        private fun setProfileName(firstName: String?) {
            binding.textName.text = firstName
        }
    }
}
