package dev.cisnux.jetheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.cisnux.jetheroes.ui.theme.JetHeroesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetHeroesTheme {
                // A surface container using the 'background' color from the theme
                Scaffold { innerPadding ->
                    JetHeroesApp(
                        modifier = Modifier.padding(
                            PaddingValues(
                                top = innerPadding.calculateTopPadding() + 12.dp,
                                bottom = innerPadding.calculateBottomPadding() + 12.dp,
                                start = 12.dp,
                                end = 12.dp
                            ),
                        )
                    )
                }
            }
        }
    }
}
