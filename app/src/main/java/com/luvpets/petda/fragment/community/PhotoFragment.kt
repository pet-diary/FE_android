package com.luvpets.petda.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luvpets.petda.databinding.FragmentCommunityPhotoBinding

class PhotoFragment: Fragment() {
  private val binding by lazy { FragmentCommunityPhotoBinding.inflate(layoutInflater) }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return binding.root
  }
}