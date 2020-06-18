package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

/**
 * @param smt berupa angka
 */
data class PresenceClassSmt(private val _id: String,
                    var smt: String,
                    var presenceClassList: FK_M<PresenceClass>?): DataWithId(_id)