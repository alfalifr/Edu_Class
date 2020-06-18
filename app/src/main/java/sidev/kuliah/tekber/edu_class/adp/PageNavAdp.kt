package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_nav_modul_item.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Page
import sidev.lib.android.siframe.adapter.RvAdp

class PageNavAdp(c: Context, data: ArrayList<Page>?): RvAdp<Page, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_nav_modul_item

    var selectedPageInd= 0
        set(v){
            field= v
            notifyDataSetChanged_()
        }

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: Page) {
        val v= vh.itemView
        var indicTopVis= View.VISIBLE
        var indicBottomVis= View.VISIBLE
        if(pos == 0)
            indicTopVis= View.GONE
        if(pos == itemCount-1)
            indicBottomVis= View.GONE

        v.iv_indication_top.visibility= indicTopVis
        v.iv_indication_bottom.visibility= indicBottomVis

        v.tv.text= data.name
        v.iv_indication.setImageResource(
            if(pos != selectedPageInd) R.drawable.ic_circle_border
            else R.drawable.ic_circle_border_filled
        )
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)
}