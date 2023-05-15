package cat.petrushkacat.foodies.core.components.main

import cat.petrushkacat.foodies.core.components.main.dishinfo.DishInfoComponent
import cat.petrushkacat.foodies.core.components.main.foodcatalog.FoodCatalogComponent
import cat.petrushkacat.foodies.core.components.main.shoppingcart.ShoppingCartComponent
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow

interface MainComponent {

    val childStack: StateFlow<ChildStack<*, Child>>

    sealed interface Child {
        data class FoodCatalog(val component: FoodCatalogComponent): Child
        data class ShoppingCart(val component: ShoppingCartComponent): Child
        data class DishInfo(val component: DishInfoComponent): Child
    }
}