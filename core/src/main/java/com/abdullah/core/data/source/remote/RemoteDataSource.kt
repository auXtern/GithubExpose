package com.abdullah.core.data.source.remote

import android.util.Log
import com.abdullah.core.data.source.remote.network.ApiResponse
import com.abdullah.core.data.source.remote.network.ApiService
import com.abdullah.core.data.source.remote.response.DetailUserResponse
import com.abdullah.core.data.source.remote.response.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers(username: String): Flow<ApiResponse<List<ItemsItem>>> {
        return flow{
            try {
                val response = apiService.getUsers(username)
                val dataArray = response.items
                if (dataArray != null){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollow(username: String, isFollower: Boolean): Flow<ApiResponse<List<ItemsItem>>> {
        return flow{
            try {
                val response = if(isFollower){
                    apiService.getFollowers(username)
                }else{
                    apiService.getFollowing(username)
                }
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailUser(username: String): Flow<ApiResponse<DetailUserResponse>> {
        return flow{
            try {
                val response = apiService.getDetailUser(username)
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}