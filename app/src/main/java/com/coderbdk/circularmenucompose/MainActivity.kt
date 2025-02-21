package com.coderbdk.circularmenucompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coderbdk.circularmenu.CircularMenu
import com.coderbdk.circularmenu.CircularMenuDefaults
import com.coderbdk.circularmenu.CircularMenuIcon
import com.coderbdk.circularmenu.CircularMenuItem
import com.coderbdk.circularmenu.rememberCircularMenuState
import com.coderbdk.circularmenucompose.ui.theme.CircularMenuComposeTheme


private val circularMenus = listOf(
    CircularMenuItem(
        title = "Home",
        icon = CircularMenuIcon.Vector(image = Icons.Default.Home)
    ),
    CircularMenuItem(
        title = "AccountCircle",
        icon = CircularMenuIcon.Vector(image = Icons.Default.AccountCircle)
    ),
    CircularMenuItem(
        title = "Favorite",
        icon = CircularMenuIcon.Vector(image = Icons.Default.Favorite)
    ),
    CircularMenuItem(
        title = "Build",
        icon = CircularMenuIcon.Vector(image = Icons.Default.Build)
    ),
    CircularMenuItem(
        title = "Delete",
        icon = CircularMenuIcon.Vector(image = Icons.Default.Delete)
    ),
    CircularMenuItem(
        title = "Email",
        icon = CircularMenuIcon.Vector(image = Icons.Default.Email)
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state = rememberCircularMenuState(
                menus = circularMenus,
                colors = CircularMenuDefaults.colors(
                    selectedIconColor = Color(0xFFFFFFFF),
                    unselectedIconColor = Color(0xFFD97069),
                    controllerButtonContainerColor = Color(0xFFE31F11),
                    controllerButtonIconColor = Color(0xFFF5F5F5),
                    overlayContainerBorderColor = Color(0xFF4A4A4A).copy(alpha = 0.4f)
                ),
                brushes = CircularMenuDefaults.brushes(
                    overlayContainerBrush = Brush.radialGradient(
                        listOf(
                            Color(0xFFFF5722),
                            Color(0xFF9D2920)
                        )
                    ),
                    indicatorBrush = Brush.linearGradient(
                        listOf(
                            Color(0xFFD7382E),
                            Color(0xFFE88D87)
                        )
                    ),

                    )
            )
            CircularMenuComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        CircularMenu(state = state, onMenuSelected = {

                        })
                    }) { innerPadding ->

                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        LazyColumn {
                            items(100) {
                                ListItem(
                                    modifier = Modifier.clickable { },
                                    leadingContent = {
                                        Text("${it + 1}")
                                    },
                                    headlineContent = {
                                        Text("Heading")
                                    }
                                )

                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainContent() {

    val state = rememberCircularMenuState(
        menus = circularMenus,
        colors = CircularMenuDefaults.colors(
            selectedIconColor = Color(0xFFFFFFFF),
            unselectedIconColor = Color(0xFFD97069),
            controllerButtonContainerColor = Color(0xFFE31F11),
            controllerButtonIconColor = Color(0xFFF5F5F5),
            overlayContainerBorderColor = Color(0xFF4A4A4A).copy(alpha = 0.4f)
        ),
        brushes = CircularMenuDefaults.brushes(
            overlayContainerBrush = Brush.radialGradient(
                listOf(
                    Color(0xFFFF5722),
                    Color(0xFF9D2920)
                )
            ),
            indicatorBrush = Brush.linearGradient(
                listOf(
                    Color(0xFFD7382E),
                    Color(0xFFE88D87)
                )
            ),

            )
    )
    CircularMenu(state = state, onMenuSelected = {

    })

}


@Preview(showBackground = true)
@Composable
fun CircularMenuPreview() {
    CircularMenuComposeTheme(
        darkTheme = true
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainContent()
        }

    }
}