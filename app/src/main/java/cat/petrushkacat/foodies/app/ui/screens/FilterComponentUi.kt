package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foodies.core.components.main.foodcatalog.filter.FilterComponent

@Composable
fun FilterComponentUi(component: FilterComponent, onSubmit: () -> Unit) {
    val model by component.models.collectAsState()

    Column(modifier = Modifier
        .padding(vertical = 32.dp, horizontal = 34.dp)
    ) {
        Text("Подобрать блюда", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(model.size) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(model[index].name)
                    Checkbox(checked = model[index].isSelected, onCheckedChange = {
                        component.onTagClick(model[index])
                    })
                }
                if(index != model.size - 1) {
                    Divider(Modifier.fillMaxWidth())
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .shadow(1.dp, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(48.dp),
            onClick = {
            onSubmit()
            component.onFilterSubmitClick()
        }) {
            Text("Готово", style = MaterialTheme.typography.bodyMedium.copy(color = Color.White))
        }
    }
}