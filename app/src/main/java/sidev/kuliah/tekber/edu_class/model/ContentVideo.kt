package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import java.io.Serializable

data class ContentVideo(var id: String?, var link: String, var note: String?): Serializable, Content