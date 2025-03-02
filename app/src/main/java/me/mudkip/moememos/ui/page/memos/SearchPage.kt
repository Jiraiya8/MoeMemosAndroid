package me.mudkip.moememos.ui.page.memos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.mudkip.moememos.R
import me.mudkip.moememos.ext.string
import me.mudkip.moememos.ui.page.common.LocalRootNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPage() {
    var searchText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    val rootNavController = LocalRootNavController.current
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = searchText,
                        onValueChange = { searchText = it },
                        singleLine = true,
                        placeholder = { Text(R.string.search.string) },
                        shape = ShapeDefaults.ExtraLarge,
                        leadingIcon = {
                            IconButton(onClick = { rootNavController.popBackStack() }) {
                                Icon(
                                    Icons.Outlined.ArrowBack,
                                    contentDescription = R.string.back.string
                                )
                            }
                        }
                    )
                }
            )
        },

        content = { innerPadding ->
            MemosList(
                contentPadding = innerPadding,
                searchString = searchText.text
            )
        }
    )

    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
    }
}