package com.baptisterssl.dreemtest.domain.player.impl

import com.baptisterssl.dreemtest.AUDIO_FILES
import com.baptisterssl.dreemtest.data.repository.PlayerRepository
import com.baptisterssl.dreemtest.domain.player.FetchAudioCarouselUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchAudioCarouselUseCaseImplTest {

    private lateinit var useCase: FetchAudioCarouselUseCase

    private val playerRepository = mock<PlayerRepository>()

    @Before
    fun setUp() {
        useCase = FetchAudioCarouselUseCaseImpl(playerRepository, Dispatchers.Unconfined)
    }

    @Test
    fun fetch_Sorted_Success(): Unit = runBlocking {
        val expect = AUDIO_FILES
        whenever(playerRepository.fetchAudioCarousel()).thenReturn(expect)

        val result = useCase.execute()

        assertTrue(result[0].order == 1)
        assertEquals(result, expect.sortedBy { it.order })
    }
}