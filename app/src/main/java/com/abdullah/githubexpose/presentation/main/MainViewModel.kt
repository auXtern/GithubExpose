package com.abdullah.githubexpose.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.core.domain.usecase.AppSetting.AppSettingUseCase
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase, private val appSettingUseCase: AppSettingUseCase) : ViewModel() {
    fun getUsers(username: String): LiveData<Resource<List<GithubUser>?>> = githubUserUseCase.getUsers(username).asLiveData()

    fun getThemeSettings(): LiveData<Boolean> {
        return appSettingUseCase.getThemeSetting().asLiveData()
    }
}