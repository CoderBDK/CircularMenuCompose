package com.coderbdk.circularmenu


import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.abs


sealed class CircularMenuIcon {
    data class Vector(val image: ImageVector) : CircularMenuIcon()
    data class Resource(@DrawableRes val resId: Int) : CircularMenuIcon()
    data class Url(val url: String) : CircularMenuIcon()
}

data class CircularMenuItem(
    val title: String,
    val icon: CircularMenuIcon
)

abstract class CircularMenuState(
    val menus: List<CircularMenuItem>,
    val colors: CircularMenuColor,
    val brushes: CircularMenuBrush
) {

    abstract val menuButtonSize: Dp
    abstract val menuSurfaceSize: Dp
    abstract val countMenu: Int
    abstract val angleStep: Float

    abstract val expand: Boolean
    abstract val selectedMenu: Int
    abstract val previousMenu: Int

    abstract fun selectMenu(index: Int)
    abstract fun show()
    abstract fun hide()
    abstract fun toggleMenu()
}

internal class CircularMenuStateImpl(
    menus: List<CircularMenuItem>,
    colors: CircularMenuColor,
    brushes: CircularMenuBrush
) : CircularMenuState(menus, colors, brushes) {

    override var menuButtonSize: Dp = 24.dp
    override var menuSurfaceSize: Dp = (48 * 3).dp
    override var countMenu: Int = menus.size
    override var angleStep: Float = if (countMenu > 0) 360f / countMenu else 0f


    private var _expand: Boolean by mutableStateOf(false)
    private var _previousMenu: Int by mutableIntStateOf(0)
    private var _selectedMenu: Int by mutableIntStateOf(0)


    override val expand: Boolean
        get() = _expand

    override val selectedMenu: Int
        get() = _selectedMenu

    override val previousMenu: Int
        get() = _previousMenu


    override fun show() {
        if (!expand) {
            _expand = true
        }
    }

    override fun hide() {
        if (expand) {
            _expand = false
        }
    }

    override fun toggleMenu() {
        _expand = !_expand
        if (!expand) {
            _previousMenu = 0
        }
    }

    override fun selectMenu(index: Int) {
        _previousMenu = selectedMenu
        _selectedMenu = index
    }
}


@Composable
fun rememberCircularMenuState(
    menus: List<CircularMenuItem>,
    colors: CircularMenuColor = CircularMenuDefaults.colors(),
    brushes: CircularMenuBrush = CircularMenuDefaults.brushes()
): CircularMenuState = remember {
    CircularMenuStateImpl(
        menus = menus,
        colors = colors,
        brushes = brushes
    )
}


@Composable
fun CircularMenu(
    state: CircularMenuState,
    onMenuSelected: (Int) -> Unit,
) {

    val expandValue by animateIntAsState(
        targetValue = if (state.expand) state.countMenu else 0,
        animationSpec = tween(
            durationMillis = if (state.expand) 300 else 100 * state.countMenu
        ),
        label = "expand_value"
    )
    val expandRotationValue by animateFloatAsState(
        targetValue = if (state.expand) 0f else 180f,
        animationSpec = tween(
            durationMillis = 500
        ),
        label = "expand_value"
    )

    val selectedValue by animateIntAsState(
        targetValue = if (state.expand) state.selectedMenu else 0,
        animationSpec = tween(
            durationMillis = if (abs(state.selectedMenu - state.previousMenu) == 1) 50
            else (60 * abs(state.selectedMenu - state.previousMenu)).coerceIn(100, 500)
        ),
        label = "expand_value"
    )


    Box(
        Modifier
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {

        MenuOverlayContainer(
            expand = state.expand,
            menuSurfaceSize = state.menuSurfaceSize,
            expandValue = expandValue,
            brush = state.brushes.overlayContainerBrush,
            colors = state.colors
        )

        MenuIndicator(
            expand = state.expand,
            expandValue = expandValue,
            menuButtonSize = state.menuButtonSize,
            angleStep = state.angleStep,
            selectedValue = selectedValue,
            brush = state.brushes.indicatorBrush
        )

        MenuControllerButton(
            expandRotationValue = expandRotationValue,
            expand = state.expand,
            colors = state.colors,
            onClick = {
                state.toggleMenu()
            }
        )

        MenuContent(
            countMenu = state.countMenu,
            expandValue = expandValue,
            selectedValue = state.selectedMenu,
            menuButtonSize = state.menuButtonSize,
            state = state,
            angleStep = state.angleStep,
            colors = state.colors,
            onMenuSelected = {
                onMenuSelected(it)
                state.selectMenu(it)
            }
        )
    }
}

@Composable
private fun MenuOverlayContainer(
    expand: Boolean,
    expandValue: Int,
    menuSurfaceSize: Dp,
    brush: Brush,
    colors: CircularMenuColor
) {
    if (expand || expandValue != 0)
        Box(
            modifier = Modifier
                .size(menuSurfaceSize)
                .border(
                    width = 1.dp,
                    color = colors.overlayContainerBorderColor,
                    shape = CircleShape
                )
                .shadow(
                    elevation = 1.dp,
                    shape = CircleShape
                )
                .background(
                    brush = brush,
                    shape = CircleShape,
                )
                .pointerInput(Unit) {
                    if (expand) {
                        detectTapGestures { }
                    }
                }
        ) {

        }
}

@Composable
private fun MenuIndicator(
    expand: Boolean,
    expandValue: Int,
    menuButtonSize: Dp,
    angleStep: Float,
    selectedValue: Int,
    brush: Brush
) {
    if (expand || expandValue != 0) {
        Box(
            modifier = Modifier
                .size(menuButtonSize + 24.dp)
                .rotate(angleStep * selectedValue)
                .graphicsLayer {
                    translationX = 48.dp.toPx()
                    rotationZ = 45f
                }
                .background(
                    brush,
                    shape = RoundedCornerShape(
                        topEnd = 100.dp
                    )
                )
        )
    }

}

@Composable
private fun MenuControllerButton(
    expandRotationValue: Float,
    expand: Boolean,
    colors: CircularMenuColor,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .rotate(expandRotationValue)
            .size(40.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = colors.controllerButtonContainerColor
        ),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            imageVector = if (expand) Icons.Default.Close else Icons.Default.Add,
            tint = colors.controllerButtonIconColor,
            contentDescription = "Menu Expand & Collapse"
        )
    }
}

