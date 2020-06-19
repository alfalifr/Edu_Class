package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_presence.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Presence_
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ResUtil

class PresenceAdp(c: Context, data: ArrayList<Presence_>?)
    : RvAdp<Presence_, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_presence

    var onStatusChangeListener: ((Presence_, status: Int, pos: Int) -> Unit)?= null
    var onNewsClickListener: ((Presence_, news: String?, pos: Int) -> Unit)?= null

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Presence_) {
        val v= vh.itemView
        v.tv_time.text= data.date
        var col= R.color.black

        v.tv_status.text= when(data.status){
            Const.STATUS_PRESENCE_PRESENT -> {
                col= R.color.ijoRumput
                "HADIR"
            }
            Const.STATUS_PRESENCE_IJIN -> {
                col= R.color.biru
                "IJIN"
            }
            Const.STATUS_PRESENCE_ALPHA -> {
                col= R.color.merah
                "ALPHA"
            }
            else -> "-"
        }
        v.tv_status.setTextColor(_ResUtil.getColor(ctx, col))

        val canChangeStatus= data.status == Const.STATUS_PRESENCE_NEW

        v.cv_present_container.visibility= if(canChangeStatus) View.VISIBLE
        else View.GONE
        v.cv_excuse_container.visibility= if(canChangeStatus) View.VISIBLE
        else View.GONE
        v.cv_news_container.visibility= if(!canChangeStatus) View.VISIBLE
        else View.GONE

        v.cv_present_container.setOnClickListener {
            onStatusChangeListener?.invoke(data, Const.STATUS_PRESENCE_PRESENT, pos)
        }
        v.cv_excuse_container.setOnClickListener {
            onStatusChangeListener?.invoke(data, Const.STATUS_PRESENCE_IJIN, pos)
        }
        v.cv_news_container.setOnClickListener {
            onNewsClickListener?.invoke(data, data.news, pos)
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}