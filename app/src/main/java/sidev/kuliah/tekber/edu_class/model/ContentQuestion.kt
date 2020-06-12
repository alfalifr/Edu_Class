package sidev.kuliah.tekber.edu_class.model

import sidev.kuliah.tekber.edu_class.intfc.Content
import java.io.Serializable

data class ContentQuestion(var id: String?, var question: String,
                           var answerKind: Int,
                           var answerChoice: Array<String>?,
                            var answerByTeacher: Array<String>?,
                            var answerByReader: Array<String>?): Serializable, Content {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentQuestion

        if (id != other.id) return false
        if (question != other.question) return false
        if (answerKind != other.answerKind) return false
        if (answerChoice != null) {
            if (other.answerChoice == null) return false
            if (!answerChoice!!.contentEquals(other.answerChoice!!)) return false
        } else if (other.answerChoice != null) return false
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
        result = 31 * result + (answerChoice?.contentHashCode() ?: 0)
        result = 31 * result + (answerByTeacher?.contentHashCode() ?: 0)
        result = 31 * result + (answerByReader?.contentHashCode() ?: 0)
        return result
    }
}