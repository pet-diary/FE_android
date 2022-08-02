package com.luvpets.petda.fragment.community.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.luvpets.petda.R
import com.luvpets.petda.adapter.CommunityShareImageAdapter
import com.luvpets.petda.databinding.FragmentCommunityShareDetailBinding
import com.luvpets.petda.model.ShareDetailModel
import com.luvpets.petda.service.Instance
import com.luvpets.petda.service.ShareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShareDetailFragment: Fragment() {
  private val binding by lazy { FragmentCommunityShareDetailBinding.inflate(layoutInflater) }
  private val imageAdapter = CommunityShareImageAdapter()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
  
    initRecyclerAdapter()
    initLoadingAnimation()
    getDetailItemFromApi()
    
    return binding.root
  }
  
  private fun initRecyclerAdapter() {
    binding.shareDetailImageList.adapter = imageAdapter
  }
  private fun initLoadingAnimation() {
    binding.loadingContent.visibility = View.VISIBLE
    binding.detailContent.visibility = View.GONE
    binding.loadingPaw.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.loading_paw))
  }
  private fun getDetailItemFromApi() {
    val retrofit = Instance().instance
    
    retrofit.create(ShareService::class.java).also {
      it.getShareDetail()
        .enqueue(object: Callback<ShareDetailModel> {
          override fun onResponse(
            call: Call<ShareDetailModel>,
            response: Response<ShareDetailModel>) {
            if (response.isSuccessful.not()) {
              return
            }
            response.body()?.let { dto ->
              binding.shareCategory.text = dto.category
              binding.shareTitle.text = dto.title
              binding.sharePetName.text = dto.petInfo.name
              binding.sharePetBirth.text = "${dto.petInfo.birth}\n(${dto.petInfo.age}살)"
              binding.sharePetWeight.text = "${dto.petInfo.weight}\n"
              binding.sharePetBreed.text = "${dto.petInfo.breed}\n"
              binding.shareContent.text = dto.content
              binding.shareLiked.text = dto.liked.toString()
              imageAdapter.submitList(dto.images)
  
              binding.loadingContent.visibility = View.GONE
              binding.detailContent.visibility = View.VISIBLE
            }
          }
  
          override fun onFailure(call: Call<ShareDetailModel>, t: Throwable) {
            Toast.makeText(activity, "데이터 로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
          }
        })
    }
  }
}