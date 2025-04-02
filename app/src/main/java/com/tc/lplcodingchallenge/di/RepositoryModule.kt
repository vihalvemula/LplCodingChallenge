package com.tc.lplcodingchallenge.di

import com.tc.lplcodingchallenge.domain.repo.PostsRepo
import com.tc.lplcodingchallenge.data.repoImpl.PostsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsPostsRepo(postsRepoImpl: PostsRepoImpl): PostsRepo
}