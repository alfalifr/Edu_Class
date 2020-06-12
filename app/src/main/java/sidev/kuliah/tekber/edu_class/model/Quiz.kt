package sidev.kuliah.tekber.edu_class.model

import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

data class Quiz(var id: String?,
            var title: String, var desc: String?,
            var questions: FK_M<Question>?,
            var correctCount: Int
): Serializable