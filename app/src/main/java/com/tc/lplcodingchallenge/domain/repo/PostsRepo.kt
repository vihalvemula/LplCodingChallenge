package com.tc.lplcodingchallenge.domain.repo

import com.tc.lplcodingchallenge.data.model.PostsItemModel
import retrofit2.Response

interface PostsRepo {
    suspend fun getAllPosts():Response<List<PostsItemModel>>
}