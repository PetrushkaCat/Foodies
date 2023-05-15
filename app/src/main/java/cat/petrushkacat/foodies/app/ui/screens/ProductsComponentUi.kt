package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.core.components.main.foodcatalog.products.ProductsComponent
import cat.petrushkacat.foodies.core.models.Product

@Composable
fun ProductsComponentUi(component: ProductsComponent) {

    val model by component.models.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        LazyVerticalGrid(
            state = rememberLazyGridState(),
            modifier = Modifier
                .fillMaxSize()
                .weight(7f),
            columns = GridCells.Adaptive(150.dp), content = {
                items(model.size) {
                    ProductItem(
                        product = model[it], onProductClick = component::onProductClick,
                        onButtonPlusClick = component.addInCartComponent::plusItem,
                        onButtonMinusClick = component.addInCartComponent::minusItem
                    )
                }
            })
        Button(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 16.dp)
                .shadow(1.dp, clip = true, shape = RoundedCornerShape(8.dp))
                .height(48.dp),
            onClick = { component.onCartButtonClick() }) {
            Text("shopping cart")
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Product) -> Unit,
    onButtonPlusClick: (Int) -> Unit,
    onButtonMinusClick: (Int) -> Unit
) {
    Column(modifier = Modifier
        .padding(4.dp)
        .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
        .clickable {
            onProductClick(product)
        },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            product.name,
            modifier = Modifier.padding(horizontal = 12.dp),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1
        )

        Text(
            product.measure.toString() + " " + product.measure_unit,
            modifier = Modifier.padding(horizontal = 12.dp),
            style = MaterialTheme.typography.labelMedium.copy(color = Color.Gray),
        )

        if (product.quantity == 0) {
            Button(
                modifier = Modifier
                    .padding(12.dp)
                    .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                    .height(40.dp)
                    .background(color = Color.White),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.DarkGray),
                onClick = {
                    onButtonPlusClick(product.id)
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        product.price_current.toString() + "₽",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500)),

                    )
                    if (product.price_old != 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            product.price_old.toString() + "₽",
                            textDecoration = TextDecoration.LineThrough,
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    modifier = Modifier
                        .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                        .size(40.dp)
                        .background(color = Color.White),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White, contentColor = Color.Red),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        onButtonMinusClick(product.id)
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_minus),
                        null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    product.quantity.toString(),
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                FilledIconButton(
                    modifier = Modifier
                        .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                        .size(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White, contentColor = Color.Red),
                    shape = RoundedCornerShape(8.dp),
                    onClick = {
                        onButtonPlusClick(product.id)
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_plus),
                        null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}