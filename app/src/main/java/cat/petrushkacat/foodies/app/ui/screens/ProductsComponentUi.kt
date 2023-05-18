package cat.petrushkacat.foodies.app.ui.screens

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.app.ui.screens.shared.AddInCartButton
import cat.petrushkacat.foodies.core.components.main.foodcatalog.products.ProductsComponent
import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun ProductsComponentUi(component: ProductsComponent) {

    val scope = rememberCoroutineScope()
    val model by component.models.collectAsState()
    val categories by component.categories.collectAsState()
    val shoppingCartInfo by component.shoppingCartInfo.collectAsState()

    val sortedCategories = remember {
        mutableStateOf(listOf<Category>())
    }
    val categoriesState = rememberLazyListState()
    val productsState = rememberLazyGridState()

    val products: MutableState<List<Product>> = remember {
        mutableStateOf(emptyList())
    }
    val currentCategoryId = remember { mutableStateOf(0) }

    val oldProductsSize = rememberSaveable { mutableStateOf(products.value.size) }

    val isCategoryClicked = remember { mutableStateOf(false) }


    if(model.isEmpty()) {
        Column(modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {//захардкожено :(
            Text("Упс, ничего не найдено...",
                style = MaterialTheme.typography.bodyMedium.copy(Color.Gray),
                textAlign = TextAlign.Center
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        LazyRow(
            modifier = Modifier
                .padding(horizontal = 4.dp),
            state = categoriesState
        ) {
            items(sortedCategories.value.size) {

                val thisCategory = sortedCategories.value[it]

                CategoryItem(
                    category = thisCategory,
                    isCurrent = thisCategory.id == currentCategoryId.value
                ) {
                    scope.launch {
                        isCategoryClicked.value = true
                        products.value.forEachIndexed { i, product ->
                            if (product.category_id == thisCategory.id) {
                                productsState.animateScrollBy(
                                    (i - productsState.firstVisibleItemIndex) * 395f,
                                    tween((i - productsState.firstVisibleItemIndex).absoluteValue * 50,
                                        easing = LinearEasing)
                                )
                                productsState.animateScrollToItem(i)
                                return@launch
                            }
                        }
                        isCategoryClicked.value = false
                    }

                }
            }
        }
        Column {
            LazyVerticalGrid(
                userScrollEnabled = true,
                state = productsState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                columns = GridCells.Adaptive(150.dp), content = {
                    items(products.value.size) {
                        ProductItem(
                            Modifier,
                                //.animateItemPlacement(tween(500)),
                            product = products.value[it],
                            onProductClick = component::onProductClick,
                            onButtonPlusClick = component.addInCartComponent::plusItem,
                            onButtonMinusClick = component.addInCartComponent::minusItem
                        )
                    }
                })

            if (shoppingCartInfo.itemsPrice > 0) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 12.dp)
                        .shadow(1.dp, clip = true, shape = RoundedCornerShape(8.dp))
                        .height(48.dp),
                    onClick = { component.onCartButtonClick() }) {
                    Box(modifier = Modifier.size(40.dp)) {
                        Text(
                            shoppingCartInfo.itemsQuantity.toString(),
                            style = TextStyle(fontSize = 10.sp, color = Color.White),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                                .align(Alignment.TopStart)
                                .size(14.dp)
                        )
                        Icon(
                            painterResource(id = R.drawable.img_cart),
                            null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(5.dp)
                        )
                    }

                    Text(
                        shoppingCartInfo.itemsPrice.toString() + " ₽",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight(500))
                    )
                }
            }
        }
    }

    //When user use filter or searching it scrolls to start of the list
    SideEffect {
        scope.launch {
            delay(500) //when navigate from details screen we have to wait because products are not yet loaded
            if (products.value.size != oldProductsSize.value) {
                productsState.scrollToItem(1)
                oldProductsSize.value = products.value.size
            }
        }
    }
    //Sorting, to make products in one category be in the same part of the list
    LaunchedEffect(key1 = model) {
        CoroutineScope(Dispatchers.Default).launch {
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
    //Scrolls categories
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
    //when visible category in products changes it changes currentCategoryId and the func above is called
    Log.d("ProductsScreen", "recomposition...")
    LaunchedEffect(key1 = productsState) {
        delay(500) //when app starts we have to wait because products are not yet loaded
        snapshotFlow { productsState.firstVisibleItemIndex }.collect {
            try {
                currentCategoryId.value = products.value[it + 1].category_id
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, isCurrent: Boolean, onClick: () -> Unit) {
    var buttonColor = Color.Transparent

    val style = if (isCurrent) {
        buttonColor = MaterialTheme.colorScheme.primary
        MaterialTheme.typography.bodyMedium.copy(color = Color.White)
    } else {
        MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
    }

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        }) {
        Text(category.name, style = style)
    }
}

@Composable
fun ProductItem(
    modifier: Modifier,
    product: Product,
    onProductClick: (Product) -> Unit,
    onButtonPlusClick: (Int) -> Unit,
    onButtonMinusClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .background(
                color = cat.petrushkacat.foodies.app.ui.theme.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable {
                onProductClick(product)
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Row {
                if (product.price_old != 0) {
                    Image(
                        painterResource
                            (id = R.drawable.img_discount),
                        null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                if (product.tag_ids.contains(2)) {
                    Image(
                        painterResource
                            (id = R.drawable.img_vegan),
                        null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                if (product.tag_ids.contains(4)) {
                    Image(
                        painterResource
                            (id = R.drawable.img_hot),
                        null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
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

        AddInCartButton(
            product = product,
            onButtonPlusClick = onButtonPlusClick,
            onButtonMinusClick = onButtonMinusClick,
            40.dp,
            Color.White,
            Color.Black,
            product.price_current.toString() + "₽"
        )
    }
}