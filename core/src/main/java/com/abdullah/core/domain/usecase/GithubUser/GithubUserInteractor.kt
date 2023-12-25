package com.abdullah.core.domain.usecase.GithubUser

import com.abdullah.core.data.Resource
import com.abdullah.core.domain.model.GithubDetailUser
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.core.domain.repository.IGithubUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubUserInteractor @Inject constructor(private val githubUserRepository: IGithubUserRepository):
    GithubUserUseCase {
    override fun getUsers(username: String): Flow<Resource<List<GithubUser>?>> = githubUserRepository.getUsers(username)

    override fun getFollow(
        username: String,
        isFollower: Boolean
    ): Flow<Resource<List<GithubUser>?>> = githubUserRepository.getFollow(username, isFollower)

    override fun getDetailUser(username: String): Flow<Resource<GithubDetailUser?>> = githubUserRepository.getDetailUser(username)

    override fun getFavoriteUser(): Flow<List<GithubUser>> = githubUserRepository.getFavoriteUser()

    override fun getFavoriteUserByUsername(username: String): Flow<GithubUser?> = githubUserRepository.getFavoriteUserByUsername(username)

    override fun setFavoriteUser(githubUser: GithubUser, state: Boolean) = githubUserRepository.setFavoriteUser(githubUser, state)

}