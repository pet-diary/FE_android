package com.luvpets.petda.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
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
    
    handleCheck()
    return binding.root
  }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setFragmentResultListener("userNameData") { _, bundle ->
      val userName = bundle.getString("userName")
      binding.userNameText.text = "${userName}님!"
    }
  }
  
  private fun handleCheck() {
    val cat = binding.selectCat
    val dog = binding.selectDog
    cat.setOnClickListener {
      changeChecked(dog)
    }
    dog.setOnClickListener {
      changeChecked(cat)
    }
  }
  private fun changeChecked(item: RadioButton) {
    if (item.isChecked) {
      item.isChecked = false
    }
  }
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}