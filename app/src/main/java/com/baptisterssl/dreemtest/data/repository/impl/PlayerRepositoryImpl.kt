package com.baptisterssl.dreemtest.data.repository.impl

import com.baptisterssl.dreemtest.data.entity.AudioFile
import com.baptisterssl.dreemtest.data.remote.DreemAPIService
import com.baptisterssl.dreemtest.data.repository.PlayerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PlayerRepositoryImpl(
    private val dreemAPIService: DreemAPIService,
    private val coroutineDispatcher: CoroutineDispatcher
) : PlayerRepository {

    override suspend fun fetchAudioCarousel(): List<AudioFile> =
        withContext(coroutineDispatcher) {
            dreemAPIService.fetchAudioCarousel().contents
        }
}