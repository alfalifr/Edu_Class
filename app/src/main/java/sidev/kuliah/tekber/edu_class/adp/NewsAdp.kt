package sidev.kuliah.tekber.edu_class.adp

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_news.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.NewsModel
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTvTitle
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvDescTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTitleTxt
import sidev.lib.universal.`fun`.notNull

class NewsAdp(c: Context, data: ArrayList<NewsModel>?) : RvAdp<NewsModel, LinearLayoutManager>(c, data){
    override val itemLayoutId: Int
        get() = R.layout.comp_item_news

    override fun bindVH(vh: SimpleViewHolder, pos: Int, data: NewsModel) {
        getTvTitle?.invoke(vh.itemView).notNull { tv ->
            if(data.title.isNotBlank()){
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