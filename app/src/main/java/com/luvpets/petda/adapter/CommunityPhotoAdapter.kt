package com.luvpets.petda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.luvpets.petda.R
import com.luvpets.petda.data.model.PhotoModel
import com.luvpets.petda.util.share.DpToPx

class CommunityPhotoAdapter: ListAdapter<PhotoModel, CommunityPhotoAdapter.ViewHolder>(differ) {
  private val photoTagAdapter = CommunityPhotoTagAdapter()
  inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun bind(photoModel: PhotoModel) {
      val photoItemImage = view.findViewById<ImageView>(R.id.photoItemImage)
      val photoItemUserProfile = view.findViewById<ImageView>(R.id.photoItemUserProfile)
      val photoItemUserName = view.findViewById<TextView>(R.id.photoItemUserName)
      val photoItemContent = view.findViewById<TextView>(R.id.photoItemContent)
      val photoItemTagList = view.findViewById<RecyclerView>(R.id.photoItemTagList)
      
      photoItemUserName.text = photoModel.writer.name
      photoItemContent.text = photoModel.content
      
      Glide.with(photoItemImage.context)
        .load(photoModel.imageUrl)
        .transform(FitCenter(), RoundedCorners(DpToPx().dpToPx(photoItemImage.context, 10)))
        .into(photoItemImage)
      Glide.with(photoItemUserProfile.context)
        .load(photoModel.writer.profile)
        .transform(CenterCrop(), CircleCrop())
        .into(photoItemUserProfile)
  
      photoItemTagList.adapter = photoTagAdapter
      photoTagAdapter.submitList(photoModel.tag)
    }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.recycler_community_photo_item, parent, false))
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
  
  companion object {
    val differ = object: DiffUtil.ItemCallback<PhotoModel>() {
      override fun areItemsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem.id == newItem.id
      }
  
      override fun areContentsTheSame(oldItem: PhotoModel, newItem: PhotoModel): Boolean {
        return oldItem == newItem
      }
    }
  }
}