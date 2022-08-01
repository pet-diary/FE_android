package com.luvpets.petda.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luvpets.petda.databinding.FragmentHomeWalkBinding

class WalkFragment: Fragment() {
  private val binding by lazy { FragmentHomeWalkBinding.inflate(layoutInflater) }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return binding.root
  }
}