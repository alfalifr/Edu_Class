package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class Question(var id: String?,
                var content: FK_M<ContentQuestion>?,
                var scoreIfCorrect: Int,
                var isCorrect: Boolean= false
): Serializable