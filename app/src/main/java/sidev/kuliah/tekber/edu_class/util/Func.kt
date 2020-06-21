package sidev.kuliah.tekber.edu_class.util

import android.util.SparseArray
import android.util.SparseIntArray
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.indices
import java.time.Duration
/*
fun <T> Array<T>.toArrayList(): ArrayList<T>{
    val list= ArrayList<T>()
    this.forEach { list.add(it) }
    return list
}
 */

fun RadioGroup.getSelectedInd(): Int{
    for(i in 0 until this.childCount){
        val child= getChildAt(i)
        if(child is RadioButton){
            if(child.isChecked)
                return i
        }
    }
    return -1
}

fun <T> SparseArray<T>.keys(): List<Int>{
    val keys= ArrayList<Int>()
    for(i in 0 until this.size()){
        keys.add(this.keyAt(i))
    }
    return keys
}

operator fun <T> SparseArray<T>.iterator(): Iterator<Pair<Int, T>> {
    return object : Iterator<Pair<Int, T>>{
        var i= 0
        override fun hasNext(): Boolean {
            return i < this@iterator.size()
        }

        override fun next(): Pair<Int, T> {
            val key= this@iterator.keyAt(i++)
            return Pair(
                key,
                this@iterator[key]
            )
        }
    }
}
operator fun SparseIntArray.iterator(): Iterator<Pair<Int, Int>> {
    return object : Iterator<Pair<Int, Int>>{
        var i= 0
        override fun hasNext(): Boolean {
            return i < this@iterator.size()
        }

        override fun next(): Pair<Int, Int> {
            val key= this@iterator.keyAt(i++)
            return Pair(
                key,
                this@iterator[key]
            )
        }
    }
}

inline fun <reified T> Collection<T>.toSimpleString(): String{
    val elClsName= T::class.java.simpleName
    var str= "Collection:$elClsName:("
    for(e in this)
        str += "$e, "
    str= str.removeSuffix(", ")
    str += ")"
    return str
}
inline fun <reified T> Array<T>.toSimpleString(): String{
    val elClsName= T::class.java.simpleName
    var str= "Array:$elClsName:("
    for(e in this)
        str += "$e, "
    str= str.removeSuffix(", ")
    str += ")"
    return str
}
/*
inline fun <reified TO: DataWithId> fkmFrom(vararg model: TO): FK_M<TO>{
    val idArray= Array(model.size){ model[it].id }
    val objArray= Array(model.size){ model[it] }
    return FK_M(idArray, objArray)
}
 */