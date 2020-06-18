package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.comp_item_presence_class.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.PresenceClass
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util.`fun`.firstObj
import sidev.lib.universal.`fun`.toArrayList
import java.lang.IndexOutOfBoundsException

class PresenceClassAdp(c: Context, data: ArrayList<PresenceClass>?)
    : RvAdp<PresenceClass, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_presence_class

    val presenceAdp= ArrayList<PresenceScheduleAdp>()

    var isLecturerVisible: Boolean= false
        set(v){
            field= v
            notifyDataSetChanged_()
        }

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: PresenceClass) {
        vh.itemView.ll_lecturer_container.visibility= if(isLecturerVisible) View.GONE
        else View.GONE
        vh.itemView.tv_class.text= data.clazz.firstObj()!!.name
        vh.itemView.tv_teacher.text= data.clazz.firstObj()!!.teacher

        try{ presenceAdp[pos] }
        catch (e: IndexOutOfBoundsException){
            val adp= PresenceScheduleAdp(ctx, data.scheduleList?.toObj!!.toArrayList())
            presenceAdp.add(adp)
            adp.rv= vh.itemView.rv_schedule
            adp.rv!!.setOnTouchListener { v, event -> false }
/*
            adp.rv!!.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener(){
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    return true
                }
            })
 */
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}