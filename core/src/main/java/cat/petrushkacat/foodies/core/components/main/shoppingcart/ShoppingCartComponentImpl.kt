package cat.petrushkacat.foodies.core.components.main.shoppingcart

import android.util.Log
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShoppingCartComponentImpl(
    componentContext: ComponentContext,
    products: MutableStateFlow<List<Product>>,
    private val onBackClicked: () -> Unit,
    _shoppingCartInfo: MutableStateFlow<ShoppingCartInfo>
) : ShoppingCartComponent, ComponentContext by componentContext {

    private val scopeDefault = componentCoroutineScopeDefault()

    override val addInCartComponent: AddInCartComponent = AddInCartComponentImpl(
        childContext("shopping_cart_add_in_cart"),
        products,
        _shoppingCartInfo
    )

    override val models = MutableStateFlow<List<Product>>(emptyList())
    override val shoppingCartInfo = _shoppingCartInfo.asStateFlow()

    init {
        scopeDefault.launch {
            products.collect { products ->
                models.value = products.filter {
                    it.quantity > 0
                }
                Log.d("state", "state")
            }
        }
    }

    override fun onBackClick() {
        onBackClicked()
    }

    override fun onSubmit() {
       //Sending info to the server...
    }
}