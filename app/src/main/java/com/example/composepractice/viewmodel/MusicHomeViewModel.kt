package com.example.composepractice.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.example.composepractice.MusicSettings
import com.example.composepractice.ViewState
import com.example.composepractice.data.*
import com.example.composepractice.db.MusicDatabase
import com.example.composepractice.http.NetMusicApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject

@HiltViewModel
class MusicHomeViewModel @Inject constructor(
    private val netEaseMusicApi: NetMusicApi,
    private val musicDb: MusicDatabase,
    private val musicSettings: DataStore<MusicSettings>
):ViewModel(){
    //在ViewModel内部使用了private的MutableLiveData实例，但对外暴露的是LiveData类型
    //LiveData在实体类里可以通知指定某个字段的数据更新；
    //MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段。
    private val _userState: MutableStateFlow<MusicHomeState> =
        MutableStateFlow(MusicHomeState.Splash)
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Normal)
    private val _discoveryData: MutableStateFlow<DiscoveryViewData> =
        MutableStateFlow(DiscoveryViewData())
    //个人歌单
    private val _myPlayList = MutableStateFlow<ViewState>(ViewState.Normal)

    val userState: StateFlow<MusicHomeState> = _userState
    val viewState: StateFlow<ViewState> = _viewState
    val discoveryData: StateFlow<DiscoveryViewData> = _discoveryData
    val myPlayList: StateFlow<ViewState> = _myPlayList

    init {
        busyWork(_viewState){
            val userAccountId = musicSettings.data.firstOrNull()?.userAccountId
            if (userAccountId != null){
                musicDb.userDao().findUser(userAccountId.toLong())?.run {
                    _userState.emit(MusicHomeState.Login(user = this))
                    loadDiscoveryPage()
                    loadMyMusicPage(user = this)
                    return@busyWork ViewState.Normal
                }
            }
            val response = netEaseMusicApi.refreshLogin().awaitResponse()
            _userState.emit(if (response.isSuccessful) MusicHomeState.Login() else MusicHomeState.Visitor)
        }
    }

    /**
     * 登录
     */
    fun login(phone: String, password: String) = busyWork(_viewState) {
        val user = netEaseMusicApi.cellphoneLogin(phone, password).awaitResponse()
            .takeIf { it.isSuccessful }?.body()
        user?.takeIf { it.isValid() }?.run {
            //保存已登录用户
            user.accountId = user.account.id
            musicSettings.updateData { it.toBuilder().setUserAccountId(user.accountId).build() }
            musicDb.userDao().insert(user = user)
            _userState.emit(MusicHomeState.Login(user))
            loadDiscoveryPage()
            loadMyMusicPage(user = this)
        } ?: throw Throwable("登录失败")
    }

    /**
     * “我的”页面载入
     */
    private fun loadMyMusicPage(user: User?) = busyWork(_myPlayList) {
        return@busyWork MyPlayListLoaded(
            netEaseMusicApi.selfPlaylistData(user?.accountId ?: 0).await().playlist, user
        )
    }

    private fun loadDiscoveryPage() = busyWork(_viewState) {
        val bannerCall = netEaseMusicApi.banners()
        val recommendDataCall = netEaseMusicApi.recommendResource()
        val topAlbumDataCall = netEaseMusicApi.topAlbums()
        val mvListCall = netEaseMusicApi.topMVs()
        _discoveryData.tryEmit(DiscoveryViewData().apply {
            try {
                bannerList = bannerCall.await().banners.orEmpty()
            } catch (_: Throwable) {
            }
            try {
                recommendList = recommendDataCall.await().recommend
            } catch (_: Throwable) {
            }
            //新碟接口由于目前API的limit无效（无法分页返回了所有的月数据）导致数据量过大，因此暂时不使用
//            try {
//                newAlbumList = topAlbumDataCall.await().weekData.orEmpty()
//            } catch (_: Throwable) {
//            }
            try {
                topMVList = mvListCall.await().data.orEmpty()
            } catch (_: Throwable) {
            }
        })
    }


}

/**
 * 个人歌单载入完毕
 */
class MyPlayListLoaded(playList: List<PlayList>, user: User?) : ViewState() {
    //我创建的歌单
    val selfCreates = playList.filter { it.creator?.userId == user?.accountId }

    //收藏的歌单
    val collects = playList.filterNot { it.creator?.userId == user?.accountId }
}

/**
 * 发现页数据
 */
class DiscoveryViewData {
    var bannerList: List<BannerData.Banner> = emptyList()
    var recommendList: List<Recommend> = emptyList()
    var newAlbumList: List<Album> = emptyList()
    var topMVList: List<MVData.MV> = emptyList()
}


/**
 * 音乐主页状态
 */
sealed class MusicHomeState {
    object Splash : MusicHomeState()
    object Visitor : MusicHomeState()
    class Login(val user: User? = null) : MusicHomeState()
}