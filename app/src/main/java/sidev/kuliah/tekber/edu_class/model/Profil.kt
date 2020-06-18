package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.DataWithId
import java.io.Serializable

data class Profil(private val _id: String,
                  var uname: String, var name: String, var nrp: String, var email: String): DataWithId(_id)