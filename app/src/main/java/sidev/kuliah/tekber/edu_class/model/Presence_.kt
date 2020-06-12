package sidev.kuliah.tekber.edu_class.model

import java.io.Serializable

data class Presence_(var id: String?,
                     var date: String, var status: Int,
                     var news: String?, var attachment: ArrayList<Any>?): Serializable