@Composable
private fun MenuContent(
    countMenu: Int,
    expandValue: Int,
    selectedValue: Int,
    menuButtonSize: Dp,
    state: CircularMenuState,
    angleStep: Float,
    colors: CircularMenuColor,
    onMenuSelected: (Int) -> Unit
) {
    for (i in 0..<countMenu) {
        key(i) {
            if (i < expandValue) {
                MenuItem(
                    selectedValue = selectedValue,
                    index = i,
                    menuButtonSize = menuButtonSize,
                    menus = state.menus,
                    angleStep = angleStep,
                    colors = colors,
                    onMenuSelected = onMenuSelected
                )
            }
        }

    }
}


@Composable
private fun MenuItem(
    selectedValue: Int,
    menuButtonSize: Dp,
    angleStep: Float,
    index: Int,
    menus: List<CircularMenuItem>,
    colors: CircularMenuColor,
    onMenuSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(menuButtonSize)
            .rotate(angleStep * index)
            .graphicsLayer {
                translationX = 44.dp.toPx()
            },
    ) {

        when (val icon = menus[index].icon) {
            is CircularMenuIcon.Resource -> {

            }

            is CircularMenuIcon.Url -> {

            }

            is CircularMenuIcon.Vector -> {
                IconButton(
                    modifier = Modifier
                        .size(menuButtonSize)
                        .rotate(-angleStep * index),
                    onClick = {
                        onMenuSelected(index)
                    }) {

                    Icon(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxSize(),
                        imageVector = icon.image,
                        tint = if (selectedValue == index) colors.selectedIconColor else colors.unselectedIconColor,
                        contentDescription = "Menu Item"
                    )
                }
            }

        }
    }
}

data class CircularMenuColor(
    val overlayContainerBorderColor: Color,
    val selectedIconColor: Color,
    val unselectedIconColor: Color,
    val controllerButtonContainerColor: Color,
    val controllerButtonIconColor: Color
)

private data object DefaultCircularMenuColor {
    val overlayContainerBorderColor = Color.White
    val controllerButtonContainerColor = Color(0xFF223FFF)
    val controllerButtonIconColor = Color(0xFFFFFFFF)
    val selectedIconColor = Color(0xFFFAFCFF)
    val unselectedIconColor = Color(0xF3979797)
}

data class CircularMenuBrush(
    val overlayContainerBrush: Brush,
    val indicatorBrush: Brush
)

private data object DefaultCircularMenuBrush {
    val overlayContainerBrush = Brush.radialGradient(
        listOf(
            Color(0xFF060C25),
            Color(0xFF224EFF),
        )
    )
    val indicatorBrush = Brush.radialGradient(
        listOf(
            Color(0xFF214BF3),
            Color(0xFF224EFF),
        )
    )
}

object CircularMenuDefaults {
    fun colors(
        overlayContainerBorderColor: Color = DefaultCircularMenuColor.overlayContainerBorderColor,
        selectedIconColor: Color = DefaultCircularMenuColor.selectedIconColor,
        unselectedIconColor: Color = DefaultCircularMenuColor.unselectedIconColor,
        controllerButtonContainerColor: Color = DefaultCircularMenuColor.controllerButtonContainerColor,
        controllerButtonIconColor: Color = DefaultCircularMenuColor.controllerButtonIconColor
    ): CircularMenuColor {
        return CircularMenuColor(
            overlayContainerBorderColor,
            selectedIconColor,
            unselectedIconColor,
            controllerButtonContainerColor,
            controllerButtonIconColor
        )
    }

    fun brushes(
        overlayContainerBrush: Brush = DefaultCircularMenuBrush.overlayContainerBrush,
        indicatorBrush: Brush = DefaultCircularMenuBrush.indicatorBrush

    ): CircularMenuBrush {
        return CircularMenuBrush(
            overlayContainerBrush,
            indicatorBrush
        )
    }
}