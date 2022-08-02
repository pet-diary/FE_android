package com.luvpets.petda.fragment.community

import android.content.Context
import android.content.Intent
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
import com.luvpets.petda.activities.CommunityDetailActivity
import com.luvpets.petda.adapter.CommunityShareAdapter
import com.luvpets.petda.databinding.FragmentCommunityShareBinding
import com.luvpets.petda.dto.ShareDto
import com.luvpets.petda.service.Instance
import com.luvpets.petda.service.ShareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShareFragment: Fragment() {
  private val binding by lazy { FragmentCommunityShareBinding.inflate(layoutInflater) }
  private val loadingContent by lazy { binding.loadingContent }
  private val shareAdapter = CommunityShareAdapter(itemClicked = {
    val intent = Intent(activity, CommunityDetailActivity::class.java)
      .apply {
        putExtra("selectedShareItem", it.id)
        putExtra("fragmentType", "shareDetail")
      }
    startActivity(Intent(intent))
  })
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    binding.communityShareList.adapter = shareAdapter
  
    initLoadingAnimation()
    getShareListFromApi()
    
    return binding.root
  }
  
  private fun initLoadingAnimation() {
    loadingContent.visibility = View.VISIBLE
    binding.loadingPaw.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.loading_paw))
  }
  private fun getShareListFromApi() {
    // retrofit 생성
    val retrofit = Instance().instance
    
    // 호출
    retrofit.create(ShareService::class.java).also {
      it.getShareList()
        .enqueue(object: Callback<ShareDto> {
          // 성공시
          override fun onResponse(call: Call<ShareDto>, response: Response<ShareDto>) {
            if (response.isSuccessful.not()) {
              return
            }
            loadingContent.visibility = View.GONE
            response.body()?.let { dto ->
              shareAdapter.submitList(dto.items)
            }
          }
  
          // 실패시
          override fun onFailure(call: Call<ShareDto>, t: Throwable) {
            binding.loadingText.text = "데이터 로드에 실패했습니다."
          }
        })
    }
  }
}