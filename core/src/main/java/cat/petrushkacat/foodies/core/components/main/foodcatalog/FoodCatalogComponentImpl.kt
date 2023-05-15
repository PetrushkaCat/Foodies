package cat.petrushkacat.foodies.core.components.main.foodcatalog

import cat.petrushkacat.foodies.core.components.main.foodcatalog.filter.FilterComponent
import cat.petrushkacat.foodies.core.components.main.foodcatalog.filter.FilterComponentImpl
import cat.petrushkacat.foodies.core.components.main.foodcatalog.products.ProductsComponent
import cat.petrushkacat.foodies.core.components.main.foodcatalog.products.ProductsComponentImpl
import cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar.FoodCatalogToolbarComponent
import cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar.FoodCatalogToolbarComponentImpl
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.Tag
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FoodCatalogComponentImpl(
    componentContext: ComponentContext,
    products: MutableStateFlow<List<Product>>,
    _tags: MutableStateFlow<List<Tag>>,
    onCartClicked: () -> Unit,
    onProductClicked: (Product) -> Unit
) : FoodCatalogComponent, ComponentContext by componentContext {

    private val searchText = MutableStateFlow("")
    private val tags: StateFlow<List<Tag>> = _tags.asStateFlow()
    private val selectedTags = MutableStateFlow<List<Tag>>(emptyList())

    override val toolbarComponent: FoodCatalogToolbarComponent = FoodCatalogToolbarComponentImpl(
        onSearched = {
            searchText.value = it
        }
    )

    override val productsComponent: ProductsComponent = ProductsComponentImpl(
        childContext("food_catalog_products"),
        products,
        selectedTags,
        searchText,
        onCartClicked,
        onProductClicked
    )

    override val filterComponent: FilterComponent = FilterComponentImpl(
        childContext("filter_products"),
        tags,
        onFilter = { tags ->
            val temp = tags.filter { tag ->
                tag.isSelected
            }
            selectedTags.value = temp
        })

}