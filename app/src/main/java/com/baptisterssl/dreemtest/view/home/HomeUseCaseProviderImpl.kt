package com.baptisterssl.dreemtest.view.home

import com.baptisterssl.dreemtest.data.repository.PlayerRepository
import com.baptisterssl.dreemtest.domain.player.FetchAudioCarouselUseCase
import com.baptisterssl.dreemtest.domain.player.impl.FetchAudioCarouselUseCaseImpl
import kotlinx.coroutines.Dispatchers

class HomeUseCaseProviderImpl(private val playerRepository: PlayerRepository) :
    HomeUseCaseProvider {

    override fun fetchAudioCarousel(): FetchAudioCarouselUseCase =
        FetchAudioCarouselUseCaseImpl(playerRepository, Dispatchers.IO)
}