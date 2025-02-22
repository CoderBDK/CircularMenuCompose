# 🌀 CircularMenuCompose

🚀 CircularMenuCompose is a beautiful and interactive Jetpack Compose library for creating elegant circular menus with smooth animations.

---

## ✨ Features

- 🎨 **Customizable UI** – Easily modify colors to match your app’s theme.

- ⚡ Smooth Animations – Expand, rotate, and select menu items with seamless transitions.

- 🔄 Dynamic Menu – Clean, intuitive design with menu items arranged in a circular fashion.

- 🎯 **State Management** – Seamless handling of menu selection and expansion.

- ✅ **Lightweight & Fast** – Optimized for performance and smooth user experience.

- 🔧 **Easy to Use** – Simple setup with minimal configuration required.



---

## 📸 Preview
![output](https://github.com/user-attachments/assets/0a8789f5-f54a-4134-8472-2dc7a9e58ce7)
![image](https://github.com/user-attachments/assets/9609ae4a-9c96-46e8-bc6f-fefc7fa0df8b)


---

## 🛠 Usage

### **Step 1: Create a Menu State**

```kotlin

val circularMenus = listOf(
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
    )
)
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
```

### **Step 2: Implement the Circular Menu**

```kotlin
CircularMenu(
    state = state,
    onMenuSelected = { index ->
        println("Selected item: $index")
    }
)
```

---

## 🎨 Customization

You can change the **colors and brushes** to fit your design:

```kotlin
colors = CircularMenuDefaults.colors(
    selectedIconColor = Color(0xFFFFFFFF),
    unselectedIconColor = Color(0xFFD97069),
    controllerButtonContainerColor = Color(0xFFE31F11),
    controllerButtonIconColor = Color(0xFFF5F5F5),
    overlayContainerBorderColor = Color(0xFF4A4A4A).copy(alpha = 0.4f)
)
```

```kotlin
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
    )
)
```

## 📦 Installation

Clone this repository:
```bash
git clone https://github.com/CoderBDK/CircularMenuCompose.git
```

## License


This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.


