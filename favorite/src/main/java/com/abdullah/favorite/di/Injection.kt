package com.abdullah.favorite.di

import android.content.Context
import com.abdullah.core.data.GithubUserRepository
import com.abdullah.core.data.source.local.LocalDataSource
import com.abdullah.core.data.source.local.room.GithubUserDatabase
import com.abdullah.core.data.source.remote.RemoteDataSource
import com.abdullah.core.data.source.remote.network.ApiConfig
import com.abdullah.core.domain.usecase.GithubUser.GithubUserInteractor
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase
import com.abdullah.core.utils.AppExecutors

object Injection {
    fun provideGithubUserUseCase(context: Context) : GithubUserUseCase {
        val apiClient = ApiConfig.provideOkHttpClient()
        val apiService = ApiConfig.provideApiService(apiClient)
        val remoteDataSource = RemoteDataSource(apiService)

        val githubUserDao = GithubUserDatabase.getInstance(context).githubUserDao()
        val localDataSource = LocalDataSource(githubUserDao)

        val appExecutors = AppExecutors()

        val githubUserRepository = GithubUserRepository(remoteDataSource, localDataSource, appExecutors)
        return GithubUserInteractor(githubUserRepository)
    }
}