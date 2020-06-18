package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import java.io.Serializable

/**
 * @param answerChoice jika answerKind selain PILGAN atau MULTIPLE, maka answerChoice null.
 * @param answerByTeacher jika answerKind merupakan PILGAN atau MULTIPLE, maka answerByReader berisi Int yg merepresentasikan index jwb.
 * @param answerByReader jika answerKind merupakan PILGAN atau MULTIPLE, maka answerByReader berisi Int yg merepresentasikan index jwb.
 */
data class ContentQuestion(private val _id: String, var question: String,
                           var answerKind: Int,
                           var answerChoice: Array<String>?,
                            var answerByTeacher: Array<String>?,
                            var answerByReader: Array<String>?): Content(_id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentQuestion

        if (id != other.id) return false
        if (question != other.question) return false
        if (answerKind != other.answerKind) return false
        if (answerChoice != other.answerChoice) return false
        if (answerByTeacher != null) {
            if (other.answerByTeacher == null) return false
            if (!answerByTeacher!!.contentEquals(other.answerByTeacher!!)) return false
        } else if (other.answerByTeacher != null) return false
        if (answerByReader != null) {
            if (other.answerByReader == null) return false
            if (!answerByReader!!.contentEquals(other.answerByReader!!)) return false
        } else if (other.answerByReader != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + question.hashCode()
        result = 31 * result + answerKind
        result = 31 * result + (answerChoice?.hashCode() ?: 0)
        result = 31 * result + (answerByTeacher?.contentHashCode() ?: 0)
        result = 31 * result + (answerByReader?.contentHashCode() ?: 0)
        return result
    }
}