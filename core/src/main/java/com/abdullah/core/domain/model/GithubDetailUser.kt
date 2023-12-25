package com.abdullah.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubDetailUser(
    val id: Int,
    val username: String,
    val photo: String,
    val name: String,
    val following: Int,
    val followers: Int,
) : Parcelable