package com.abdullah.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    val id: Int,
    val username: String,
    val photo: String,
) : Parcelable