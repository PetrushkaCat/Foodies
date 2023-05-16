package cat.petrushkacat.foodies.core.components.shared

import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.ShoppingCartInfo
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeIO
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddInCartComponentImpl(
    componentContext: ComponentContext,
    private val products: MutableStateFlow<List<Product>>,
    private val shoppingCartInfo: MutableStateFlow<ShoppingCartInfo>,
) : AddInCartComponent, ComponentContext by componentContext {

    private val scopeIO = componentCoroutineScopeIO()

    override fun plusItem(itemId: Int) {
        scopeIO.launch {

            val temp = products.value.toMutableList()
            var oldProduct: Product? = null
            var index: Int = -1
            temp.forEachIndexed { i, it ->
                if (it.id == itemId) {
                    index = i
                    oldProduct = it
                    return@forEachIndexed
                }
            }

            if (oldProduct != null) {
                temp.remove(oldProduct)
                temp.add(index, oldProduct!!.copy(quantity = oldProduct!!.quantity + 1))
            }

            updateShoppingCartInfo(oldProduct!!, true)
            products.value = temp.toList()
        }
    }

    override fun minusItem(itemId: Int) {
        scopeIO.launch {
            val temp = products.value.toMutableList()
            var oldProduct: Product? = null
            var index: Int = -1
            temp.forEachIndexed { i, it ->
                if (it.id == itemId) {
                    if (it.quantity > 0) {
                        index = i
                        oldProduct = it
                        return@forEachIndexed
                    } else {
                        return@forEachIndexed
                    }
                }
            }

            if (oldProduct != null) {
                temp.remove(oldProduct)
                temp.add(index, oldProduct!!.copy(quantity = oldProduct!!.quantity - 1))
            }
            updateShoppingCartInfo(oldProduct!!, false)
            products.value = temp.toList()
        }
    }

    private fun updateShoppingCartInfo(oldProduct: Product, isPlus: Boolean) {

        var quantity = 0
        val price = if (isPlus) {
            quantity = shoppingCartInfo.value.itemsQuantity + 1
            shoppingCartInfo.value.itemsPrice + oldProduct.price_current
        } else {
            quantity = shoppingCartInfo.value.itemsQuantity - 1
            shoppingCartInfo.value.itemsPrice - oldProduct.price_current
        }

        shoppingCartInfo.value = ShoppingCartInfo(
            itemsQuantity = quantity,
            itemsPrice = price
        )
    }

}