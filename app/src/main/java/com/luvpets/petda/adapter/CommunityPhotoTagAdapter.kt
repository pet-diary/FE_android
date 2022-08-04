package com.luvpets.petda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luvpets.petda.R

class CommunityPhotoTagAdapter: ListAdapter<String, CommunityPhotoTagAdapter.ViewHolder>(differ) {
  inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun bind(tag: String) {
      val tagText = view.findViewById<AppCompatButton>(R.id.communityTag)
      tagText.text = tag
    }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.recycler_community_tag, parent, false))
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
  
  companion object {
    val differ = object: DiffUtil.ItemCallback<String>() {
      override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
      }
  
      override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
      }
    }
  }
}