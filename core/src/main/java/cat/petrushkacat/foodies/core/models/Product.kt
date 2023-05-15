package cat.petrushkacat.foodies.core.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val price_current: Int,
    val price_old: Int,
    val category_id: Int,
    val measure: Int,
    val measure_unit: String,
    val energy_per_100_grams: Double,
    val proteins_per_100_grams: Double,
    val fats_per_100_grams: Double,
    val carbohydrates_per_100_grams: Double,
    val tag_ids: List<Int>,

    val quantity: Int = 0,
): Parcelable