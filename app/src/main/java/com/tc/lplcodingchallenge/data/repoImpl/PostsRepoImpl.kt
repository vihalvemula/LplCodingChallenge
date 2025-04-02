package com.tc.lplcodingchallenge.data.repoImpl

import com.tc.lplcodingchallenge.data.model.PostsItemModel
import com.tc.lplcodingchallenge.data.network.ApiService
import com.tc.lplcodingchallenge.domain.repo.PostsRepo
import retrofit2.Response
import javax.inject.Inject

class PostsRepoImpl @Inject constructor(private val apiService: ApiService): PostsRepo {
    override suspend fun getAllPosts(): Response<List<PostsItemModel>> {
        return apiService.getPosts()
    }
}