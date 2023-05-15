package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.core.components.main.shoppingcart.ShoppingCartComponent
import cat.petrushkacat.foodies.core.models.Product

@Composable
fun ShoppingCartComponentUi(component: ShoppingCartComponent) {
    val model by component.models.collectAsState()

    Column() {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().weight(7f),
            columns = GridCells.Adaptive(150.dp), content = {
                items(model.size) {
                    ShoppingCartItem(
                        product = model[it],
                        onButtonPlusClick = component.addInCartComponent::plusItem,
                        onButtonMinusClick = component.addInCartComponent::minusItem
                    )
                }
            })
    }
}

@Composable
fun ShoppingCartItem(product: Product, onButtonPlusClick: (Int) -> Unit, onButtonMinusClick: (Int) -> Unit) {
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
                onButtonPlusClick(product.id)
            }) {
                Text(product.price_current.toString())
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {
                    onButtonMinusClick(product.id)
                }) {
                    Text("-")
                }
                Text(product.quantity.toString())
                Button(onClick = {
                    onButtonPlusClick(product.id)
                }) {
                    Text("+")
                }
            }
        }
    }
}