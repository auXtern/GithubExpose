package com.abdullah.core.utils

import com.abdullah.core.data.source.local.entity.GithubUserEntity
import com.abdullah.core.data.source.remote.response.DetailUserResponse
import com.abdullah.core.data.source.remote.response.ItemsItem
import com.abdullah.core.domain.model.GithubDetailUser
import com.abdullah.core.domain.model.GithubUser

object DataMapper {
    fun mapResponsesToDomains(input: List<ItemsItem>): List<GithubUser> {
        val userList = ArrayList<GithubUser>()
        input.map {
            val githubUser = GithubUser(
                id = it.id,
                username = it.login,
                photo = it.avatarUrl
            )
            userList.add(githubUser)
        }
        return userList
    }

    fun mapResponseToDomain(input: DetailUserResponse) =
        GithubDetailUser(
            id = input.id,
            username = input.login,
            photo = input.avatarUrl,
            name = input.name,
            following = input.following,
            followers = input.followers
        )

    fun mapEntitiesToDomains(input: List<GithubUserEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                id = it.id,
                username = it.username,
                photo = it.photo,
            )
        }

    fun mapDomainToEntity(input: GithubUser) =
       GithubUserEntity(
           id = input.id,
           username = input.username,
           photo = input.photo,
        )

    fun mapEntityToDomain(entity: GithubUserEntity) =
        GithubUser(
            id = entity.id,
            username = entity.username,
            photo = entity.photo,
        )
}