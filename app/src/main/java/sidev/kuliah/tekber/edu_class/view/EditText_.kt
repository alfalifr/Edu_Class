package sidev.kuliah.tekber.edu_class.view

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText

class EditText_ : EditText{

    constructor(c: Context): super(c)
    constructor(c: Context, attrs: AttributeSet): super(c, attrs)

    private var textChangeListener: ArrayList<TextWatcher>?= null

    override fun addTextChangedListener(watcher: TextWatcher) {
        super.addTextChangedListener(watcher)
        if(textChangeListener == null)
            textChangeListener= ArrayList()
        textChangeListener!!.add(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher?) {
        super.removeTextChangedListener(watcher)
        textChangeListener?.remove(watcher)
    }

    fun hasTextChangeListener(): Boolean{
        return textChangeListener?.isNotEmpty() ?: false
    }

    fun removeAllTextChangedListener(){
        if(textChangeListener != null){
            for(tw in textChangeListener!!){
                removeTextChangedListener(tw)
            }
//            textChangeListener?.clear()
            textChangeListener= null
        }
    }
}