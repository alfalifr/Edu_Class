package sidev.kuliah.tekber.edu_class.act

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.comp_nav_modul_item_container.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.PageNavAdp
import sidev.kuliah.tekber.edu_class.frag.ContentFrag
import sidev.kuliah.tekber.edu_class.model.Module
import sidev.kuliah.tekber.edu_class.model.Page
import sidev.kuliah.tekber.edu_class.presenter.PageContentPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.customizable.view.ModVp
import sidev.lib.android.siframe.intfc.lifecycle.sidebase.DrawerActBase
import sidev.lib.android.siframe.lifecycle.activity.DrawerBarContentNavAct_ViewPager
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.setBgColor
import sidev.lib.android.siframe.tool.util._ViewUtil.setColor
import sidev.lib.android.siframe.tool.util.`fun`.getExtra
import sidev.lib.android.siframe.tool.util.`fun`.getRootView
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.android.siframe.tool.util.`fun`.toast
import sidev.lib.universal.`fun`.asNotNull
import sidev.lib.universal.`fun`.notNull

class ContentAct : DrawerBarContentNavAct_ViewPager<ContentFrag>(){
    override val startDrawerLayoutId: Int
        get() = R.layout.comp_nav_modul_item_container
    override val endDrawerLayoutId: Int
        get() = _Config.INT_EMPTY
/*
    override val bottomLayoutId: Int
        get() = R.layout.comp_nav_forth_back
 */

    override var vpFragList: Array<ContentFrag> = arrayOf()
    override var vpFragListStartMark: Array<Int> = arrayOf()
    override var isVpBackOnBackPressed: Boolean= false
    /*
    override var bottomContainer: View?= null
    override var middleContainer: View?= null
    override var topContainer: View?= null
 */

    lateinit var module: Module
    lateinit var selectedPageId: String

    lateinit var sideNavAdp: PageNavAdp


    override fun _initNavBar(navBarView: BottomNavigationView) {}
    override fun _initEndDrawerView(endDrawerView: View) {}
//    override fun _initMiddleView(middleView: View) {}
//    override fun _initTopView(topView: View) {}

    override fun _initDataFromIntent(intent: Intent) {
        super._initDataFromIntent(intent)
        module= intent.getExtra(Const.DATA_MODULE)!!
    }

    override fun initPresenter(): Presenter? {
        loge("initPresenter()")
        return PageContentPres(this)
    }

    override fun _initActBar(actBarView: View) {
        setActBarTitle(module.name)
        actBarView.findViewById<ImageView>(_Config.ID_IV_BACK).notNull { iv ->
            iv.setImageResource(R.drawable.ic_hamburger)
            setColor(iv, R.color.white)
            iv.rotation= 0f
            iv.setOnClickListener { slideDrawer(DrawerActBase.Type.DRAWER_START) }
        }
    }

/*
    override fun _initBottomView(bottomView: View) {
        bottomView.ll_back_container.setOnClickListener { pageBackward() }
        bottomView.ll_forth_container.setOnClickListener { pageForth() }
        loge("_initBottomView()")
    }
 */

    override fun _initStartDrawerView(startDrawerView: View) {
        setBgColor(startDrawerView, R.color.white)
        startDrawerView.ll_reload_container.setOnClickListener { downloadPageList() }

        sideNavAdp= PageNavAdp(this, null)
        sideNavAdp.setOnItemClickListener { v, pos, data ->
            selectedPageId= data.id
            vp.currentItem= pos
            sideNavAdp.selectedPageInd= pos
//            downloadContent(pageId)
        }

        _ViewUtil.Comp.getTv?.invoke(startDrawerView).notNull { tv -> tv.text= module.name }
        _ViewUtil.Comp.getRv?.invoke(startDrawerView).notNull { rv -> sideNavAdp.rv= rv }
        downloadPageList()
    }

    override fun _initView(layoutView: View) {
        vp.asNotNull { modVp: ModVp ->
            modVp.isTouchable= false
            modVp.isTouchInterceptable= false
            loge("vp is ModableVp")
        }
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}

            override fun onPageSelected(position: Int) {
                sideNavAdp.selectedPageInd= position
            }
        })
    }

