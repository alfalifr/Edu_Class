package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import sidev.lib.android.siframe.model.PictModel
import java.io.Serializable

/**
 * @param subname pembagian kelasnya, misalnya kelas A, B, C, dkk.
 */
data class ClassModel(private val _id: String,
                var name: String, var subname: String= "_",
                var teacher: String, var sks: Int,
                var moduleList: FK_M<Module>?,
                var img: PictModel?): DataWithId(_id)