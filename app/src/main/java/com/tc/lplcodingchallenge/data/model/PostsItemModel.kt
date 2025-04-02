package com.tc.lplcodingchallenge.data.model


import com.google.gson.annotations.SerializedName

data class PostsItemModel(
    @SerializedName("body")
    val body: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("postId")
    val postId: Int? = null
)