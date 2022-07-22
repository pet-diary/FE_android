package com.luvpets.petda.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luvpets.petda.databinding.FragmentSelectPetBinding

class EnterSelectPetFragment: Fragment() {
  private var _binding: FragmentSelectPetBinding? = null
  private val binding get() = _binding!!
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentSelectPetBinding.inflate(inflater, container, false)
    
    return binding.root
  }
  
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}