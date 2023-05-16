package cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar

import cat.petrushkacat.foodies.core.models.Tag
import kotlinx.coroutines.flow.StateFlow

interface FoodCatalogToolbarComponent {

    val selectedTags: StateFlow<List<Tag>>
    fun onSearch(text: String)
}