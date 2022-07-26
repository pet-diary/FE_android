package com.luvpets.petda.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.databinding.FragmentTypeBinding

class EnterTypeFragment: Fragment() {
  private var _binding: FragmentTypeBinding? = null
  private val binding get() = _binding!!
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentTypeBinding.inflate(inflater, container, false)
    
    handleClickMode()
    return binding.root
  }
  
  private fun handleClickMode() {
    val modeDetail = binding.modeDetail
    val modeSimple = binding.modeSimple
    handleModeBackground(modeDetail, modeSimple)
    handleModeBackground(modeSimple, modeDetail)
  }
  private fun handleModeBackground(target: LinearLayout, default: LinearLayout) {
    target.apply {
      this.setOnClickListener {
        default.setBackgroundResource(R.drawable.btn_12r_default)
        this.setBackgroundResource(R.drawable.btn_12r_main)
      }
    }
  }
}