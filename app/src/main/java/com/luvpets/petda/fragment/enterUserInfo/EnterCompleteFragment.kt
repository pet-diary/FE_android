package com.luvpets.petda.fragment.enterUserInfo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.luvpets.petda.R
import com.luvpets.petda.databinding.FragmentCompleteBinding

class EnterCompleteFragment: Fragment() {
  private val binding by lazy { FragmentCompleteBinding.inflate(layoutInflater) }
  
  private lateinit var _context: Context
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setFragmentResultListener("petNameData") { _, bundle ->
      val petName = bundle.getString("petName")
      binding.completedPetName.text = "반가워요!\n이제 우리 ${petName}(이)를\n돌보러 가볼까요?"
    }
    setFragmentResultListener("petImageData") { _, bundle ->
      val imageUri = bundle.getString("petImage")
      val glide = Glide.with(this)
      glide.load(Uri.parse(imageUri)).centerCrop().circleCrop().into(binding.completedPetImage)
      binding.completedPetImage.startAnimation(AnimationUtils.loadAnimation(_context, R.anim.tongtong))
    }
  }
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    if (container != null) {
      _context = container.context
    }
    return binding.root
  }
}