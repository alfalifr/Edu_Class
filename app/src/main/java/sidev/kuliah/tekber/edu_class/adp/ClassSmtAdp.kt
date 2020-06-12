package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_class_container.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.ClassModel
import sidev.kuliah.tekber.edu_class.model.SemesterClass
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.universal.`fun`.notNull
import java.lang.IndexOutOfBoundsException

class ClassSmtAdp(c: Context, data: ArrayList<SemesterClass>?)
    : RvAdp<SemesterClass, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_class_container

    var clazzAdp= ArrayList<ClassAdp>()

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: SemesterClass) {
        _ViewUtil.Comp.getTv?.invoke(vh.itemView)
            .notNull { tv -> tv.text= "Semester ${data.semester}" }
        try{ clazzAdp[pos] }
        catch (e: IndexOutOfBoundsException){
            _ViewUtil.Comp.getRv?.invoke(vh.itemView)
                .notNull { rv ->
                    var isRvVisible= false
                    vh.itemView.comp_header.setOnClickListener {
                        isRvVisible= !isRvVisible
                        rv.visibility= if(isRvVisible) View.VISIBLE
                            else View.GONE
                    }

                    val clzData= ArrayList<ClassModel>()
                    data.clazz!!.toObj!!.forEach { clz ->
                        clzData.add(clz)
                    }
                    val clzAdp= ClassAdp(ctx, clzData)
                    clazzAdp.add(clzAdp)
                    clzAdp.rv= rv
                }
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}