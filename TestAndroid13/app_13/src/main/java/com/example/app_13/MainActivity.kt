package com.example.app_13

import android.annotation.SuppressLint
import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import androidx.core.os.LocaleListCompat
import com.example.app_13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("NewApi", "WrongConstant", "UnsafeOptInUsageError")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("ko")

            AppCompatDelegate.setApplicationLocales(appLocale)
        }

        binding.button2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val statusBarManager =
                    getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager

                statusBarManager.requestAddTileService(
                    ComponentName(
                        "com.example.app_13",
                        "com.example.app_13.MyTileService",
                    ),
                    "MyTile2",
                    Icon.createWithResource(this, R.drawable.ic_baseline_vibration_24),
                    {},
                    {}
                )
            } else {
                Toast.makeText(
                    this,
                    "`requestAddTileService` can only be called in Android 13/Tiramisu.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }


    }

}



