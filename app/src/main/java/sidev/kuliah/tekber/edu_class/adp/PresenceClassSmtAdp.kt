package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_class_presence_container.view.*
import kotlinx.android.synthetic.main.comp_item_class_presence_header.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.PresenceClass
import sidev.kuliah.tekber.edu_class.model.PresenceClassSmt
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util.`fun`.toObjList
import java.lang.IndexOutOfBoundsException

class PresenceClassSmtAdp(c: Context, data: ArrayList<PresenceClassSmt>?)
    : RvAdp<PresenceClassSmt, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_class_presence_container

    val presenceClassAdp= ArrayList<PresenceClassAdp>()
    var onClassItemClickListener: ((PresenceClass, pos: Int) -> Unit)?= null

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: PresenceClassSmt) {
        vh.itemView.tv_time.text= "Semester ${data.smt}"
        var isRvVisible= true
        vh.itemView.comp_header.setOnClickListener {
            isRvVisible= !isRvVisible
            vh.itemView.rv.visibility= if(isRvVisible) View.VISIBLE
                else View.GONE
            vh.itemView.comp_header.iv_drop_down.rotation= if(isRvVisible) 0f
            else 180f
        }

        try{ presenceClassAdp[pos] }
        catch (e: IndexOutOfBoundsException){
            val adp= PresenceClassAdp(ctx, data.presenceClassList?.toObjList()!!)
            adp.rv= vh.itemView.rv
            adp.setOnItemClickListener { v, pos, data ->
                onClassItemClickListener?.invoke(data, pos)
            }
            presenceClassAdp.add(adp)
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}