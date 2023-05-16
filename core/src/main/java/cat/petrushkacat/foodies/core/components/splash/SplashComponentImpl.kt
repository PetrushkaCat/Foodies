package cat.petrushkacat.foodies.core.components.splash

class SplashComponentImpl(
    private val onAnimationEnd: () -> Unit
) : SplashComponent {

    override fun onAnimationEnded() {
        onAnimationEnd()
    }
}