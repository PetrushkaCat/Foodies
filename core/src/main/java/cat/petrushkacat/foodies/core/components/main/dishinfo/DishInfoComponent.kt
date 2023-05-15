package cat.petrushkacat.foodies.core.components.main.dishinfo

import cat.petrushkacat.foodies.core.components.shared.AddInCartComponent
import cat.petrushkacat.foodies.core.models.Product
import kotlinx.coroutines.flow.StateFlow

interface DishInfoComponent {

    val addInCartComponent: AddInCartComponent

    val models: StateFlow<Product>

    fun onBackClick()
}