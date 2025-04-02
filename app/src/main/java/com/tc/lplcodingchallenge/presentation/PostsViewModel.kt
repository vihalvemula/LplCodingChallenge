package com.tc.lplcodingchallenge.presentation

import android.net.Uri
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

    private val _selectedImages = MutableStateFlow<Map<Int, Uri?>>(emptyMap())
    val selectedImages:StateFlow<Map<Int,Uri?>> = _selectedImages

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

    fun updateImageIcon(index: Int, uri: Uri?) {
        _selectedImages.value = selectedImages.value.toMutableMap().apply {
            put(index, uri)
        }
    }


}