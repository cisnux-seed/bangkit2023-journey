@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package dev.cisnux.composestateexample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cisnux.composestateexample.ui.theme.ComposeStateExampleTheme
import dev.cisnux.composestateexample.utils.Scale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStateExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        AppConverter()
                        TwoWayConverterApp()
                    }
                }
            }
        }
    }
}

@Preview(device = "id:pixel_6_pro", showBackground = true)
@Composable
private fun AppConverterPreview() {
    AppConverter()
}

@Composable
private fun AppConverter(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        var newInput by rememberSaveable {
            mutableStateOf("")
        }
        StatefulTemperatureInput()
        StatelessTemperatureInput(newInput = newInput, onNewInput = {
            newInput = it
        }, onOutputTransform = {
            convertToFahrenheit(it)
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GreetingPreview() {
    ComposeStateExampleTheme {
        Scaffold { innerPadding ->
            Greeting("Android", modifier = Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatefulTemperatureInputPreview() {
    StatefulTemperatureInput()
}

@Preview(showBackground = true)
@Composable
fun StatelessTemperatureInputPreview() {
    StatelessTemperatureInput(onNewInput = {})
}

@Composable
fun StatefulTemperatureInput(modifier: Modifier = Modifier) {
    var input by rememberSaveable {
        mutableStateOf("")
    }
    var output by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.stateful_converter),
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(value = input,
            label = { Text(text = stringResource(id = R.string.enter_celsius)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            })
        Text(text = stringResource(id = R.string.temperature_fahrenheit, output))
    }
}

@Composable
fun StatelessTemperatureInput(
    modifier: Modifier = Modifier,
    newInput: String = "",
    onNewInput: (String) -> Unit,
    onOutputTransform: ((String) -> String)? = null,
) {
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.stateless_converter),
            style = MaterialTheme.typography.titleLarge
        )
        OutlinedTextField(
            value = newInput,
            label = { Text(text = stringResource(id = R.string.enter_celsius)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onNewInput
        )
        Text(
            text = stringResource(
                id = R.string.temperature_fahrenheit,
                onOutputTransform?.let { it(newInput) } ?: newInput
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralTemperatureInput(
    scale: Scale,
    input: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        OutlinedTextField(
            value = input,
            label = {
                Text(
                    text = stringResource(
                        id = R.string.enter_temperature,
                        scale.scaleName
                    )
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange
        )
    }
}

@Preview(
    device = "id:pixel_6_pro", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showSystemUi = true
)
@Composable
private fun TwoWayConverterAppPreview() {
    ComposeStateExampleTheme {
        Scaffold { innerPadding ->
            TwoWayConverterApp(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun TwoWayConverterApp(modifier: Modifier = Modifier) {
    Column(modifier.padding(16.dp)) {
        var celcius by rememberSaveable {
            mutableStateOf("")
        }
        var fahrenheit by rememberSaveable {
            mutableStateOf("")
        }

        Text(
            text = stringResource(id = R.string.two_way_converter),
            style = MaterialTheme.typography.titleLarge
        )
        GeneralTemperatureInput(
            scale = Scale.CELCIUS,
            input = celcius,
            onValueChange = { newInput ->
                celcius = newInput
                fahrenheit = convertToFahrenheit(newInput)
            }
        )
        GeneralTemperatureInput(
            scale = Scale.FAHRENHEIT,
            input = fahrenheit,
            onValueChange = { newInput ->
                fahrenheit = newInput
                celcius = convertToCelsius(newInput)
            }
        )
    }
}

private fun convertToFahrenheit(celcius: String): String = celcius.toDoubleOrNull()?.let {
    (it * 9 / 5) + 32
}?.toString() ?: ""

private fun convertToCelsius(fahrenheit: String) =
    fahrenheit.toDoubleOrNull()?.let {
        (it - 32) * 5 / 9
    }?.toString() ?: ""

