package cat.petrushkacat.foodies.core.components.main.foodcatalog.filter

import android.util.Log
import cat.petrushkacat.foodies.core.models.Tag
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeDefault
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterComponentImpl(
    componentContext: ComponentContext,
    tags: StateFlow<List<Tag>>,
    private val onFilter: (List<Tag>) -> Unit,
) : FilterComponent, ComponentContext by componentContext {

    private val scope = componentCoroutineScopeDefault()

    private val _models = MutableStateFlow(tags.value)
    override val models: StateFlow<List<Tag>> = _models

    init {
        scope.launch {
            tags.collect {
                _models.value = it
            }
        }
    }

    override fun onTagClick(tag: Tag) {
        val temp = models.value.toMutableList()

        var tagIndex: Int = -1
        models.value.forEachIndexed {i, it ->
            if(it.id == tag.id) {
                tagIndex = i
                return@forEachIndexed
            }
        }
        if(tagIndex > -1) {
            temp.removeAt(tagIndex)
            temp.add(tagIndex, tag.copy(isSelected = !tag.isSelected))
        }

        _models.value = temp
        Log.d("tags", models.value.toString())
    }

    override fun onFilterSubmitClick() {
        onFilter(models.value)
        Log.d("tags", models.value.toString())
    }
}