package com.baptisterssl.dreemtest.domain.player.impl

import com.baptisterssl.dreemtest.data.entity.AudioFile
import com.baptisterssl.dreemtest.data.repository.PlayerRepository
import com.baptisterssl.dreemtest.domain.player.FetchAudioCarouselUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchAudioCarouselUseCaseImpl(
    private val playerRepository: PlayerRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) : FetchAudioCarouselUseCase {

    override suspend fun execute(): List<AudioFile> =
        withContext(coroutineDispatcher) {
            playerRepository.fetchAudioCarousel().sortedBy { it.order }
        }
}