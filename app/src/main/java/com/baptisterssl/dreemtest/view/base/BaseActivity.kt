package com.baptisterssl.dreemtest.view.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.baptisterssl.dreemtest.App
import com.baptisterssl.dreemtest.data.repository.PlayerRepository

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit var playerRepository: PlayerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(application as App) {
            this@BaseActivity.playerRepository = playerRepository
        }
    }
}