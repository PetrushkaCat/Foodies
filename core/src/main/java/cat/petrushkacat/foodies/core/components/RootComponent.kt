package cat.petrushkacat.foodies.core.components

import cat.petrushkacat.foodies.core.components.main.MainComponent
import cat.petrushkacat.foodies.core.components.splash.SplashComponent
import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {

    val childStack: StateFlow<ChildStack<*, Child>>
    sealed interface Child {
        data class Main(val component: MainComponent): Child

        data class Splash(val component: SplashComponent): Child
    }
}