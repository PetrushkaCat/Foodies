package cat.petrushkacat.foodies.core.components.main.foodcatalog.filter

import cat.petrushkacat.foodies.core.models.Tag
import kotlinx.coroutines.flow.StateFlow

interface FilterComponent {

    val models: StateFlow<List<Tag>>

    fun onTagClick(tag: Tag)

    fun onFilterSubmitClick()
}