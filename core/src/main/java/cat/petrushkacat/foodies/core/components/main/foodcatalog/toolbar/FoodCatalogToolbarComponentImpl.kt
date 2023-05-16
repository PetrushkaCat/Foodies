package cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar

import cat.petrushkacat.foodies.core.models.Tag
import kotlinx.coroutines.flow.StateFlow

class FoodCatalogToolbarComponentImpl(
    _selectedTags: StateFlow<List<Tag>>,
    private val onSearched: (String) -> Unit
) : FoodCatalogToolbarComponent {

    override val selectedTags = _selectedTags

    override fun onSearch(text: String) {
        onSearched(text)
    }
}