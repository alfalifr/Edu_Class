package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import java.io.Serializable

data class ContentRead(var id: String?, var title: String?, var desc: String): Serializable, Content