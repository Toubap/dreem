package com.baptisterssl.dreemtest.view.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.baptisterssl.dreemtest.data.remote.AudioFileResponseBody
import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var scenario = ActivityTestRule(HomeActivity::class.java, false, false)

    private val homeScreen = HomeScreen()
    private val mockServer = MockWebServer()

    private class SuccessDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
            when (request.path) {
                "/test2.json" -> MockResponse().setResponseCode(200)
                    .setBody(Gson().toJson(AudioFileResponseBody(AUDIO_FILES)))
                else -> MockResponse().setResponseCode(400)
            }
    }

    private class ErrorDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse =
            when (request.path) {
                else -> MockResponse().setResponseCode(400)
            }
    }

    @Before
    fun setUp() {
        mockServer.start(8080)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun fetchCarousel_Success() {
        mockServer.dispatcher = SuccessDispatcher()
        scenario.launchActivity(null)

        homeScreen {
            viewPlayer.isVisible()
            recyclerViewCarousel.isVisible()
            progressPlayer.isGone()
        }
    }

    @Test
    fun fetchCarousel_Error() {
        mockServer.dispatcher = ErrorDispatcher()
        scenario.launchActivity(null)

        homeScreen {
            viewPlayerError.isVisible()
            progressPlayer.isGone()
        }
    }
}