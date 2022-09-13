package com.example.app_13

import android.os.Build
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.N)
class MyTileService : TileService() {

    override fun onTileAdded() {
    }

    override fun onTileRemoved() {
    }

    override fun onStartListening() {
    }

    override fun onStopListening() {
    }

    override fun onClick() {
    }

    companion object {
        val TAG = MyTileService::class.java.simpleName
    }
}