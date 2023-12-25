package com.abdullah.core.data

import com.abdullah.core.data.source.local.LocalDataSource
import com.abdullah.core.data.source.remote.RemoteDataSource
import com.abdullah.core.data.source.remote.network.ApiResponse
import com.abdullah.core.domain.model.GithubDetailUser
import com.abdullah.core.domain.model.GithubUser
import com.abdullah.core.domain.repository.IGithubUserRepository
import com.abdullah.core.utils.AppExecutors
import com.abdullah.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IGithubUserRepository {
    override fun getUsers(username: String): Flow<Resource<List<GithubUser>?>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getUsers(username).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapResponsesToDomains(response.data)))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(null))
            }
        }
    }

    override fun getFollow(
        username: String,
        isFollower: Boolean
    ): Flow<Resource<List<GithubUser>?>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getFollow(username, isFollower).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapResponsesToDomains(response.data)))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(null))
            }
        }
    }

    override fun getDetailUser(username: String): Flow<Resource<GithubDetailUser?>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getDetailUser(username).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapResponseToDomain(response.data)))
            }

            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }

            is ApiResponse.Empty -> {
                emit(Resource.Success(null))
            }
        }
    }

    override fun getFavoriteUser(): Flow<List<GithubUser>> {
        return localDataSource.getAllUser().map { DataMapper.mapEntitiesToDomains(it!!) }
    }

    override fun setFavoriteUser(githubUser: GithubUser, state: Boolean) {
        appExecutors.diskIO().execute {
            if (state) {
                localDataSource.insertGithubUser(DataMapper.mapDomainToEntity(githubUser))
            } else {
                localDataSource.deleteGithubUser(DataMapper.mapDomainToEntity(githubUser))
            }
        }
    }

    override fun getFavoriteUserByUsername(username: String): Flow<GithubUser?> {
        return localDataSource.getUserByUsername(username).map {
            if(it != null){
                DataMapper.mapEntityToDomain(it)
            }else{
                null
            }
        }
    }
}