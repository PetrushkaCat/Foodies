package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.app.ui.screens.shared.AddInCartButton
import cat.petrushkacat.foodies.core.components.main.dishinfo.DishInfoComponent

@Composable
fun DishInfoComponentUi(component: DishInfoComponent) {

    val product by component.models.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            Box(modifier = Modifier.background(Color.Transparent)) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                        .align(Alignment.TopStart)
                        .shadow(2.dp, shape = CircleShape)
                        .size(44.dp)
                        .background(color = Color.White, shape = CircleShape),
                    onClick = {
                        component.onBackClick()
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_arrow_left),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.img), contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                product.name,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                product.description.replace("  ", "\n"),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Divider(Modifier.fillMaxWidth())
            InfoItem(name = "Вес", value = product.measure.toString() + " " + product.measure_unit)
            InfoItem(
                name = "Энерг. ценность",
                value = product.energy_per_100_grams.toString() + " ккал"
            )
            InfoItem(name = "Белки", value = product.proteins_per_100_grams.toString() + " г.")
            InfoItem(name = "Жиры", value = product.fats_per_100_grams.toString() + " г.")
            InfoItem(
                name = "Углеводы",
                value = product.carbohydrates_per_100_grams.toString() + " г."
            )
        }
        AddInCartButton(
            product = product,
            onButtonPlusClick = component.addInCartComponent::plusItem,
            onButtonMinusClick = component.addInCartComponent::minusItem,
            48.dp,
            MaterialTheme.colorScheme.primary,
            Color.White,
            "В корзину за ${product.price_current} ₽",
            product.price_current.toString() + " ₽" + "   x" + product.quantity,
            displayOldPrice = false
        )

    }
}

@Composable
fun InfoItem(name: String, value: String) {

    Column(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(name, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
            Text(value, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
        }
        Divider(Modifier.fillMaxWidth())
    }
}