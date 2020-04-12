package com.baptisterssl.dreemtest

import android.app.Application
import com.baptisterssl.dreemtest.data.remote.DreemAPIService
import com.baptisterssl.dreemtest.data.repository.PlayerRepository
import com.baptisterssl.dreemtest.data.repository.impl.PlayerRepositoryImpl
import kotlinx.coroutines.Dispatchers

open class App : Application() {

    lateinit var playerRepository: PlayerRepository

    open val dreemAPIService: DreemAPIService
        get() = DreemAPIService.createService(getString(R.string.api_service_endpoint))

    override fun onCreate() {
        super.onCreate()

        playerRepository = PlayerRepositoryImpl(dreemAPIService, Dispatchers.IO)
    }
}