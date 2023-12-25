package com.abdullah.githubexpose.presentation.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abdullah.core.domain.usecase.GithubUser.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    fun getFollow(username: String, isFollower: Boolean) = githubUserUseCase.getFollow(username, isFollower).asLiveData()
}