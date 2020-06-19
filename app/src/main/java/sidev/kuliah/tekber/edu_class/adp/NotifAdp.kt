package sidev.kuliah.tekber.edu_class.adp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_header_splitter.view.*
import kotlinx.android.synthetic.main.comp_item_notif.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Notif
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.adapter.RvMultiViewAdp
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTvTitle
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvDescTxt
import sidev.lib.android.siframe.tool.util.`fun`.inflate
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.asNotNull
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.`fun`.notNullTo
import sidev.lib.universal.tool.util.TimeUtil


class NotifAdp(c: Context, data: ArrayList<Notif>?) : RvAdp<Notif, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_notif

    /**
     * Format $Const.FORMAT_TIMESTAMP "YYYY-MM-dd HH:mm:ss"
     */
    var lastReadTimestamp: String= ""
    var timestampNow= ""
        set(v){
            field= v
            dateNow= v.split(" ").first()
        }
    private var dateNow= ""
    private var isSplitterShown= false
    private var splitterView: View?= null

    init{
        timestampNow= TimeUtil.timestamp(pattern = Const.FORMAT_TIMESTAMP)
        loge("dateNow= $dateNow")
    }

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Notif) {
        if(!isSplitterShown && lastReadTimestamp.isNotBlank()){
            val lastReadTimestampDiff= TimeUtil.getTimeDiff(timestampNow, data.timestamp, Const.FORMAT_TIMESTAMP)
            val hasBeenRead= lastReadTimestampDiff <= 0
            loge("bindVH() lastReadTimestampDiff= $lastReadTimestampDiff timestampNow= $timestampNow data.timestamp= ${data.timestamp}")
            if(!hasBeenRead){
                vh.itemView.rl_splitter_header.visibility= View.VISIBLE
                vh.itemView.rl_splitter_header.addView(
                    splitterView
                        ?: ctx.inflate(R.layout.comp_item_header_splitter, vh.itemView.rl_splitter_header).notNullTo { splitter ->
                            splitter.tv.text= "Belum Dibaca"
                            splitterView= splitter
                            splitter
                        }
                )
                isSplitterShown= true
            }
        } else {
            vh.itemView.rl_splitter_header.visibility= View.GONE
            vh.itemView.rl_splitter_header.removeAllViews()
        }

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
        val timeList= data.timestamp.split(" ")
        var day= ""
        if(timeList.first() != dateNow)
            day= TimeUtil.convertFormatTo(timeList.first(), Const.FORMAT_DATE, "EEE") +","

        val time= data.timestamp.split(" ").last()
        vh.itemView.tv_time.text= "$day $time"
    }

    override fun setupLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(ctx)
    }
}