package cat.petrushkacat.foodies.app.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.petrushkacat.foodies.app.R
import cat.petrushkacat.foodies.core.components.main.foodcatalog.toolbar.FoodCatalogToolbarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCatalogToolbarComponentUi(component: FoodCatalogToolbarComponent, onFilterButtonClick: ()-> Unit) {

    val isExpanded = rememberSaveable { mutableStateOf(false) }
    val selectedTage = component.selectedTags.collectAsState()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
        title = {
        },
        navigationIcon = {
            Box(modifier = Modifier.size(40.dp)) {
                if (selectedTage.value.isNotEmpty()) {
                    Text(
                        selectedTage.value.size.toString(),
                        style = TextStyle(fontSize = 10.sp, color = Color.White),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.background(
                            MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ).align(Alignment.TopEnd).size(14.dp)
                    )
                }
                Icon(painterResource(id = R.drawable.img_filter),
                    null,
                    modifier = Modifier
                        .clickable {
                            onFilterButtonClick()
                        }
                        .size(40.dp)
                        .padding(horizontal = 10.dp)
                )
            }
        },
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
    val searchText = rememberSaveable { mutableStateOf("") }

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
        SideEffect() {
            if(searchText.value == "") {
                focusRequester.requestFocus()
            }
        }
        TextField(value = searchText.value,
            onValueChange = {
                searchText.value = it
                onSearch(searchText.value)
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                focusedLabelColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
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
                Text("Найти блюда")
            }
        )
    }
}