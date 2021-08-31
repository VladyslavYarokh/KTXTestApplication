package kz.beeline.cars.adapterdelegatewithdagger

import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.FooterType
import kz.beeline.cars.adapterdelegatewithdagger.data.HeaderType
import kz.beeline.cars.adapterdelegatewithdagger.data.TypeMarker
import javax.inject.Inject

object ListUtils {
    fun provideList(): List<TypeMarker> = listOf(
        HeaderType("Main header"),
        DataType("Milk", 10, 13),
        DataType("Bread", 3, 4),
        DataType("Beef", 2, 25),
        DataType("Oil", 12, 10),
        FooterType("Total price is")
    )
}