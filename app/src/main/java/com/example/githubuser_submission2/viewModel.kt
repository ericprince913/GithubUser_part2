package com.example.githubuser_submission2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class searchViewModel : ViewModel() {
    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()
    fun setSearchUsers(query: String){
        val client = ApiConfig.getApiService().getSearchUsers(query)
            client.enqueue(object: Callback<DataUser> {
                override fun onResponse(
                    call: Call<DataUser>,
                    response: Response<DataUser>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items as ArrayList<ItemsItem>?)
                    }
                }

                override fun onFailure(call: Call<DataUser>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<ItemsItem>> {
        return listUsers
    }

}

class detailViewModel : ViewModel() {
    val user = MutableLiveData<DetailDataUser>()

    fun setUserDetail(username: String?){
        val client = ApiConfig.getApiService()
            .getUserDetail(username.toString())
            .enqueue(object : Callback<DetailDataUser> {
                override fun onResponse(
                    call: Call<DetailDataUser>,
                    response: Response<DetailDataUser>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailDataUser>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<DetailDataUser> {
        return user
    }
}

class followerViewModel : ViewModel() {
    val Followers = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<ItemsItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    if (response.isSuccessful){
                        Followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowers(): LiveData<ArrayList<ItemsItem>> {
        return Followers
    }
}

class followingViewModel : ViewModel() {
    val Following = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowers(username: String){
        val client = ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<ItemsItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    if (response.isSuccessful){
                        Following.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<ItemsItem>> {
        return Following
    }
}