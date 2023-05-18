package cat.petrushkacat.foodies.core.components.main.dishinfo

import cat.petrushkacat.foodies.core.components.shared.AddInCartComponent
import cat.petrushkacat.foodies.core.components.shared.AddInCartComponentImpl
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.ShoppingCartInfo
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeDefault
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DishInfoComponentImpl(
    componentContext: ComponentContext,
    products: MutableStateFlow<List<Product>>,
    shoppingCartInfo: MutableStateFlow<ShoppingCartInfo>,
    product: Product,
    private val onBackClicked: () -> Unit
) : DishInfoComponent, ComponentContext by componentContext {

    private val scope = componentCoroutineScopeDefault()
    override val addInCartComponent: AddInCartComponent = AddInCartComponentImpl(
        childContext("dish_info_add_in_cart"),
        products,
        shoppingCartInfo
    )
    private val _models = MutableStateFlow(product)
    override val models = _models.asStateFlow()

    init {
        scope.launch {
            // why so? we have to observe the product quantity changes...
            products.collect {
                _models.value = products.value.first { it.id == product.id }
            }
        }
    }
    override fun onBackClick() {
        onBackClicked()
    }
}