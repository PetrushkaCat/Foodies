package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cat.petrushkacat.foodies.core.components.RootComponent
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children

@Composable
fun RootComponentUi(component: RootComponent) {
    val childStack by component.childStack.collectAsState()

    Children(stack = childStack) { child ->
        when(val instance = child.instance) {
            is RootComponent.Child.Main -> {
                MainComponentUi(component = instance.component)
            }
            is RootComponent.Child.Splash -> {
                SplashComponentUi(component = instance.component)
            }
        }

    }
}