/*
    fun initSideNavView(){
//        __initTopMiddleBottomView(actSimple!!.layoutView)
/*
        actSimple.asNotNull { act: DrawerActBase ->
            context!!.inflate(R.layout.comp_nav_modul_item_container, act.startDrawerContainer).notNull { v ->
                _ViewUtil.Comp.getTv?.invoke(v).notNull { tv -> tv.text= module.name }
                _ViewUtil.Comp.getRv?.invoke(v).notNull { rv -> sideNavAdp.rv= rv }

                act.setDrawerView(DrawerActBase.Type.DRAWER_START, v)
            }
        }
 */
    }
 */


    fun downloadPageList(){
        loge("downloadPageList() module.id= ${module.id}")
        downloadData(Const.REQ_GET_PAGE, Const.DATA_MODULE_ID to module.id)
        showSideNavPb()
        showVpPb()
        showSideNavNoData(false)
    }


    fun showVpPb(show: Boolean= true){
        getRootView().findViewById<ProgressBar>(R.id.pb).notNull { pb ->
            pb.visibility= if(show) View.VISIBLE
            else View.GONE
            vp.visibility= if(!show) View.VISIBLE
            else View.GONE
        }
    }
    fun showSideNavPb(show: Boolean= true){
        loge("showSideNavPb() startDrawerContainer.findViewById<View>(R.id.pb)= ${startDrawerContainer.findViewById<View>(R.id.pb)}")
        startDrawerContainer.findViewById<View>(R.id.pb)?.visibility= if(show) View.VISIBLE
        else View.GONE
        startDrawerContainer.findViewById<View>(R.id.rv)?.visibility= if(!show) View.VISIBLE
        else View.GONE
//        startDrawerContainer.findViewById<SwipeRefreshLayout>(R.id.srl)?.isRefreshing= show
    }
    fun showSideNavNoData(show: Boolean= true, msg: String?= null){
        startDrawerContainer.findViewById<View>(R.id.ll_reload_container).notNull { v ->
            v.visibility= if(show) View.VISIBLE
            else View.GONE
            v.findViewById<TextView>(R.id.tv_no_data)?.text= msg
        }
        startDrawerContainer.findViewById<View>(R.id.rv)?.visibility= if(!show) View.VISIBLE
        else View.GONE
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showVpPb(false)
        showSideNavPb(false)
        when(reqCode){
            Const.REQ_GET_PAGE -> {
                loge("onPresenterSucc() resCode == Const.RES_NO_PAGE => ${resCode == Const.RES_NO_PAGE}")
                if(resCode == Const.RES_NO_PAGE)
                    showSideNavNoData(msg= Const.MSG_NO_DATA)
                else {
                    val pageList= data!![Const.DATA_PAGE] as ArrayList<Page>
                    if(pageList.isEmpty())
                        showSideNavNoData(msg= Const.MSG_NO_DATA)
                    else {
//                        val pageListSize= pageList.size
                        showSideNavNoData(false)
                        val contentFragList= Array(pageList.size){
                            val frag= ContentFrag()
//                            frag.pageId= pageList[it].id
                            frag.pageData= pageList[it]
                            frag.isNextPageQuiz=
                                try{ pageList[it+1].isQuiz }
                                catch (e: IndexOutOfBoundsException){ false }
                            frag.isNextPageQuizStillValid=
                                try{ pageList[it+1].isQuizStillValid }
                                catch (e: IndexOutOfBoundsException){ true }
                            frag
                        }
                        sideNavAdp.dataList= pageList
                        setFragList(contentFragList)
                        loge("onPresenterSucc() Const.REQ_GET_PAGE pageList.isNotEmpty()")
                    }
                }
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showVpPb(false)
        showSideNavPb(false)
        when(reqCode){
            Const.REQ_GET_PAGE -> {
                toast("Terjadi kesalahan download data page.\nHarap ulangi.")
                showSideNavNoData(msg= Const.MSG_LOAD_ERROR_PAGE)
            }
            else -> toast("Terjadi kesalahan.\nHarap ulangi halaman ini.")
        }
    }
}