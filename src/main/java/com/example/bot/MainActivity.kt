package com.example.bot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bot.ui.theme.BotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // for hide upper window
        WindowCompat.setDecorFitsSystemWindows(window,false);

        WindowInsetsControllerCompat(window,window.decorView).apply{
            hide(android.view.WindowInsets.Type.statusBars())
        }

        //viewmodel instance
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            BotTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChatPage(modifier = Modifier.padding(innerPadding),chatViewModel)
                }
            }
        }
    }
}
