package com.luvpets.petda.fragment.enterUserInfo

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.luvpets.petda.R
import com.luvpets.petda.activities.EnterUserInfoActivity
import com.luvpets.petda.adapter.BreedAdapter
import com.luvpets.petda.databinding.FragmentInfoPetBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class EnterInfoPetsFragment: Fragment(){
  private val binding by lazy { FragmentInfoPetBinding.inflate(layoutInflater) }
  private var pet: String? = null
  private var breedArr: MutableList<String>? = null
  
  private lateinit var _context: Context
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setFragmentResultListener("selectPetData") { _, bundle ->
      pet = bundle.getString("selectPet")
      binding.selectedPetTitle.text = "${pet}의"
      val catBreedArr = resources.getStringArray(R.array.breed_cat).toMutableList()
      val dogBreedArr = resources.getStringArray(R.array.breed_dog).toMutableList()
      breedArr = if (pet == "멍멍이") {
        formatList(dogBreedArr)
        dogBreedArr
      } else {
        formatList(catBreedArr)
        catBreedArr
      }
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
  
    handleChangePetName()
    handleClickBtnCamera()
    handleClickGender()
    handleClickCalender()
    handleClickBreed()
    handleClickNeutering()
    handleSearch()
    
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
  private fun formatList(list: MutableList<String>) {
    binding.slidingBreedList.recyclerBreedList.adapter = BreedAdapter(
      list,
      LayoutInflater.from(context), binding.sliderBreedWrap, binding.petBreedText
    )
  }
  private fun handleChangePetName() {
    binding.petName.apply {
      this.addTextChangedListener {
        val petName = this.text.toString()
        setFragmentResult("petNameData", bundleOf("petName" to petName))
      }
    }
  }
  private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val imageUri = it.data?.data
    val glide = Glide.with(this)
    glide.load(Uri.parse(imageUri.toString())).centerCrop().circleCrop().into(binding.btnCamera)
    setFragmentResult("petImageData", bundleOf("petImage" to imageUri.toString()))
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
      activity as EnterUserInfoActivity,
      arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
      1000
    )
  }
  private fun handleClickGender() {
    val btnGirl = binding.btnGirl
    val btnBoy = binding.btnBoy
    changeBtnStatus(btnGirl, btnBoy)
    changeBtnStatus(btnBoy, btnGirl)
  }
  private fun handleClickCalender() {
    binding.btnPetBirth.setOnClickListener {
      val target = binding.petBirthText
      initCalendar(target)
    }
    binding.btnPetWithMe.setOnClickListener {
      val target = binding.petWithMeText
      initCalendar(target)
    }
  }
  private fun initCalendar(target: TextView) {
    // 캘린더 노출
    val dialog = AlertDialog.Builder(context).create()
    val edialog : LayoutInflater = LayoutInflater.from(context)
    val mView : View = edialog.inflate(R.layout.layout_min_calendar,null)
  
    val year : NumberPicker = mView.findViewById(R.id.year)
    val month : NumberPicker = mView.findViewById(R.id.month)
    val day : NumberPicker = mView.findViewById(R.id.day)
    val cancel : AppCompatButton = mView.findViewById(R.id.btnCancel)
    val confirm : AppCompatButton = mView.findViewById(R.id.btnConfirm)
    //  순환 안되게 막기
    year.wrapSelectorWheel = false
    month.wrapSelectorWheel = false
    day.wrapSelectorWheel = false
    //  editText 설정 해제
    year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    day.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    //  최소값 설정
    year.minValue = 2000
    month.minValue = 1
    day.minValue = 1
    //  최대값 설정
    year.maxValue = 2022
    month.maxValue = 12
    day.maxValue = 31
    //  취소 버튼 클릭 시
    cancel.setOnClickListener {
      dialog.dismiss()
      dialog.cancel()
    }
    //  완료 버튼 클릭 시
    confirm.setOnClickListener {
      target.text = (year.value).toString() + "년" + (month.value).toString() + "월" + (day.value).toString() + "일"
      dialog.dismiss()
      dialog.cancel()
    }
    dialog.setView(mView)
    dialog.create()
    dialog.show()
  }
  private fun handleClickBreed() {
    binding.btnPetBreed.setOnClickListener {
      binding.sliderBreedWrap.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
    }
    binding.slidingBreedList.btnCloseSlider.setOnClickListener {
      binding.sliderBreedWrap.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }
  }
  private fun handleClickNeutering() {
    val btnPetNonNeutering = binding.btnPetNonNeutering
    val btnPetNeutering = binding.btnPetNeutering
    changeBtnStatus(btnPetNonNeutering, btnPetNeutering)
    changeBtnStatus(btnPetNeutering, btnPetNonNeutering)
  }
  private fun changeBtnStatus(target: AppCompatButton, default: AppCompatButton) {
    target.apply {
      this.setOnClickListener {
        this.setTextColor(Color.WHITE)
        this.setBackgroundResource(R.drawable.btn_18r_main)
        default.setTextColor(Color.parseColor("#2b2b2b"))
        default.setBackgroundResource(R.drawable.btn_18r_default)
      }
    }
  }
  private fun handleSearch() {
    binding.slidingBreedList.inputSearch.apply {
      this.addTextChangedListener {
        val newArr: MutableList<String> = emptyList<String>().toMutableList()
        if (this.text.isEmpty()) {
          formatList(breedArr!!)
        } else {
          for (breed in breedArr!!) {
            if (breed.contains(this.text)) {
              newArr.add(breed)
            }
          }
          formatList(newArr)
        }
      }
    }
  }
  private fun showAlert(title: String, message: String) {
    val builder: androidx.appcompat.app.AlertDialog.Builder = androidx.appcompat.app.AlertDialog.Builder(_context)
    builder.setTitle(title)
      .setMessage(message)
      .setPositiveButton("동의") { _, _ -> requestPermission()}
      .setNegativeButton("취소", null)
    builder.create().show()
  }
}