package cat.petrushkacat.foodies.core.components.shared

interface AddInCartComponent {

    fun plusItem(itemId: Int)

    fun minusItem(itemId: Int)
}