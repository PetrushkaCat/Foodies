package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.core.components.main.dishinfo.DishInfoComponent

@Composable
fun DishInfoComponentUi(component: DishInfoComponent) {
    val product by component.models.collectAsState()
    Column(modifier = Modifier.clickable {

    }) {
        Image(
            painter = painterResource(id = R.drawable.img)
            , contentDescription = null,
            modifier = Modifier.size(170.dp)
        )
        Text(product.name)
        Text(product.measure.toString())
        if(product.quantity == 0) {
            Button(onClick = {
                component.addInCartComponent.plusItem(product.id)
            }) {
                Text(product.price_current.toString())
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    component.addInCartComponent.minusItem(product.id)
                }) {
                    Text("-")
                }
                Text(product.quantity.toString())
                Button(onClick = {
                    component.addInCartComponent.plusItem(product.id)
                }) {
                    Text("+")
                }
            }
        }
    }
}