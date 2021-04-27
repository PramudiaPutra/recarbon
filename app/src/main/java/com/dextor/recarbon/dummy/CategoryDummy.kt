package com.dextor.recarbon.dummy

import com.dextor.recarbon.R
import com.dextor.recarbon.data.CategoryData

class CategoryDummy {
    companion object {
        fun dataSet(): ArrayList<CategoryData> {
            val list = ArrayList<CategoryData>()
            list.add(
                CategoryData(
                    R.drawable.car_white_icon,
                    "Mobil"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.motor_white_icon,
                    "Motor"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.plastic_white_icon,
                    "Plastik"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.flash_white_icon,
                    "Listrik"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.walk_white_icon,
                    "Jalan"
                )
            )
            return list
        }
    }
}