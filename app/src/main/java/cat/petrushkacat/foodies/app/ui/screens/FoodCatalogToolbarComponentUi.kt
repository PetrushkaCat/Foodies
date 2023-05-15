package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar.FoodCatalogToolbarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCatalogToolbarComponentUi(component: FoodCatalogToolbarComponent, onFilterButtonClick: ()-> Unit) {

    val isExpanded = remember { mutableStateOf(false) }

    TopAppBar(
        title = {
        },
        navigationIcon = {
            Icon(painterResource(id = R.drawable.img_filter),
            null, 
            modifier = Modifier
                .clickable {
                    onFilterButtonClick()
                }
                .size(40.dp)
                .padding(horizontal = 10.dp)
            )},
        actions = {
            Row(modifier = Modifier.fillMaxWidth()) {
                if(!isExpanded.value) {
                    Spacer(modifier = Modifier.width(60.dp))
                }
                Image(
                    painter = painterResource(id = R.drawable.img_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f)
                )
                SearchView(onSearch = component::onSearch, isExpanded)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(onSearch: (String) -> Unit, isExpanded: MutableState<Boolean>) {
    val searchText = remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }

    if(!isExpanded.value) {
        Icon(
            painterResource(id = R.drawable.img_search),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    isExpanded.value = true
                }
                .size(48.dp)
                .padding(horizontal = 10.dp)
        )
    } else {
        SideEffect {
            focusRequester.requestFocus()
        }
        TextField(value = searchText.value,
            onValueChange = {
                searchText.value = it
                onSearch(searchText.value)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedLabelColor = Color.Transparent,
                //focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            trailingIcon = {
                Icon(
                    painterResource(id = R.drawable.img_cancel),
                    null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                        .clickable {
                            searchText.value = ""
                            isExpanded.value = false
                            onSearch(searchText.value)
                        })
            },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.img_arrow_left),
                    null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp)
                        .clickable {
                            searchText.value = ""
                            isExpanded.value = false
                            onSearch(searchText.value)
                        },
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            placeholder = {
                Text("Search dishes")
            }
        )
    }
}