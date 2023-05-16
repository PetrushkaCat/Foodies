package cat.petrushkacat.foodies.core.components

import android.util.Log
import cat.petrushkacat.foodies.core.Repository
import cat.petrushkacat.foodies.core.components.main.MainComponent
import cat.petrushkacat.foodies.core.components.main.MainComponentImpl
import cat.petrushkacat.foodies.core.components.splash.SplashComponentImpl
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeDefault
import cat.petrushkacat.foodies.core.utils.componentCoroutineScopeMain
import cat.petrushkacat.foodies.core.utils.toStateFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RootComponentImpl(
    componentContext: ComponentContext,
    private val repository: Repository
) : RootComponent, ComponentContext by componentContext {

    private val scope = componentCoroutineScopeMain()

    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: StateFlow<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialStack = {
            listOf(ChildConfig.Main, ChildConfig.Splash)
        },
        handleBackButton = false,
        childFactory = ::createChild
    ).toStateFlow(lifecycle)


    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ) = when(config) {
        is ChildConfig.Main -> {
            RootComponent.Child.Main(
                MainComponentImpl(
                    componentContext = componentContext,
                    repository = repository
                )
            )
        }

        is ChildConfig.Splash -> {
            RootComponent.Child.Splash(
                SplashComponentImpl {
                    scope.launch {
                        Log.d("launch" ,"1")
                        navigation.pop()
                    }
                }
            )
        }
    }

    private sealed interface ChildConfig: Parcelable {

        @Parcelize
        object Main: ChildConfig

        @Parcelize
        object Splash: ChildConfig
    }
}