package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class PresenceClass(var id: String?,
                    var clazz: FK_M<ClassModel>?,
                    var presenceList: FK_M<Presence_>?,
                    var presentCount: Int= 0,
                    var ijinCount: Int= 0,
                    var alphaCount: Int= 0): Serializable