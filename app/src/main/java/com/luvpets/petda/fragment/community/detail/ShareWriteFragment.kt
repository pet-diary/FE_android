package com.luvpets.petda.fragment.community.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.luvpets.petda.databinding.FragmentCommunityShareWriteBinding
import com.luvpets.petda.model.PetInfoEntity
import com.luvpets.petda.room.PetInfoDB

class ShareWriteFragment: Fragment() {
  private val binding by lazy { FragmentCommunityShareWriteBinding.inflate(layoutInflater) }
  private lateinit var petInfo: PetInfoEntity
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    val r = Runnable {
      val getPetInfo = context?.let { PetInfoDB.getInstance(it).dao().getPetInfo() }!!
      initPetInfo(getPetInfo)
    }
    // 서브 쓰레드를 사용하여 메인 쓰레드에 영향을 주지 않도록 해야한다.
    val thread = Thread(r)
    thread.start()
  
    settingPetInfo()
    
    return binding.root
  }
  
  private fun initPetInfo(getPetInfo: PetInfoEntity) {
    petInfo = getPetInfo
  }
  @SuppressLint("SetTextI18n")
  private fun settingPetInfo() {
    binding.shareIncludePetInfo.sharePetName.text = petInfo.name
    binding.shareIncludePetInfo.sharePetBirth.text = "${petInfo.birth}\n${petInfo.age}살"
    binding.shareIncludePetInfo.sharePetWeight.text = petInfo.weight.toString()
    binding.shareIncludePetInfo.sharePetBreed.text = petInfo.breed
    
    val image = binding.shareIncludePetInfo.sharePetImage
    Log.d("petInfo", "$petInfo")
    Glide.with(image.context)
      .load(petInfo.image)
      .transform(CenterCrop(), CircleCrop())
      .into(image)
  }
}