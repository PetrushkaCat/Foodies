package cat.petrushkacat.foodies.app.ui.screens.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.core.models.Product

@Composable
fun AddInCartButton(
    product: Product,
    onButtonPlusClick: (Int) -> Unit,
    onButtonMinusClick: (Int) -> Unit,
    height: Dp,
    color: Color,
    contentColor: Color,
    text: String,
    textWhenClicked: String? = null,
    displayOldPrice: Boolean = true,
    secondButtonsColor: Color = Color.White
) {

    if (product.quantity == 0) {
        Button(
            modifier = Modifier
                .padding(12.dp)
                .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                .height(height)
                .background(color = color),
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = Color.DarkGray
            ),
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
                    text,
                    style = MaterialTheme.typography.bodyMedium
                        .copy(fontWeight = FontWeight(500))
                        .copy(color = contentColor),

                    )
                if (product.price_old != 0 && displayOldPrice) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        product.price_old.toString() + "₽",
                        textDecoration = TextDecoration.LineThrough,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                        maxLines = 1
                    )
                }
            }
        }
    } else {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                modifier = Modifier
                    .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                    .size(40.dp)
                    .background(color = secondButtonsColor),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = secondButtonsColor,
                    contentColor = Color.Red
                ),
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
                textWhenClicked ?: product.quantity.toString(),
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            FilledIconButton(
                modifier = Modifier
                    .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                    .size(40.dp)
                    .background(color = secondButtonsColor, shape = RoundedCornerShape(8.dp)),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = secondButtonsColor,
                    contentColor = Color.Red
                ),
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