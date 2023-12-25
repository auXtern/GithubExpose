package com.abdullah.core.data.source.local

import com.abdullah.core.data.source.local.entity.GithubUserEntity
import com.abdullah.core.data.source.local.room.GithubUserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val githubUserDao: GithubUserDao) {

    fun insertGithubUser(githubUser: GithubUserEntity) =
        githubUserDao.insertGithubUser(githubUser)

    fun deleteGithubUser(githubUser: GithubUserEntity) =
        githubUserDao.deleteGithubUser(githubUser)

    fun getUserByUsername(username: String): Flow<GithubUserEntity?> =
        githubUserDao.getUserByUsername(username)

    fun getAllUser(): Flow<List<GithubUserEntity>?> =
        githubUserDao.getAllUser()


}