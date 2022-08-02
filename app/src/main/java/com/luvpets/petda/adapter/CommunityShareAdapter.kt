package com.luvpets.petda.adapter

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.luvpets.petda.R
import com.luvpets.petda.model.ShareModel
import com.luvpets.petda.util.DpToPx

class CommunityShareAdapter(
  val itemClicked: (ShareModel) -> Unit
): ListAdapter<ShareModel, CommunityShareAdapter.ViewHolder>(differ) {
  inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    fun bind(shareModel: ShareModel) {
      val shareCategory = view.findViewById<AppCompatButton>(R.id.shareCategory)
      val shareTitle = view.findViewById<TextView>(R.id.shareTitle)
      val shareContent = view.findViewById<TextView>(R.id.shareContent)
      val shareDate = view.findViewById<TextView>(R.id.shareDate)
      val shareWatched = view.findViewById<TextView>(R.id.shareWatchedCount)
      val shareLiked = view.findViewById<TextView>(R.id.shareLikedCount)
      val shareImaged = view.findViewById<ImageView>(R.id.shareImage)
  
      shareCategory.text = shareModel.category
      shareTitle.text = shareModel.title
      shareContent.text = shareModel.content
      shareDate.text = shareModel.date
      shareWatched.text = shareModel.watched.toString()
      shareLiked.text = shareModel.liked.toString()
      
      Glide.with(shareImaged.context)
        .load(shareModel.imageUrl)
        .transform(CenterCrop(), RoundedCorners(DpToPx().dpToPx(shareImaged.context, 10)))
        .into(shareImaged)
      
      // 아이템 클릭
      view.setOnClickListener {
        itemClicked(shareModel)
      }
    }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.recycler_community_share_item, parent, false))
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(currentList[position])
  }
  
  companion object {
    val differ = object: DiffUtil.ItemCallback<ShareModel>() {
      override fun areItemsTheSame(oldItem: ShareModel, newItem: ShareModel): Boolean {
        return oldItem.id == newItem.id
      }
  
      override fun areContentsTheSame(oldItem: ShareModel, newItem: ShareModel): Boolean {
        return oldItem == newItem
      }
    }
  }
}