package com.luvpets.petda.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.luvpets.petda.activities.EnterMyInfo
import com.luvpets.petda.databinding.FragmentInfoUserBinding

class EnterInfoFragment: Fragment() {
  private var _binding: FragmentInfoUserBinding? = null
  private val binding get() = _binding!!
  
  private lateinit var _context: Context
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentInfoUserBinding.inflate(inflater, container, false)
  
    if (container != null) {
      _context = container.context
    }
    
    handleClickBtnCamera()
    
    return binding.root
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
      })
    } else {
      showAlert("권한 요청 확인","권한이 거부되었을 경우 서비스 이용이 어렵습니다.")
    }
  }
  
  private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val imageUri = it.data?.data
    val glide = Glide.with(this)
    glide.load(Uri.parse(imageUri.toString())).centerCrop().circleCrop().into(binding.btnCamera)
  }
  private fun handleClickBtnCamera() {
    binding.btnCamera.setOnClickListener {
      val permission = ContextCompat.checkSelfPermission(_context, Manifest.permission.READ_EXTERNAL_STORAGE)
      if (permission == PackageManager.PERMISSION_GRANTED) {
        navigatePhotos()
      } else {
        requestPermission()
      }
    }
  }
  private fun navigatePhotos() {
    imagePickerLauncher.launch(Intent(Intent.ACTION_GET_CONTENT).apply {
      this.type = "image/*"
    })
  }
  private fun requestPermission() {
    ActivityCompat.requestPermissions(
      activity as EnterMyInfo,
      arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
      1000
    )
  }
  private fun showAlert(title: String, message: String) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(_context)
    builder.setTitle(title)
      .setMessage(message)
      .setPositiveButton("동의") { _, _ -> requestPermission()}
      .setNegativeButton("취소", null)
    builder.create().show()
  }
  
  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}