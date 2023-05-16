package cat.petrushkacat.foodies.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foodies.core.components.main.foodcatalog.FoodCatalogComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FoodCatalogComponentUi(component: FoodCatalogComponent) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden, skipHalfExpanded = true)

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
        FilterComponentUi(component = component.filterComponent, {
            scope.launch {
                sheetState.hide()
            }
        })
    },
        sheetState = sheetState
    ) {
        Column {
            FoodCatalogToolbarComponentUi(
                component = component.toolbarComponent,
                onFilterButtonClick = {
                    scope.launch {
                        sheetState.show()
                    }
                })
            ProductsComponentUi(component = component.productsComponent)
        }
    }
}
