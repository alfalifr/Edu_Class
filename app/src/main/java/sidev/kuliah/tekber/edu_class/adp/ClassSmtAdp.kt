package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_class_container.view.*
import kotlinx.android.synthetic.main.comp_item_class_header.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.ClassModel
import sidev.kuliah.tekber.edu_class.model.SemesterClass
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull
import java.lang.IndexOutOfBoundsException

class ClassSmtAdp(c: Context, data: ArrayList<SemesterClass>?)
    : RvAdp<SemesterClass, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_class_container

    var onClassItemClickListener: ((smt: Int, cls: ClassModel, pos: Int) -> Unit)?= null
    var clazzAdp= ArrayList<ClassAdp>()

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: SemesterClass) {
//        loge("bindVH() AWAL")
        _ViewUtil.Comp.getTv?.invoke(vh.itemView)
            .notNull { tv -> tv.text= "Semester ${data.semester}" }
        try{ clazzAdp[pos] }
        catch (e: IndexOutOfBoundsException){
//            loge("bindVH() CATCH AWAL")
            _ViewUtil.Comp.getRv?.invoke(vh.itemView)
                .notNull { rv ->
//                    loge("bindVH() RV NOT NULL")
                    var isRvVisible= true
                    vh.itemView.comp_header.setOnClickListener {
                        isRvVisible= !isRvVisible
                        rv.visibility= if(isRvVisible) View.VISIBLE
                            else View.GONE
                        vh.itemView.iv_drop_down.rotation= if(isRvVisible) 0f
                            else 180f
                    }
                    rv.visibility= View.VISIBLE
                    vh.itemView.iv_drop_down.rotation= 0f

                    val clzData= ArrayList<ClassModel>()
                    data.clazz!!.toObj!!.forEach { clz ->
                        clzData.add(clz)
                    }
                    val clzAdp= ClassAdp(ctx, clzData)
                    clzAdp.setOnItemClickListener { v, pos, dataCls ->
                        onClassItemClickListener?.invoke(data.semester, dataCls, pos)
                    }
                    clazzAdp.add(clzAdp)
                    clzAdp.rv= rv

                    loge("bindVH() Yes gak null")
                }. isNull { loge("bindVH() Yah NULL...") }
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}