package sidev.kuliah.tekber.edu_class.frag

import android.content.Intent
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.comp_nav_forth_back.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.ContentAdp
import sidev.kuliah.tekber.edu_class.adp.PageNavAdp
import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.kuliah.tekber.edu_class.model.Module
import sidev.kuliah.tekber.edu_class.model.Page
import sidev.kuliah.tekber.edu_class.presenter.PageContentPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.intfc.lifecycle.sidebase.DrawerActBase
import sidev.lib.android.siframe.intfc.lifecycle.sidebase.TopMiddleBottomBase
import sidev.lib.android.siframe.intfc.lifecycle.sidebase.ViewPagerActBase
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getRv
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTv
import sidev.lib.android.siframe.tool.util.`fun`.getExtra
import sidev.lib.android.siframe.tool.util.`fun`.inflate
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.asNotNull
import sidev.lib.universal.`fun`.notNull

class ContentFrag : RvFrag<ContentAdp>(), TopMiddleBottomBase{
    //    lateinit var moduleId: String
    override var bottomContainer: View?= null
    override var middleContainer: View?= null
    override var topContainer: View?= null

    override val bottomLayoutId: Int
        get() = R.layout.comp_nav_forth_back

    lateinit var pageId: String


    override fun _initMiddleView(middleView: View) {}
    override fun _initTopView(topView: View) {}
    override fun ___initSideBase() {}


    override fun initPresenter(): Presenter? {
        return PageContentPres(this)
    }

    override fun initRvAdp(): ContentAdp {
        return ContentAdp(context!!, null)
    }

    override fun _initBottomView(bottomView: View) {
        loge("_initBottomView() MULAI")
        actSimple.asNotNull { act: ViewPagerActBase<*> ->
            loge("_initBottomView() act: ViewPagerActBase<*>")
            bottomView.ll_back_container.setOnClickListener { act.pageBackward() }
            bottomView.ll_forth_container.setOnClickListener { act.pageForth() }
        }
    }

    override fun _initView(layoutView: View) {
        __initTopMiddleBottomView(layoutView)
        onRefreshListener= {
            downloadContent(pageId)
        }
        downloadContent(pageId)
//        downloadPageList()
    }

    fun downloadContent(pageId: String){
        downloadData(Const.REQ_GET_CONTENT, Const.DATA_PAGE_ID to pageId)
        showPb()
    }

    fun showPb(show: Boolean= true){
        layoutView.findViewById<View>(R.id.pb)?.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<View>(R.id.rv)?.visibility= if(!show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<SwipeRefreshLayout>(R.id.srl)?.isRefreshing= show
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_GET_CONTENT -> {
                val data= data!![Const.DATA_CONTENT] as ArrayList<Content>
                rvAdp.dataList= data
            }
        }
    }
}