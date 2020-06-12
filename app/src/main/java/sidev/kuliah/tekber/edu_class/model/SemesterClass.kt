package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class SemesterClass(var id: String,
                    var clazz: FK_M<ClassModel>?,
                    var semester: Int): Serializable