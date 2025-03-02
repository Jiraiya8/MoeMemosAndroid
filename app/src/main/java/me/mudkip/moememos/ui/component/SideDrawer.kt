package me.mudkip.moememos.ui.component

import android.icu.text.DateFormatSymbols
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.mudkip.moememos.R
import me.mudkip.moememos.ext.string
import me.mudkip.moememos.ui.page.common.LocalRootNavController
import me.mudkip.moememos.ui.page.common.RouteName
import me.mudkip.moememos.viewmodel.LocalMemos
import java.net.URLEncoder

@Composable
fun SideDrawer(
    memosNavController: NavHostController,
    drawerState: DrawerState
) {
    val weekDays = remember {
        DateFormatSymbols.getInstance().shortWeekdays
    }
    var showHeatMap by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val memosViewModel = LocalMemos.current
    val rootNavController = LocalRootNavController.current

    ModalDrawerSheet {
        LazyColumn {
            item {
                Stats()
            }
            
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(10.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(end = 5.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(weekDays[1],
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline)
                        Text(weekDays[4],
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline)
                        Text(weekDays[7],
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline)
                    }
                    if (showHeatMap) {
                        Heatmap()
                    }
                }
            }
            
            item {
                Text(
                    R.string.moe_memos.string,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(20.dp)
                )
            }
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.memos.string) },
                    icon = { Icon(Icons.Outlined.GridView, contentDescription = null) },
                    selected = memosNavController.currentDestination?.route == RouteName.MEMOS,
                    onClick = {
                        scope.launch {
                            memosNavController.navigate(RouteName.MEMOS) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.explore.string) },
                    icon = { Icon(Icons.Outlined.Home, contentDescription = null) },
                    selected = memosNavController.currentDestination?.route == RouteName.EXPLORE,
                    onClick = {
                        scope.launch {
                            memosNavController.navigate(RouteName.EXPLORE) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.resources.string) },
                    icon = { Icon(Icons.Outlined.PhotoLibrary, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            rootNavController.navigate(RouteName.RESOURCE)
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.archived.string) },
                    icon = { Icon(Icons.Outlined.Inventory2, contentDescription = null) },
                    selected = memosNavController.currentDestination?.route == RouteName.ARCHIVED,
                    onClick = {
                        scope.launch {
                            memosNavController.navigate(RouteName.ARCHIVED) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
            item {
                NavigationDrawerItem(
                    label = { Text(R.string.settings.string) },
                    icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            rootNavController.navigate(RouteName.SETTINGS)
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }

            item {
                Divider(Modifier.padding(vertical = 10.dp))
            }

            item {
                Text(
                    R.string.tags.string,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(20.dp)
                )
            }

            memosViewModel.tags.toList().forEach { tag ->
                item {
                    NavigationDrawerItem(
                        label = { Text(tag) },
                        icon = { Icon(Icons.Outlined.Tag, contentDescription = null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                memosNavController.navigate("${RouteName.TAG}/${URLEncoder.encode(tag, "UTF-8")}") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        memosViewModel.loadTags()
        delay(500)
        showHeatMap = true
    }
}
