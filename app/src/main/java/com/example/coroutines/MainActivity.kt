package com.example.coroutines

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
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutines.ui.theme.CoroutinesTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesTheme {
            }
        }

        GlobalScope.launch {
            val bird1 = launch {
                repeat(4) {
                    delay(1000)
                    println("Coo")
                }
            }
            val bird2 = launch {
                repeat(4) {
                    delay(2000)
                    println("Caw")
                }
            }
            val bird3 = launch {
                repeat(4) {
                    delay(3000)
                    println("Chirp")
                }
            }

            joinAll(bird1, bird2, bird3)
            println("Finished all three")
        }
    }
}