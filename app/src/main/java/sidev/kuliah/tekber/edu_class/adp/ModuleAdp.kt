package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_modul.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Module
import sidev.kuliah.tekber.edu_class.util.ViewUtil
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvDescTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTitleTxt
import sidev.lib.universal.`fun`.notNull

class ModuleAdp(c: Context, data: ArrayList<Module>?) : RvAdp<Module, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_modul

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Module) {
        setTvTitleTxt(vh.itemView, data.name)
        setTvDescTxt(vh.itemView, data.desc)
        _ViewUtil.Comp.getIv?.invoke(vh.itemView)
            .notNull { iv -> ViewUtil.setImg(iv, data.img) }
        vh.itemView.tv_time.text= "Minggu ${data.duration}"

        vh.itemView.cv_main_container.setOnClickListener {
            onItemClickListener?.onClickItem(it, pos, data)
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}