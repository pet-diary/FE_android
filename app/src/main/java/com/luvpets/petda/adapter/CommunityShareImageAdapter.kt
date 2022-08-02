package com.luvpets.petda.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.luvpets.petda.R
import com.luvpets.petda.model.ShareDetailImagesModel
import com.luvpets.petda.util.DpToPx

class CommunityShareImageAdapter: ListAdapter<ShareDetailImagesModel, CommunityShareImageAdapter.ViewHolder>(differ) {
  inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun bind(imageModel: ShareDetailImagesModel) {
      val image = view.findViewById<ImageView>(R.id.shareDetailImageItem)
      Glide.with(image.context)
        .load(imageModel.image)
        .transform(CenterCrop(), RoundedCorners(DpToPx().dpToPx(image.context, 10)))
        .into(image)
    }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.recycler_community_detail_image, parent, false))
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
  
  companion object {
    val differ = object: DiffUtil.ItemCallback<ShareDetailImagesModel>() {
      override fun areItemsTheSame(
        oldItem: ShareDetailImagesModel,
        newItem: ShareDetailImagesModel
      ): Boolean {
        return oldItem == newItem
      }
  
      override fun areContentsTheSame(
        oldItem: ShareDetailImagesModel,
        newItem: ShareDetailImagesModel
      ): Boolean {
        return oldItem == newItem
      }
    }
  }
}