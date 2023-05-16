package cat.petrushkacat.foodies.core.components.main.foodcatalog.products

import android.util.Log
import cat.petrushkacat.foodies.core.components.shared.AddInCartComponent
import cat.petrushkacat.foodies.core.components.shared.AddInCartComponentImpl
import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.Tag
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeDefault
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductsComponentImpl(
    componentContext: ComponentContext,
    private val products: MutableStateFlow<List<Product>>,
    private val _categories: StateFlow<List<Category>>,
    tags: StateFlow<List<Tag>>,
    searchText: StateFlow<String>,
    private val onCartClicked: () -> Unit,
    private val onProductClicked: (Product) -> Unit,
) : ProductsComponent, ComponentContext by componentContext {

    private val scopeDefault = componentCoroutineScopeDefault()

    private val _models = MutableStateFlow(products.value)
    override val models: StateFlow<List<Product>> = _models.asStateFlow()
    override val categories: StateFlow<List<Category>> = _categories

    init {

        scopeDefault.launch {
            launch {
                tags.collect { tags ->
                    filterWithTags(tags)
                    Log.d("tags", tags.toString())
                }
            }

            launch {
                searchText.collect {text ->
                    if(text.trim().isEmpty()) {
                        filterWithTags(tags.value)
                    } else {
                        _models.value = products.value.filter { product ->
                            product.name.lowercase().contains(searchText.value.lowercase().toRegex())
                        }
                    }
                }
            }
            launch {
                products.collect {
                    if(searchText.value.trim().isEmpty()) {
                        filterWithTags(tags.value)
                    } else {
                        _models.value = products.value.filter { product ->
                            product.name.lowercase().contains(searchText.value.lowercase().toRegex())
                        }
                    }
                }
            }
        }
    }

    override val addInCartComponent: AddInCartComponent = AddInCartComponentImpl(
        childContext("products_add_in_cart"),
        products
    )

    override fun onCartButtonClick() {
        onCartClicked()
    }

    override fun onProductClick(product: Product) {
        onProductClicked(product)
    }

    private fun filterWithTags(tags: List<Tag>) {
        if(tags.isEmpty()) {
            _models.value = products.value
        } else {
            _models.value = products.value.filter { product ->
                product.tag_ids.containsAll(tags.map { it.id })
            }
        }
        Log.d("tags", "11" + models.value.toString())
        Log.d("tags", "11" + products.value.toString())

    }
}