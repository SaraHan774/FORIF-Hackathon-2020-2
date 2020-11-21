package com.gahee.hotchoco

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

    val marshList : MutableLiveData<ArrayList<MarshMallow>> = MutableLiveData()
    val list : ArrayList<MarshMallow> = arrayListOf()

    val isFirstOpen : MutableLiveData<Boolean> = MutableLiveData()

    fun updateList(input : MarshMallow){
        list.add(input)
        marshList.postValue(list)
    }

    companion object{
        const val TAG = "MainViewModel"
    }
}