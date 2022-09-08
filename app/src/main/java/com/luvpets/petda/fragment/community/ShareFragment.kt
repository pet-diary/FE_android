package com.luvpets.petda.fragment.community

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.activities.CommunityDetailActivity
import com.luvpets.petda.adapter.CommunityShareAdapter
import com.luvpets.petda.databinding.FragmentCommunityShareBinding
import com.luvpets.petda.dto.ShareDto
import com.luvpets.petda.data.model.PetInfoEntity
import com.luvpets.petda.room.PetInfoDB
import com.luvpets.petda.data.service.Instance
import com.luvpets.petda.data.service.ShareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    loadingContent.loadingContent.visibility = View.VISIBLE
    binding.loadingContent.loadingPaw.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.loading_paw))
  }
  private fun getShareListFromApi() {
    // retrofit 생성
//    val retrofit = Instance().instance
//
//    // 호출
//    retrofit.create(ShareService::class.java).also {
//      it.getShareList()
//        .enqueue(object: Callback<ShareDto> {
//          // 성공시
//          override fun onResponse(call: Call<ShareDto>, response: Response<ShareDto>) {
//            if (response.isSuccessful.not()) return
//            loadingContent.loadingContent.visibility = View.GONE
//            response.body()?.let { dto ->
//              shareAdapter.submitList(dto.items)
//            }
//
//            val r = Runnable {
//              val tempEntity = PetInfoEntity(0, "", "가을", "2019.07.15", 3, 6, "스노우슈")
//              context?.let { it1 -> PetInfoDB.getInstance(it1).dao().addPetInfo(tempEntity) }
//            }
//            val thread = Thread(r)
//            thread.start()
//          }
//
//          // 실패시
//          override fun onFailure(call: Call<ShareDto>, t: Throwable) {
//            binding.loadingContent.loadingText.text = "데이터 로드에 실패했습니다."
//          }
//        })
//    }
  }
}