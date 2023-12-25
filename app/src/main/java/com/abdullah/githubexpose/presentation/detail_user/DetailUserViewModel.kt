package com.abdullah.githubexpose.presentation.detail_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailUserViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    fun  getDetailUser(username: String) = githubUserUseCase.getDetailUser(username).asLiveData()

    fun  getFavoriteUserByUsername(username: String) = githubUserUseCase.getFavoriteUserByUsername(username).asLiveData()

    fun  setFavoriteUser(githubUser: GithubUser, state: Boolean) {
        githubUserUseCase.setFavoriteUser(githubUser, state)
    }
}