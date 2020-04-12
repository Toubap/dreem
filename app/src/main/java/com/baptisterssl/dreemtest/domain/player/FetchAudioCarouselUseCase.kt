package com.baptisterssl.dreemtest.domain.player

import com.baptisterssl.dreemtest.data.entity.AudioFile

interface FetchAudioCarouselUseCase {

    suspend fun execute(): List<AudioFile>
}