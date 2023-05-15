package cat.petrushkacat.foodies.core.components.main.foodcatalog

import cat.petrushkacat.foodies.core.components.main.foodcatalog.filter.FilterComponent
import cat.petrushkacat.foodies.core.components.main.foodcatalog.products.ProductsComponent
import cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar.FoodCatalogToolbarComponent

interface FoodCatalogComponent {

    val toolbarComponent: FoodCatalogToolbarComponent

    val productsComponent: ProductsComponent

    val filterComponent: FilterComponent
}