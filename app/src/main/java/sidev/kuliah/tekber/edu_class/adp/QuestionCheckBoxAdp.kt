package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.util.SparseArray
import androidx.core.util.set
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_fill_choice_checkbox.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTvTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTxt
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull

class QuestionCheckBoxAdp(c: Context, data: ArrayList<String>?)
    : RvAdp<String, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_fill_choice_checkbox

    val cbCheckedList= SparseArray<Boolean>()

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: String) {
        val v= vh.itemView
        setTvTxt(v, data)
        cbCheckedList.get(pos).notNull { bool ->
            v.cb.isChecked= bool
        }.isNull {
            cbCheckedList[pos]= false
        }

        v.cb.setOnCheckedChangeListener { buttonView, isChecked ->
            cbCheckedList[pos]= isChecked
        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}