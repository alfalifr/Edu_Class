package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import java.io.Serializable

/**
 * @param timestamp format $Const.FORMAT_TIMESTAMP "YYYY-MM-dd HH:mm:ss"
 */
data class Notif(private val _id: String,
                 var title: String?, var desc: String,
                 var timestamp: String): DataWithId(_id)