package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.FK_M
import sidev.lib.android.siframe.model.PictModel
import java.io.Serializable

/**
 * @param duration sebaiknya .start dan .end hanya berupa angka.
 */
data class Module(var id: String,
                  var name: String, var desc: String,
                  var img: PictModel?,
                  var duration: Duration,
                  var pageList: FK_M<Page>?
): Serializable