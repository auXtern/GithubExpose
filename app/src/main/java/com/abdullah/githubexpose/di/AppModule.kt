package com.abdullah.githubexpose.di

import com.abdullah.core.domain.usecase.AppSetting.AppSettingInteractor
import com.abdullah.core.domain.usecase.AppSetting.AppSettingUseCase
import com.abdullah.core.domain.usecase.GithubUser.GithubUserInteractor
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideGithubUserUseCase(githubUserInteractor: GithubUserInteractor): GithubUserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideAppSettingUseCase(appSettingInteractor: AppSettingInteractor): AppSettingUseCase

}