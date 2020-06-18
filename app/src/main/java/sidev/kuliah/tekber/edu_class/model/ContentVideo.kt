package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.lib.android.siframe.model.DataWithId
import java.io.Serializable

data class ContentVideo(private val _id: String, var link: String, var note: String?): Content(_id)