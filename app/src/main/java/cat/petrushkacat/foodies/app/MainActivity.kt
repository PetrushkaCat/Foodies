package cat.petrushkacat.foodies.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cat.petrushkacat.foodies.app.ui.screens.MainComponentUi
import cat.petrushkacat.foodies.app.ui.theme.FoodiesTheme
import cat.petrushkacat.foodies.core.components.main.MainComponentImpl
import cat.petrushkacat.foodies.data.repository.RepositoryImpl
import com.arkivanov.decompose.defaultComponentContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = RepositoryImpl()
        val component = MainComponentImpl(defaultComponentContext(), repository)

        setContent {
            FoodiesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComponentUi(component = component)
                }
            }
        }
    }
}

