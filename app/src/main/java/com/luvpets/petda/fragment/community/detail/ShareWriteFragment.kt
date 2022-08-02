package com.luvpets.petda.fragment.community.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luvpets.petda.databinding.FragmentCommunityShareWriteBinding

class ShareWriteFragment: Fragment() {
  private val binding by lazy { FragmentCommunityShareWriteBinding.inflate(layoutInflater) }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return binding.root
  }
}