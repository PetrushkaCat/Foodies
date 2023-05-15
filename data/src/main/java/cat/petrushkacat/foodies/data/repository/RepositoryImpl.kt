package cat.petrushkacat.foodies.data.repository

import cat.petrushkacat.foodies.core.Repository
import cat.petrushkacat.foodies.core.models.Category
import cat.petrushkacat.foodies.core.models.Product
import cat.petrushkacat.foodies.core.models.Tag
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RepositoryImpl: Repository {

    override fun getProducts(): List<Product> {
        val type = object: TypeToken<List<Product>>() {}.type
        return Gson().fromJson(getProductsJson(), type)
    }

    override fun getTags(): List<Tag> {
        val type = object: TypeToken<List<Tag>>() {}.type
        return Gson().fromJson(getTagsJson(), type)
    }

    override fun getCategories(): List<Category> {
        val type = object: TypeToken<List<Category>>() {}.type
        return Gson().fromJson(getCategoriesJson(), type)
    }
}