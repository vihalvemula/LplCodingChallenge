package com.tc.lplcodingchallenge.domain.usecase

import com.tc.lplcodingchallenge.data.model.PostsItemModel
import com.tc.lplcodingchallenge.presentation.UiState
import com.tc.lplcodingchallenge.domain.repo.PostsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postsRepo: PostsRepo) {
    operator fun invoke(): Flow<UiState<List<PostsItemModel>>> = flow {
        emit(UiState.LOADING)

        val response = postsRepo.getAllPosts()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            emit(UiState.SUCCESS(body))
        } else {
            emit(UiState.ERROR(Exception("Unsuccessful response or empty body")))
        }
    }.catch { e ->
        emit(UiState.ERROR(Exception(e)))
    }
}