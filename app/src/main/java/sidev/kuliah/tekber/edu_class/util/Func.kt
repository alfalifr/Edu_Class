package sidev.kuliah.tekber.edu_class.util

import android.widget.RadioButton
import android.widget.RadioGroup
import sidev.lib.android.siframe.model.DataWithId
import sidev.lib.android.siframe.model.FK_M
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
/*
inline fun <reified TO: DataWithId> fkmFrom(vararg model: TO): FK_M<TO>{
    val idArray= Array(model.size){ model[it].id }
    val objArray= Array(model.size){ model[it] }
    return FK_M(idArray, objArray)
}
 */