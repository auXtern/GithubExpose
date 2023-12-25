package com.abdullah.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase

class FavoriteUserViewModel (private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    val favoriteUsers = githubUserUseCase.getFavoriteUser().asLiveData()
}