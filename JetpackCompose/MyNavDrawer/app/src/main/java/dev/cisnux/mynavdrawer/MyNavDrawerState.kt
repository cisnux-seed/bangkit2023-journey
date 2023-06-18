package dev.cisnux.mynavdrawer

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Stable
class MyNavDrawerState(
    val drawerState: DrawerState,
    val snackbarState: SnackbarHostState,
    selectedItem: Int,
    private val coroutineScope: CoroutineScope,
    private val context: Context,
) {
    var selectedItem by mutableStateOf(selectedItem)
        private set

    fun onMenuClick() {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    fun onItemSelected(index: Int, title: String) {
        selectedItem = index
        coroutineScope.launch {
            drawerState.close()
            val snackbarResult = snackbarState.showSnackbar(
                message = context.getString(R.string.coming_soon, title),
                actionLabel = context.getString(R.string.subscribe_question)
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                Toast.makeText(
                    context,
                    context.getString(R.string.subscribed_info),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun onBackPress() {
        if (drawerState.isOpen)
            coroutineScope.launch {
                drawerState.close()
            }
    }
}

@ExperimentalMaterial3Api
@Composable
fun rememberMyDrawerState(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    snackbarState: SnackbarHostState = SnackbarHostState(),
    selectedItem: Int = 0,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
): MyNavDrawerState =
    // key paremeter pada remember hanya digunakan jika value tersebut juga remember
    remember(
        drawerState,
        coroutineScope,
        context
    ) {
        MyNavDrawerState(drawerState, snackbarState, selectedItem, coroutineScope, context)
    }
