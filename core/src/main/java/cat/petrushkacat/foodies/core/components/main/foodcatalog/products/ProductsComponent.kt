package cat.petrushkacat.foodies.core.components.main.foodcatalog.products

import cat.petrushkacat.foodies.core.components.shared.AddInCartComponent
import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.ShoppingCartInfo
import kotlinx.coroutines.flow.StateFlow

interface ProductsComponent {

    val models: StateFlow<List<Product>>
    val categories: StateFlow<List<Category>>
    val shoppingCartInfo: StateFlow<ShoppingCartInfo>

    val addInCartComponent: AddInCartComponent
    fun onCartButtonClick()

    fun onProductClick(product: Product)

}