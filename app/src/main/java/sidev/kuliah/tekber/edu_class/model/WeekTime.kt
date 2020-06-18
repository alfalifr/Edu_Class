package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId

data class WeekTime(private val _id: String, var date: String, var week: Int): DataWithId(_id)