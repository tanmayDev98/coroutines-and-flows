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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesTheme {
            }
        }

        val birdOne = BirdInfo("Coo",1000L)
        val birdTwo = BirdInfo("Caw",1000L)
        val birdThree = BirdInfo("Chirp",1000L)

        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            val innerScope = this
            val job = coroutineContext.job
            val measureTime = measureTimeMillis {
                innerScope.launch {
                    birds(birdOne, job.isActive)
                }
                innerScope.launch {
                    birds(birdTwo, job.isActive)
                }
                innerScope.launch {
                    birds(birdThree, job.isActive)
                }
                delay(10000)
            }
            //scope.coroutineContext.cancelChildren()
            job.cancel()
            println("Cancelled after $measureTime")
        }
    }
}

suspend fun birds(bird: BirdInfo, isActive: Boolean) {
    while(isActive) {
        delay(bird.delay)
        println(bird.birdSound)
    }
}

data class BirdInfo(val birdSound: String, val delay: Long)