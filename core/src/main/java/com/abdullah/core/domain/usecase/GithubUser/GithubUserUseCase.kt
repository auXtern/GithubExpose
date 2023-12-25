package com.abdullah.core.domain.usecase.GithubUser

import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubDetailUser
import com.abdullah.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    fun getUsers(username: String): Flow<Resource<List<GithubUser>?>>

    fun getFollow(username: String, isFollower: Boolean): Flow<Resource<List<GithubUser>?>>

    fun getDetailUser(username: String): Flow<Resource<GithubDetailUser?>>

    fun getFavoriteUser(): Flow<List<GithubUser>>

    fun getFavoriteUserByUsername(username: String): Flow<GithubUser?>

    fun setFavoriteUser(githubUser: GithubUser, state: Boolean)
}