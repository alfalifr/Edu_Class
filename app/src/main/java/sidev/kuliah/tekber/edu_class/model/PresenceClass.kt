package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

/**
 * @param scheduleList isinya bisa banyak.
 * @param clazz isinya cuma 1.
 */
data class PresenceClass(private val _id: String,
                         var clazz: FK_M<ClassModel>?,
                         var scheduleList: FK_M<ScheduleModel>?,
                         var presenceList: FK_M<Presence_>?,
                         var presentCount: Int= 0,
                         var ijinCount: Int= 0,
                         var alphaCount: Int= 0): DataWithId(_id)