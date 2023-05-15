package cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar

class FoodCatalogToolbarComponentImpl(
   // private val onFilterClick: () -> Unit,
    private val onSearched: (String) -> Unit
) : FoodCatalogToolbarComponent {

    //override fun onFilterButtonClick() = onFilterClick()

    override fun onSearch(text: String) {
        onSearched(text)
    }
}