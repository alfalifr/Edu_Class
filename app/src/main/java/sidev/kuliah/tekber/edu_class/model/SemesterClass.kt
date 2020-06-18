package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class SemesterClass(private val _id: String,
                    var clazz: FK_M<ClassModel>?,
                    var semester: Int): DataWithId(_id)