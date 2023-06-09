package cat.petrushkacat.foodies.core.components.main.shoppingcart

import cat.petrushkacat.foodies.core.components.shared.AddInCartComponent
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.ShoppingCartInfo
import kotlinx.coroutines.flow.StateFlow

interface ShoppingCartComponent {

    val addInCartComponent: AddInCartComponent

    val models: StateFlow<List<Product>>

    val shoppingCartInfo: StateFlow<ShoppingCartInfo>
    fun onBackClick()

    fun onSubmit()

}