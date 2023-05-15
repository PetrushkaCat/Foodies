package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cat.petrushkacat.foodies.core.components.main.foodcatalog.filter.FilterComponent

@Composable
fun FilterComponentUi(component: FilterComponent, onSubmit: () -> Unit) {
    val model by component.models.collectAsState()

    Column {
        LazyColumn {
            items(model.size) { index ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(model[index].name)
                    Switch(checked = model[index].isSelected, onCheckedChange = {
                        component.onTagClick(model[index])
                    })
                }
            }
        }

        Button(onClick = {
            onSubmit()
            component.onFilterSubmitClick()
        }) {
            Text("submit")
        }
    }
}