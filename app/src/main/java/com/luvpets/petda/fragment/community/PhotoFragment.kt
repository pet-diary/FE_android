package com.luvpets.petda.fragment.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.adapter.CommunityPhotoAdapter
import com.luvpets.petda.databinding.FragmentCommunityPhotoBinding
import com.luvpets.petda.dto.PhotoDto
import com.luvpets.petda.data.service.Instance
import com.luvpets.petda.data.service.ShareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoFragment: Fragment() {
  private val binding by lazy { FragmentCommunityPhotoBinding.inflate(layoutInflater) }
  private val loadingContent by lazy { binding.loadingContent }
  private lateinit var photoAdapter: CommunityPhotoAdapter
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    initAdapter()
    initLoadingAnimation()
    getPhotoListFromApi()
    
    return binding.root
  }
  
  private fun initAdapter() {
    photoAdapter = CommunityPhotoAdapter()
    binding.communityPhotoList.adapter = photoAdapter
  }
  private fun initLoadingAnimation() {
    loadingContent.loadingContent.visibility = View.VISIBLE
    binding.loadingContent.loadingPaw.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.loading_paw))
  }
  private fun getPhotoListFromApi() {
//    val retrofit = Instance(context).instance
//
//    retrofit.create(ShareService::class.java).also {
//      it.getPhotoList()
//        .enqueue(object: Callback<PhotoDto> {
//          override fun onResponse(call: Call<PhotoDto>, response: Response<PhotoDto>) {
//            if (response.isSuccessful.not()) return
//            loadingContent.loadingContent.visibility = View.GONE
//            response.body()?.let { dto ->
//              photoAdapter.submitList(dto.items)
//            }
//          }
//
//          override fun onFailure(call: Call<PhotoDto>, t: Throwable) {
//            binding.loadingContent.loadingText.text = "데이터 로드에 실패했습니다."
//          }
//        })
//    }
  }
}