package com.example.coroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coroutines.ui.theme.CoroutinesTheme
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.job

data class Birds(val id: Int, val name: String, val sound: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutinesTheme {
                BirdScreen()
            }
        }
    }
}

@Composable
fun BirdScreen() {
    var selectedBird by remember { mutableStateOf<Birds?>(null) }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
            selectedBird?.let {
                Text(
                    text = it.name,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                LaunchedEffect(it.name) {
                    val coroutineContextWithName = currentCoroutineContext() + CoroutineName(it.name.uppercase())
                    val name = coroutineContextWithName[CoroutineName]
                    while(true) {
                        println("The sound of bird is ${it.sound} in coroutine called $name")
                        delay(1000)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Bird(Birds(1, "Tweety", "Caw"),
                    onButtonClick = {selectedBird = it})

                Bird(
                    Birds(2, "Zazu", "Coo"),
                    onButtonClick = { selectedBird = it },
                )
                Bird(Birds(3, "Woodstock", "Chirp"),
                    onButtonClick = {selectedBird = it})
            }
        }
    }
}

@Composable
fun Bird(birds: Birds,
         onButtonClick: (Birds) -> Unit) {
    Button(onClick = {onButtonClick(birds)}) {
        Text(birds.name)
    }
}

@Preview(showBackground = true)
@Composable
fun BirdScreenPreview() {
    BirdScreen()
}
