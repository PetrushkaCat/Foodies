package cat.petrushkacat.foodies.core.components.main

import android.util.Log
import cat.petrushkacat.foodies.core.Repository
import cat.petrushkacat.foodies.core.components.main.dishinfo.DishInfoComponentImpl
import cat.petrushkacat.foodies.core.components.main.foodcatalog.FoodCatalogComponentImpl
import cat.petrushkacat.foodies.core.components.main.shoppingcart.ShoppingCartComponentImpl
import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.ShoppingCartInfo
import cat.petrushkacat.foodies.core.models.Tag
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeDefault
import cat.petrushkacat.foodies.core.utils.toStateFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainComponentImpl(
    componentContext: ComponentContext,
    private val repository: Repository
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<ChildConfig>()

    private val products = MutableStateFlow<List<Product>>(emptyList())
    private val tags = MutableStateFlow<List<Tag>>(emptyList())
    private val categories = MutableStateFlow<List<Category>>(emptyList())
    private val shoppingCartInfo = MutableStateFlow(ShoppingCartInfo(0,0))

    override val childStack: StateFlow<ChildStack<*, MainComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = ChildConfig.FoodCatalog,
        handleBackButton = true,
        childFactory = ::createChild
    ).toStateFlow(lifecycle)

    init {
        products.value = repository.getProducts()
        tags.value = repository.getTags()
        categories.value = repository.getCategories()
    }

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): MainComponent.Child = when(config) {
        is ChildConfig.FoodCatalog -> {
            MainComponent.Child.FoodCatalog(
                FoodCatalogComponentImpl(
                    componentContext = componentContext,
                    products = products,
                    categories = categories,
                    shoppingCartInfo = shoppingCartInfo,
                    _tags = tags,
                    onCartClicked = {
                        navigation.push(ChildConfig.ShoppingCart)
                    },
                    onProductClicked = {
                        navigation.push(ChildConfig.DishInfo(it))
                    }
                )
            )
        }
        is ChildConfig.DishInfo -> {
            MainComponent.Child.DishInfo(
                DishInfoComponentImpl(
                    componentContext = componentContext,
                    products = products,
                    product = config.product,
                    shoppingCartInfo = shoppingCartInfo,
                    onBackClicked = {
                        navigation.pop()
                    }
                )
            )
        }
        is ChildConfig.ShoppingCart -> {
            MainComponent.Child.ShoppingCart(
                ShoppingCartComponentImpl(
                    componentContext = componentContext,
                    products = products,
                    _shoppingCartInfo = shoppingCartInfo,
                    onBackClicked = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    private sealed interface ChildConfig: Parcelable {

        @Parcelize
        object FoodCatalog: ChildConfig

        @Parcelize
        data class DishInfo(val product: Product): ChildConfig

        @Parcelize
        object ShoppingCart: ChildConfig
    }
}