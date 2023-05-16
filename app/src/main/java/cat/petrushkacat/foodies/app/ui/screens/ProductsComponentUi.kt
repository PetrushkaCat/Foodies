package cat.petrushkacat.foodies.app.ui.screens

import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsComponentUi(component: ProductsComponent) {

    val scope = rememberCoroutineScope()
    val model by component.models.collectAsState()
    val categories by component.categories.collectAsState()
    val sortedCategories = remember {
        mutableStateOf(listOf<Category>())
    }
    val categoriesState = rememberLazyListState()
    val productsState = rememberLazyGridState()

    val products: MutableState<List<Product>> = remember {
        mutableStateOf(emptyList())
    }
    val currentCategoryId = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = currentCategoryId.value) {
        scope.launch {
            try {
                categoriesState.animateScrollToItem(
                    sortedCategories.value.indexOfFirst {
                        it.id == currentCategoryId.value
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        LazyRow(state = categoriesState) {
            items(sortedCategories.value.size) {
                val thisCategory = sortedCategories.value[it]
                if (thisCategory.id == currentCategoryId.value) {
                    Text(
                        thisCategory.name, modifier = Modifier.clickable {
                            scope.launch {
                                products.value.forEachIndexed { i, product ->
                                    if(product.category_id == thisCategory.id) {
                                        productsState.animateScrollToItem(i)
                                        return@launch
                                    }
                                }
                            }
                        },
                        style = TextStyle(color = Color.Red)
                    )
                } else {
                    Text(sortedCategories.value[it].name, modifier = Modifier.clickable {
                        scope.launch {
                            products.value.forEachIndexed { i, product ->
                                if(product.category_id == sortedCategories.value[it].id) {
                                    productsState.animateScrollToItem(i)
                                    return@launch
                                }
                            }
                        }
                    })
                }
            }
        }
        LazyVerticalGrid(
            userScrollEnabled = true,
            state = productsState,
            modifier = Modifier,
            columns = GridCells.Adaptive(150.dp), content = {
                items(products.value.size) {
                    currentCategoryId.value = products.value[it].category_id
                    ProductItem(
                        product = products.value[it],
                        onProductClick = component::onProductClick,
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

    LaunchedEffect(key1 = model) {
        scope.launch {
            val temp: MutableMap<Int, MutableList<Product>> = mutableMapOf()
            val tempCategories: MutableList<Category> = mutableListOf()
            model.forEach { product ->
                if (temp[product.category_id] == null) {
                    tempCategories.add(categories.first { it.id == product.category_id })
                    temp[product.category_id] = mutableListOf(product)
                } else {
                    temp[product.category_id]?.add(product)
                }
            }
            sortedCategories.value = tempCategories
            products.value = temp.toList().flatMap {
                it.second
            }
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
    Column(
        modifier = Modifier
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
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
                        .background(color = Color.White),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White,
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
                    product.quantity.toString(),
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )

                FilledIconButton(
                    modifier = Modifier
                        .shadow(2.dp, clip = true, shape = RoundedCornerShape(8.dp))
                        .size(40.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White,
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
}