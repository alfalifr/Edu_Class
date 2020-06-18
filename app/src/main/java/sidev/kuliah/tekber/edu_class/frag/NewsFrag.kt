package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import sidev.kuliah.tekber.edu_class.adp.NewsAdp
import sidev.kuliah.tekber.edu_class.model.NewsModel
import sidev.kuliah.tekber.edu_class.presenter.NewsPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.presenter.Presenter

class NewsFrag : RvFrag<NewsAdp>(){
    override val fragTitle: String
        get() = "Notifikasi"

    override fun initPresenter(): Presenter? {
        return NewsPres(this)
    }

    override fun _initView(layoutView: View) {
        downloadData(Const.REQ_GET_NEWS)
    }

    override fun initRvAdp(): NewsAdp {
        return NewsAdp(context!!, null)
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        when(reqCode){
            Const.REQ_GET_NEWS -> {
                if(resCode == Const.RES_OK){
                    val data= data!![Const.DATA_NEWS] as ArrayList<NewsModel>
                    rvAdp.dataList= data
                }
            }
        }
    }
}