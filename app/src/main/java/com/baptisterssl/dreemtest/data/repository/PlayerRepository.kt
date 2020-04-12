package com.baptisterssl.dreemtest.data.repository

import com.baptisterssl.dreemtest.data.entity.AudioFile

interface PlayerRepository {

    suspend fun fetchAudioCarousel(): List<AudioFile>
}