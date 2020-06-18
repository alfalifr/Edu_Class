package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.lib.android.siframe.model.DataWithId
import java.io.Serializable

data class ContentRead(private val _id: String, var title: String?, var desc: String): Content(_id)