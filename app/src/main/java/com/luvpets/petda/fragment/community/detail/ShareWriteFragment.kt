package com.luvpets.petda.fragment.community.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.luvpets.petda.activities.CommunityDetailActivity
import com.luvpets.petda.adapter.CommunityPostMultiImageAdapter
import com.luvpets.petda.databinding.FragmentCommunityShareWriteBinding
import com.luvpets.petda.data.model.PetInfoEntity
import com.luvpets.petda.room.PetInfoDB

class ShareWriteFragment: Fragment() {
  private val binding by lazy { FragmentCommunityShareWriteBinding.inflate(layoutInflater) }
  private lateinit var petInfo: PetInfoEntity
  private lateinit var _context: Context
  
  private lateinit var imageList: MutableList<Uri>
  private lateinit var communityPostMultiImageAdapter: CommunityPostMultiImageAdapter
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
  
    if (container != null) {
      _context = container.context
    }
    
    val r = Runnable {
      val getPetInfo = context?.let { PetInfoDB.getInstance(it).dao().getPetInfo() }!!
      initPetInfo(getPetInfo)
    }
    // 서브 쓰레드를 사용하여 메인 쓰레드에 영향을 주지 않도록 해야한다.
    val thread = Thread(r)
    thread.start()
    
    settingPetInfo()
    initImageArea()
    handlePostImage()
    
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
    Glide.with(image.context)
      .load(petInfo.image)
      .transform(CenterCrop(), CircleCrop())
      .into(image)
  }
  
  private fun initImageArea() {
    imageList = mutableListOf()
    communityPostMultiImageAdapter = CommunityPostMultiImageAdapter()
  }
  private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val imageUri = it.data?.data
    if (imageUri != null) {
      imageList.add(imageUri)
    }
    communityPostMultiImageAdapter.submitList(imageList)
  }
  private fun handlePostImage() {
    val sharePostImageBtn = binding.sharePostImageBtn
    val shareImageList = binding.sharePostImageList
    
    sharePostImageBtn.setOnClickListener {
      val permission = ContextCompat.checkSelfPermission(_context, Manifest.permission.READ_EXTERNAL_STORAGE)
      if (permission == PackageManager.PERMISSION_GRANTED) {
        navigatePhotos()
      } else {
        requestPermission()
      }
    }
    shareImageList.layoutManager = LinearLayoutManager(activity)
    shareImageList.adapter = communityPostMultiImageAdapter
  }
  private fun navigatePhotos() {
    imagePickerLauncher.launch(Intent(Intent.ACTION_GET_CONTENT).apply {
      this.type = "image/*"
    })
  }
  private fun requestPermission() {
    ActivityCompat.requestPermissions(
      activity as CommunityDetailActivity,
      arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
      1000
    )
  }
  
  @Deprecated("Deprecated in Java")
  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      imagePickerLauncher.launch(Intent(Intent.ACTION_PICK).apply {
        this.type = MediaStore.Images.Media.CONTENT_TYPE
        this.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        this.action = Intent.ACTION_GET_CONTENT
      })
    } else {
      showAlert("권한 요청 확인","권한이 거부되었을 경우 서비스 이용이 어렵습니다.")
    }
  }
  private fun showAlert(title: String, message: String) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(_context)
    builder.setTitle(title)
      .setMessage(message)
      .setPositiveButton("동의") { _, _ -> requestPermission()}
      .setNegativeButton("취소", null)
    builder.create().show()
  }
}