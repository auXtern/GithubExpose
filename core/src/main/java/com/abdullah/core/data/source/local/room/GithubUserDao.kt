package com.abdullah.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abdullah.core.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubUser(githubUser: GithubUserEntity)

    @Delete
    fun deleteGithubUser(githubUser: GithubUserEntity)

    @Query("SELECT * FROM github_user WHERE username = :username LIMIT 1")
    fun getUserByUsername(username: String): Flow<GithubUserEntity?>

    @Query("SELECT * FROM github_user ORDER BY username")
    fun getAllUser(): Flow<List<GithubUserEntity>?>
}