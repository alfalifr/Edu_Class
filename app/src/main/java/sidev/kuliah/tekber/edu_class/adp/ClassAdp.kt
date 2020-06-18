package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.ClassModel
import sidev.kuliah.tekber.edu_class.util.ViewUtil
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.universal.`fun`.notNull

class ClassAdp(c: Context, data: ArrayList<ClassModel>?) : RvAdp<ClassModel, GridLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_class

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: ClassModel) {
        _ViewUtil.Comp.getTv?.invoke(vh.itemView)
            .notNull { tv -> tv.text= "${data.name} (${data.subname})" }
        _ViewUtil.Comp.getIv?.invoke(vh.itemView)
            .notNull { iv -> ViewUtil.setImg(iv, data.img) }

        vh.itemView.setOnClickListener {
            onItemClickListener?.onClickItem(it, pos, data)
        }
    }

    override fun setupLayoutManager(): GridLayoutManager {
        return GridLayoutManager(ctx, 2)
    }
}