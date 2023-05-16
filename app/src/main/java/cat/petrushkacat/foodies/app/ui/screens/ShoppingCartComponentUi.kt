package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import cat.petrushkacat.foodies.app.ui.screens.shared.AddInCartButton
import cat.petrushkacat.foodies.core.components.main.shoppingcart.ShoppingCartComponent
import cat.petrushkacat.foodies.core.models.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartComponentUi(component: ShoppingCartComponent) {

    val model by component.models.collectAsState()
    val shoppingCartInfo by component.shoppingCartInfo.collectAsState()

    Column() {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            title = {
                Row {
                    Spacer(modifier = Modifier.width(30.dp))
                    Text(
                        "Корзина",
                        style = TextStyle(fontWeight = FontWeight(600), fontSize = 20.sp)
                    )
                }
            },
            navigationIcon = {
                Icon(
                    painterResource(id = R.drawable.img_arrow_left),
                    null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(7.dp)
                        .clickable {
                            component.onBackClick()
                        }
                )
            },
            actions = {}
        )

        Column() {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                content = {
                    items(model.size) {
                        ShoppingCartItem(
                            product = model[it],
                            onButtonPlusClick = component.addInCartComponent::plusItem,
                            onButtonMinusClick = component.addInCartComponent::minusItem
                        )
                        Divider(Modifier.fillMaxWidth())
                    }
                })
            Button(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 12.dp)
                    .shadow(1.dp, clip = true, shape = RoundedCornerShape(8.dp))
                    .height(48.dp),
                onClick = { },
                content = {
                    Text("Оформить за " + shoppingCartInfo.itemsPrice.toString() + " ₽",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500))
                    )
                }
            )
        }
    }
}

@Composable
fun ShoppingCartItem(
    product: Product,
    onButtonPlusClick: (Int) -> Unit,
    onButtonMinusClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.img), contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(product.name, modifier = Modifier.padding(horizontal = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.width(155.dp),
                ) {
                    AddInCartButton(
                        product = product,
                        onButtonPlusClick = onButtonPlusClick,
                        onButtonMinusClick = onButtonMinusClick,
                        height = 44.dp,
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        text = "",
                        secondButtonsColor = cat.petrushkacat.foodies.app.ui.theme.LightGray
                    )
                }

                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text((product.price_current * product.quantity).toString() + " ₽",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight(600)))

                    if(product.price_old != 0) {
                        Text((product.price_old * product.quantity).toString() + " ₽",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }
        }
    }
}