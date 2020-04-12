package com.baptisterssl.dreemtest.view.home

import com.baptisterssl.dreemtest.domain.player.FetchAudioCarouselUseCase

interface HomeUseCaseProvider {

    fun fetchAudioCarousel(): FetchAudioCarouselUseCase
}