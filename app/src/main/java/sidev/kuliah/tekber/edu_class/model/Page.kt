package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class Page(private val _id: String,
                var name: String,
                var no: Int,
                var contentList: FK_M<Content>?): DataWithId(_id)