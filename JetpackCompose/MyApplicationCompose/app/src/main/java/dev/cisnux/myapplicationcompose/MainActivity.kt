package dev.cisnux.myapplicationcompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Device
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import dev.cisnux.myapplicationcompose.ui.theme.MyApplicationComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hell $name!",
        modifier = modifier
    )
}

@Preview(
    name = "Without Surface",
    showSystemUi = true,
    device = "id:pixel_6_pro",
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun GreetingPreview() {
    MyApplicationComposeTheme {
        Greeting("Android")
    }
}

@Preview(
    name = "With Surface",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun GreetingSurfacePreview(){
    MyApplicationComposeTheme {
        Surface {
            Greeting("Android")
        }
    }
}
