package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_presence_class_schedule.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Presence_
import sidev.kuliah.tekber.edu_class.model.ScheduleModel
import sidev.lib.android.siframe.adapter.RvAdp

class PresenceScheduleAdp(c: Context, data: ArrayList<ScheduleModel>?)
    : RvAdp<ScheduleModel, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_presence_class_schedule

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: ScheduleModel) {
        vh.itemView.tv_day.text= data.day
        vh.itemView.tv_time.text= data.duration.toString()
        vh.itemView.tv_place.text= data.place
        vh.itemView.setOnClickListener(null)
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}