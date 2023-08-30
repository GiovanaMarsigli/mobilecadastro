package com.example.appcomponentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.appcomponentes.ui.theme.AppComponentesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App(){
    AppComponentesTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}

@Preview(showBackground = true, widthDp = 150, heightDp = 200)
@Composable
fun AppPreview(){
    App()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuTopBar(){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black
                ),
                title = {
                    Column(){
                        Text(text = "Cadastro", color = Color.White)
                        Text(text = "usuário", color = Color.White, style = MaterialTheme.typography.labelSmall)
                    }


                },
                navigationIcon =  {
                    IconButton(onClick = {}) {
                         Icon(Icons.Filled.ArrowBack, tint=Color.White, contentDescription = "")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.MoreVert, tint=Color.White, contentDescription = "")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){

        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Autocomplete() {
    @Composable
    fun AutoComplete(
        label: String,
        searchQuery: String,
        onSearchQueryChanged: (String) -> Unit,
        onItemSelected: (String) -> Unit,
        items: List<String>,
        modifier: Modifier = Modifier
    ) {
        val focusManager = LocalFocusManager.current
        var expanded by remember { mutableStateOf(false) }

        val filteredItems by remember(searchQuery) {
            derivedStateOf {
                items.filter { item ->
                    searchQuery.isNotBlank() && item.contains(
                        searchQuery, ignoreCase = true
                    ) && item.lowercase() != searchQuery.lowercase()
                }
            }
        }

        var textFieldWidth by remember { mutableStateOf(IntSize.Zero) }
        Box(modifier.onSizeChanged {
            textFieldWidth = it
        }) {
            OutlinedTextField(
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth(),
                value = searchQuery,
                trailingIcon = {
                    AnimatedVisibility(
                        visible = searchQuery.isNotBlank(),
                        enter = fadeIn(animationSpec = tween(350)),
                        exit = fadeOut(animationSpec = tween(450)),
                    ) {
                        IconButton(onClick = { onSearchQueryChanged("") }) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                },
                onValueChange = {
                    expanded = it.isNotBlank()
                    onSearchQueryChanged(it)
                },
                singleLine = true
            )
            DropdownMenu(
                modifier = Modifier
                    .then(with(LocalDensity.current) {
                        Modifier.width(width = textFieldWidth.width.toDp())
                    })
                    .heightIn(max = 200.dp),
                expanded = expanded && filteredItems.isNotEmpty(),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false, dismissOnBackPress = true, dismissOnClickOutside = true
                )
            ) {
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                            focusManager.clearFocus()
                        },
                    )
                }
            }
        }
    }
    var searchQuery by remember { mutableStateOf("") }

    val items = listOf(
"Erica da silva",
    )
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AutoComplete(
            label = "Nome Completo",
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            searchQuery = searchQuery,
            onItemSelected = {
                searchQuery = it
            },
            items = items,
            onSearchQueryChanged = {
                searchQuery = it
            }
        )
    }
}


@Preview(showBackground = true, widthDp = 250, heightDp = 300)
@Composable
fun MenuTopBarPreview(){
    MenuTopBar()
    Autocomplete()

}
