package com.example.composepractice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val savedState:SavedStateHandle) :ViewModel() {

    /**
     * Activity意外销毁的情况分为两种：
     * 由于屏幕旋转等配置更改的原因导致 Activity 被销毁,
     * 由于系统资源限制导致 Activity 被销毁
     *
     * 对于第一种，可以使用ViewModel来解决
     * 对于第二种，ViewModel是无法保留下来，activity重建后会得到一个新的ViewModel,因此需要依赖于activity原生提供的数据保存和恢复机制
     * 1、onSaveInstanceState  通过Bundle插入键值对来保存数据，但是Bundle有着容量限制，不适合存储大量的数据，而且是通过序列化到磁盘来进行保存
     * 2、SavedStateHandle   SavedStateHandle 负责接收 Activity 重建时缓存的数据，并将缓存的数据以 LiveData 的方式暴露给开发者，
     * 也向外部提供了获取最新键值对的入口
     *
     * SavedStateRegistry 则负责将 Activity 原生的数据缓存机制串联起来，向外部暴露了提交数据和消费数据的入口。SavedStateHandle 缓存的数据就需要提交给
     * SavedStateRegistry，SavedStateRegistry 缓存的数据最终也需要交由 SavedStateHandle 来消费。
     */

    private val HOME_PAGE_SELECTED_INDEX = "home_page_selected_index"

    private val mSelectLiveData = MutableLiveData<Int>()

    fun getSelectedIndex():LiveData<Int> {
        if (mSelectLiveData.value == null){
            val index = savedState.get<Int>(HOME_PAGE_SELECTED_INDEX) ?: 0
            mSelectLiveData.postValue(index)
        }
        return mSelectLiveData
    }

    fun saveSelectIndex(selectIndex:Int){
        savedState.set(HOME_PAGE_SELECTED_INDEX,selectIndex)
        mSelectLiveData.value = selectIndex
    }
}