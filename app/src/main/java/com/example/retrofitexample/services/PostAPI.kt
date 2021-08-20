package com.example.retrofitexample.services


import com.example.retrofitexample.model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface PostAPI {

    //https://jsonplaceholder.typicode.com/posts
    @GET("posts")
    fun getPosts() : Call<List<PostModel>>



}