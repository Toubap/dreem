package com.baptisterssl.dreemtest.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baptisterssl.dreemtest.data.entity.AudioFile
import kotlinx.coroutines.launch

class HomeViewModel(private val useCaseProvider: HomeUseCaseProvider) : ViewModel() {

    sealed class ViewState {
        data class Carousel(val audioFiles: List<AudioFile>) : ViewState()
        object Progress : ViewState()
        object Error : ViewState()
    }

    private val _viewStateLiveDate = MutableLiveData<ViewState>()

    val viewStateLiveData: LiveData<ViewState>
        get() = _viewStateLiveDate

    fun onLoad() {
        loadCarousel()
    }

    fun onRetryCarouselLoading() {
        loadCarousel()
    }

    /** Private methods */

    private fun loadCarousel() {
        viewModelScope.launch {
            try {
                updateViewState(ViewState.Progress)
                val result = useCaseProvider.fetchAudioCarousel().execute()
                updateViewState(ViewState.Carousel(result))
            } catch (exception: Exception) {
                updateViewState(ViewState.Error)
            }
        }
    }

    private fun updateViewState(viewState: ViewState) {
        _viewStateLiveDate.value = viewState
    }
}