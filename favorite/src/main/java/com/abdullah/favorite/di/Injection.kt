package com.abdullah.favorite.di

import android.content.Context
import com.abdullah.core.data.GithubUserRepository
import com.abdullah.core.data.source.local.LocalDataSource
import com.abdullah.core.data.source.remote.RemoteDataSource
import com.abdullah.core.data.source.remote.network.ApiConfig
import com.abdullah.core.di.DatabaseModule
import com.abdullah.core.domain.usecase.GithubUser.GithubUserInteractor
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase
import com.abdullah.core.utils.AppExecutors

object Injection {
    fun provideGithubUserUseCase(context: Context) : GithubUserUseCase {
        val apiService = ApiConfig.provideApiService()
        val remoteDataSource = RemoteDataSource(apiService)

        val githubUserDao = DatabaseModule().provideDatabase(context).githubUserDao()
        val localDataSource = LocalDataSource(githubUserDao)

        val appExecutors = AppExecutors()

        val githubUserRepository = GithubUserRepository(remoteDataSource, localDataSource, appExecutors)
        return GithubUserInteractor(githubUserRepository)
    }
}