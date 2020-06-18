package sidev.kuliah.tekber.edu_class.model

import java.io.Serializable

data class Duration(var start: String, var end: String?= null): Serializable{
    override fun toString(): String {
        return if(end != null) "$start - $end"
        else start
    }
}