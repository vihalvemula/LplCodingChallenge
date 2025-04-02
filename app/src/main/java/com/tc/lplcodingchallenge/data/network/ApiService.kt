package com.tc.lplcodingchallenge.data.network

import com.tc.lplcodingchallenge.data.model.PostsItemModel
import retrofit2.Response
import retrofit2.http.GET



//https://jsonplaceholder.typicode.com/posts/1/comments


interface ApiService {
    @GET("posts/1/comments")
    suspend fun getPosts():Response<List<PostsItemModel>>
}