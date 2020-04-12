package com.baptisterssl.dreemtest.view.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.baptisterssl.dreemtest.R
import com.baptisterssl.dreemtest.service.PlayerService
import com.baptisterssl.dreemtest.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_home_player.*
import kotlinx.android.synthetic.main.view_home_player_error.*

class HomeActivity : BaseActivity() {

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            HomeUseCaseProviderImpl(playerRepository)
        )
    }

    private lateinit var carouselAdapter: CarouselAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_home)

        observeViewState()
        initView()

        viewModel.onLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayerService.stop(this)
    }

    /** Private methods */

    private fun observeViewState() {
        viewModel.viewStateLiveData.observe(this) { viewState ->
            when (viewState) {
                is HomeViewModel.ViewState.Carousel -> {
                    carouselAdapter.update(viewState.audioFiles)
                    updateViewState(hasData = true)
                }
                HomeViewModel.ViewState.Progress -> {
                    updateViewState(isLoading = true)
                }
                HomeViewModel.ViewState.Error -> {
                    updateViewState(hasError = true)
                }
            }
        }
    }

    private fun initView() {
        initCarousel()
        buttonRetry.setOnClickListener { viewModel.onRetryCarouselLoading() }
    }

    private fun initCarousel() {
        carouselAdapter = CarouselAdapter()
        recyclerViewCarousel.run {
            adapter = carouselAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewCarousel)
    }

    private fun updateViewState(
        hasData: Boolean = false,
        hasError: Boolean = false,
        isLoading: Boolean = false
    ) {
        viewPlayer.isVisible = hasData && !hasError && !isLoading
        viewPlayerError.isVisible = !hasData && hasError && !isLoading
        progressPlayer.isVisible = isLoading
    }
}