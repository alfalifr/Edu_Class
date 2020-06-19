package sidev.kuliah.tekber.edu_class.adp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_notif.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Notif
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTvTitle
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvDescTxt
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.asNotNull
import sidev.lib.universal.`fun`.notNull

class NotifAdp(c: Context, data: ArrayList<Notif>?) : RvAdp<Notif, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_notif

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Notif) {
        ctx.asNotNull { act: Activity ->
            val screenWidth= _ViewUtil.getScreenWidth(act)
            loge("bindVH() screenWidth= $screenWidth")
            vh.itemView.layoutParams.width= screenWidth //ViewGroup.LayoutParams.MATCH_PARENT
            vh.itemView.asNotNull { vg: ViewGroup ->
                loge("bindVH() screenWidth= $screenWidth vh.itemView is vg")
                vg.getChildAt(0).layoutParams.width= screenWidth //ViewGroup.LayoutParams.MATCH_PARENT
                vg.findViewById<View>(_Config.ID_VG_CONTENT_CONTAINER).notNull { v ->
                    loge("bindVH() screenWidth= $screenWidth itemView")
                    v.layoutParams.width= screenWidth
                }
            }
        }
        getTvTitle?.invoke(vh.itemView).notNull { tv ->
            if(data.title != null && data.title!!.isNotBlank()){
                tv.visibility= View.VISIBLE
                tv.text= data.title
            } else{
                tv.visibility= View.GONE
            }
        }
        setTvDescTxt(vh.itemView, data.desc)
        val time= data.timestamp.split(" ").last()
        vh.itemView.tv_time.text= time
    }

    override fun setupLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(ctx)
    }
}