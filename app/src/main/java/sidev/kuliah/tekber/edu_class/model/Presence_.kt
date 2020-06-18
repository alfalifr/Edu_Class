package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import java.io.Serializable

data class Presence_(private val _id: String,
                     var date: String, var status: Int,
                     var news: String?, var attachment: ArrayList<Any>?): DataWithId(_id)