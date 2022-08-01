package com.luvpets.petda.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luvpets.petda.databinding.FragmentCommunityShareBinding

class ShareFragment: Fragment() {
  private val binding by lazy { FragmentCommunityShareBinding.inflate(layoutInflater) }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return binding.root
    
  }
}