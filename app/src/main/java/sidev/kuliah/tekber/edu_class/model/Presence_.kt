package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.model.DataWithId
import java.io.Serializable

/**
 * @param news berupa berita acara jika status == Const.STATUS_PRESENCE_PRESENT
 *             berupa keterangan ijin jika status == Const.STATUS_PRESENCE_IJIN
 */
data class Presence_(private val _id: String,
                     var date: String, var status: Int,
                     var news: String?,
                     var excuseReason: String?,
                     var attachment: ArrayList<Any>?): DataWithId(_id)