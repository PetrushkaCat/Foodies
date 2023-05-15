package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cat.petrushkacat.foodies.core.components.main.MainComponent
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children

@Composable
fun MainComponentUi(component: MainComponent) {
    val childStack by component.childStack.collectAsState()

    Children(stack = childStack) { child ->
        when(val instance = child.instance) {
            is MainComponent.Child.FoodCatalog -> { FoodCatalogComponentUi(component = instance.component) }
            is MainComponent.Child.ShoppingCart -> { ShoppingCartComponentUi(component = instance.component)}
            is MainComponent.Child.DishInfo -> { DishInfoComponentUi(component = instance.component)}
        }

    }
}