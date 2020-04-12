package com.baptisterssl.dreemtest.data.entity

data class AudioFile(
    val id: Int,
    val title: String,
    val description: String,
    val image: String,
    val preview: String?,
    val audio: String?,
    val order: Int
)