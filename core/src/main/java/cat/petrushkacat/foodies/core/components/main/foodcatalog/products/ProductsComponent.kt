package cat.petrushkacat.foodies.core.components.main.foodcatalog.products

import cat.petrushkacat.foodies.core.components.shared.AddInCartComponent
import cat.petrushkacat.foodies.core.models.Product
import kotlinx.coroutines.flow.StateFlow

interface ProductsComponent {

    val models: StateFlow<List<Product>>

    val addInCartComponent: AddInCartComponent

    fun onCartButtonClick()

    fun onProductClick(product: Product)

}