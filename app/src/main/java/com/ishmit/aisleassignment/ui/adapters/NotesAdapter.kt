package com.ishmit.aisleassignment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ishmit.aisleassignment.R
import com.ishmit.aisleassignment.databinding.NotesItemBinding

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesHolder>() {

    private var profilesList: List<Map<String, Any>> = emptyList()

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        // Inflate the item layout using data binding
        val binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesHolder(binding)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val profile = profilesList[position]
        holder.bindProfile(profile)
    }

    // Get item count
    override fun getItemCount(): Int = profilesList.size

    // Submit new list of profiles
    fun submitProfiles(profiles: List<Map<String, Any>>) {
        // Filter and update the profiles list
        profilesList = profiles.filterIsInstance<Map<String, Any>>()
        notifyDataSetChanged()
    }

    inner class NotesHolder(private val binding: NotesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind profile data to the view holder
        fun bindProfile(profile: Map<String, Any>) {
            val firstName = profile.getFirstName()
            val age = profile.getAge()
            binding.textName.text = formatNameAndAge(firstName, age)
            val avatarUrl = profile.getAvatarUrl()
            loadProfileImage(avatarUrl)
        }

        // Get first name from profile data
        private fun Map<String, Any>.getFirstName(): String? {
            val generalInfo = get("general_information") as? Map<*, *>
            return generalInfo?.get("first_name") as? String
        }

        // Get age from profile data
        private fun Map<String, Any>.getAge(): Int? {
            val generalInfo = get("general_information") as? Map<*, *>
            val ageDouble = generalInfo?.get("age") as? Double
            return ageDouble?.toInt()
        }

        // Format name and age for display
        private fun formatNameAndAge(firstName: String?, age: Int?): String =
            "$firstName, ${age ?: ""}"

        // Get avatar URL from profile data
        private fun Map<String, Any>.getAvatarUrl(): String? {
            val photos = this["photos"] as? List<Map<String, Any>>?
            return photos?.firstOrNull {
                it["status"] == "avatar" && it["selected"] == true
            }?.get("photo") as? String
        }

        // Load profile image using Glide
        private fun loadProfileImage(avatarUrl: String?) {
            Glide.with(binding.imageNotes)
                .load(avatarUrl ?: R.drawable.sample)
                .into(binding.imageNotes)
        }
    }
}
