package com.baptisterssl.dreemtest.view.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.baptisterssl.dreemtest.AUDIO_FILES
import com.baptisterssl.dreemtest.domain.player.FetchAudioCarouselUseCase
import com.baptisterssl.dreemtest.utils.CoroutineTestRule
import com.baptisterssl.dreemtest.view.home.HomeViewModel.ViewState
import com.jraska.livedata.TestLifecycle
import com.jraska.livedata.TestObserver
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel

    private val useCaseProvider = mock<HomeUseCaseProvider>()
    private val fetchAudioCarouselUseCase = mock<FetchAudioCarouselUseCase>()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(useCaseProvider)

        whenever(useCaseProvider.fetchAudioCarousel()).thenReturn(fetchAudioCarouselUseCase)
    }

    @Test
    fun fetchCarousel_Success() {
        val expect = AUDIO_FILES

        val testObserverViewState = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchAudioCarouselUseCase.execute()).thenReturn(expect)
            viewModel.viewStateLiveData.observe(testLifecycle, testObserverViewState)
            testLifecycle.resume()
        }

        viewModel.onLoad()

        testObserverViewState
            .assertValueHistory(ViewState.Progress, ViewState.Carousel(expect))
    }

    @Test
    fun fetchCarousel_Error() {
        val expect = RuntimeException()

        val testObserverViewState = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchAudioCarouselUseCase.execute()).thenThrow(expect)
            viewModel.viewStateLiveData.observe(testLifecycle, testObserverViewState)
            testLifecycle.resume()
        }

        viewModel.onLoad()

        testObserverViewState
            .assertValueHistory(ViewState.Progress, ViewState.Error)
    }

    @Test
    fun fetchCarousel_Retry_Success() {
        val expect = AUDIO_FILES

        val testObserverViewState = TestObserver.create<ViewState>()
        val testLifecycle = TestLifecycle.initialized()

        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(fetchAudioCarouselUseCase.execute()).thenReturn(expect)
            viewModel.viewStateLiveData.observe(testLifecycle, testObserverViewState)
            testLifecycle.resume()
        }

        viewModel.onRetryCarouselLoading()

        testObserverViewState
            .assertValueHistory(ViewState.Progress, ViewState.Carousel(expect))
    }
}