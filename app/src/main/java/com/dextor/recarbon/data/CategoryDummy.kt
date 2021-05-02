package com.dextor.recarbon.data

import com.dextor.recarbon.R
import com.dextor.recarbon.model.CategoryData

class CategoryDummy {
    companion object {
        fun dataSet(): ArrayList<CategoryData> {
            val list = ArrayList<CategoryData>()
            list.add(
                CategoryData(
                    R.drawable.car_white_icon,
                    "Mobil",
                    "#D13F40"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.motor_white_icon,
                    "Motor",
                    "#1E3C54"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.plastic_white_icon,
                    "Plastik",
                    "#013833"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.flash_white_icon,
                    "Listrik",
                    "#F4C564"
                )
            )

            list.add(
                CategoryData(
                    R.drawable.walk_white_icon,
                    "Jalan",
                    "#FEB7CF"
                )
            )
            return list
        }
    }
}