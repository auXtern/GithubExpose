package com.abdullah.core.di

import com.abdullah.core.data.AppSettingRepository
import com.abdullah.core.data.GithubUserRepository
import com.abdullah.core.domain.repository.IAppSettingRepository
import com.abdullah.core.domain.repository.IGithubUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class, PrefModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideGithubUserRepository(githubUserRepository: GithubUserRepository): IGithubUserRepository

    @Binds
    abstract fun provideAppSettingRepository(appSettingRepository: AppSettingRepository): IAppSettingRepository

}