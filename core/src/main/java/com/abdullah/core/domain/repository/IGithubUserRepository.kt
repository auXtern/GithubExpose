package com.abdullah.core.domain.repository

import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubDetailUser
import com.abdullah.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface IGithubUserRepository {

    fun getUsers(username: String): Flow<Resource<List<GithubUser>?>>

    fun getFollow(username: String, isFollower: Boolean): Flow<Resource<List<GithubUser>?>>

    fun getDetailUser(username: String): Flow<Resource<GithubDetailUser?>>

    fun getFavoriteUser(): Flow<List<GithubUser>>

    fun setFavoriteUser(githubUser: GithubUser, state: Boolean)

   fun getFavoriteUserByUsername(username: String): Flow<GithubUser?>

}