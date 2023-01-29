package me.mudkip.moememos.ui.page.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Source
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import me.mudkip.moememos.ui.page.common.RouteName
import me.mudkip.moememos.viewmodel.LocalUserState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SettingsPage(
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val userStateViewModel = LocalUserState.current
    val coroutineScope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current
    val status = userStateViewModel.status

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            userStateViewModel.currentUser?.let { user ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(15.dp)
                    ) {
                        Column(Modifier.padding(15.dp)) {
                            Text(user.displayName,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            if (user.displayName != user.displayEmail && user.displayEmail.isNotEmpty()) {
                                Text(user.displayEmail,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            if (status?.profile?.version?.isNotEmpty() == true) {
                                Text("✍️memos v${status.profile.version}",
                                    modifier = Modifier.padding(top = 5.dp),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            } ?: item {
                Button(
                    onClick = {
                        navController.navigate(RouteName.LOGIN)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    Text("Sign in",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            item {
                Text("About Moe Memos",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp, 10.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            item {
                Surface(onClick = {
                    uriHandler.openUri("https://memos.moe")
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp, 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Web,
                            contentDescription = "Web",
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Website",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                Surface(onClick = {
                    uriHandler.openUri("https://memos.moe/privacy")
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(8.dp, 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = "Privacy",
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Privacy Policy",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                Surface(onClick = {
                    uriHandler.openUri("https://memos.moe/android-acknowledgements")
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(8.dp, 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Source,
                            contentDescription = "Acknowledgements",
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Acknowledgements",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            item {
                Surface(onClick = {
                    uriHandler.openUri("https://github.com/mudkipme/MoeMemosAndroid/issues")
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(8.dp, 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.BugReport,
                            contentDescription = "Report an Issue",
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Report an Issue",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            if (userStateViewModel.currentUser != null) {
                item {
                    FilledTonalButton(
                        onClick = {
                            coroutineScope.launch {
                                userStateViewModel.logout()
                                navController.navigate(RouteName.LOGIN) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Text("Sign out")
                    }
                }
            }

        }
    }
}