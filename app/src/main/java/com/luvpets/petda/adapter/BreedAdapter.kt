package com.luvpets.petda.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luvpets.petda.R
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class BreedAdapter (
  var list: MutableList<String>,
  var inflater: LayoutInflater,
  var sliderView: SlidingUpPanelLayout,
  var selectedBreed: TextView
) : RecyclerView.Adapter<BreedAdapter.ViewHolder>() {
  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val breed: TextView
    init {
      breed = itemView.findViewById(R.id.selectBreedText)
      itemView.setOnClickListener {
        val position: Int = adapterPosition
        selectedBreed.text = list.get(position)
        sliderView.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
      }
    }
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = inflater.inflate(R.layout.recycler_breed_item, parent, false)
    return ViewHolder(view)
  }
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.breed.text = list.get(position)
  }
  
  override fun getItemCount(): Int {
    return list.size
  }
}