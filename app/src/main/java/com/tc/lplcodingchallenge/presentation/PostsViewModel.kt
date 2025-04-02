package com.tc.lplcodingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.lplcodingchallenge.data.model.PostsItemModel
import com.tc.lplcodingchallenge.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostsViewModel @Inject constructor(private val getPostsUseCase: GetPostsUseCase):ViewModel() {
    private val _posts=MutableStateFlow<UiState<List<PostsItemModel>>>(UiState.LOADING)
    val posts:StateFlow<UiState<List<PostsItemModel>>> = _posts

    init{
        getAllPosts()
    }

    fun getAllPosts(){
        viewModelScope.launch(Dispatchers.IO) {
            getPostsUseCase().collect{
                _posts.value = it
            }
        }
    }


}