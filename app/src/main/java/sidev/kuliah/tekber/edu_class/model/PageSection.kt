package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class PageSection(private val _id: String, var content: FK_M<Content>?): DataWithId(_id)