package dev.cisnux.mynavdrawer

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cisnux.mynavdrawer.ui.components.MenuItem
import dev.cisnux.mynavdrawer.ui.theme.MyNavDrawerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyNavDrawerTheme {
                // A surface container using the 'background' color from the theme
                MyNavDrawerApp(modifier = Modifier.fillMaxSize())

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyNavDrawerTheme {
        Greeting("Android")
    }
}

@Preview(
    showBackground = true, device = "id:pixel_6_pro", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun MyDrawerAppPreview() {
    MyNavDrawerTheme(dynamicColor = false) {
        MyNavDrawerApp()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNavDrawerApp(modifier: Modifier = Modifier) {
//    val snackbarState = remember { SnackbarHostState() }
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    var selectedItem by remember {
//        mutableStateOf(0)
//    }
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
    val appState = rememberMyDrawerState()
    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        drawerContent = {
            ModalDrawerSheet {
                MyDrawerContent(
                    selectedItem = appState.selectedItem,
                    onItemSelected = appState::onItemSelected,
                    onBackPress = appState::onBackPress
                )
            }
        },
//        gesturesEnabled = appState.drawerState.isOpen,
        content = {
            Scaffold(
                modifier = modifier,
                topBar = {
                    MyTopBar(
                        onMenuClick = appState::onMenuClick
                    )
                }, snackbarHost = {
                    SnackbarHost(hostState = appState.snackbarState)
                }) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.hello_world))
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    selectedItem: Int,
    onItemSelected: (index: Int, title: String) -> Unit,
    onBackPress: () -> Unit
) {
    Log.d(MainActivity::class.simpleName, selectedItem.toString())
    val items = listOf(
        MenuItem(
            title = stringResource(id = R.string.home), icon = Icons.Default.Home
        ),
        MenuItem(
            title = stringResource(id = R.string.favourite), icon = Icons.Default.Favorite
        ),
        MenuItem(
            title = stringResource(R.string.profile), icon = Icons.Default.AccountCircle
        ),
    )

    Column(modifier) {
        Box(
            modifier = Modifier
                .height(190.dp)
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        )
        items.forEachIndexed { index, item ->
            NavigationDrawerItem(modifier = Modifier.padding(horizontal = 8.dp),
                label = { Text(text = item.title) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index, item.title)
                })
        }
    }

    // automatically
    BackHandler {
        onBackPressed()
    }
    // manually
    BackPressHandler(onBackPress = onBackPress)

}

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPress: () -> Unit) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPress)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher?.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}
