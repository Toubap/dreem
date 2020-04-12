package com.baptisterssl.dreemtest

import com.baptisterssl.dreemtest.data.remote.DreemAPIService

class InstrumentedApp : App() {

    override val dreemAPIService: DreemAPIService
        get() = DreemAPIService.createService("http://localhost:8080/")
}