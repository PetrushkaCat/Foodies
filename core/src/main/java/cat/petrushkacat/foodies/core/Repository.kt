package cat.petrushkacat.foodies.core

import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.Tag

interface Repository {

    fun getProducts(): List<Product>

    fun getTags(): List<Tag>

    fun getCategories(): List<Category>
}