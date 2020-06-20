package sidev.kuliah.tekber.edu_class.frag

import android.graphics.Typeface
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.comp_nav_forth_back.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.ContentAdp
import sidev.kuliah.tekber.edu_class.dialog.ContentWarningDialog
import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.kuliah.tekber.edu_class.model.Page
import sidev.kuliah.tekber.edu_class.presenter.PageContentPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.intfc.lifecycle.sidebase.TopMiddleBottomBase
import sidev.lib.android.siframe.intfc.lifecycle.sidebase.ViewPagerActBase
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.android.siframe.tool.util.`fun`.toast
import sidev.lib.universal.`fun`.*

class ContentFrag : RvFrag<ContentAdp>(), TopMiddleBottomBase{
    //    lateinit var moduleId: String
    override var bottomContainer: View?= null
    override var middleContainer: View?= null
    override var topContainer: View?= null

    override val bottomLayoutId: Int
        get() = R.layout.comp_nav_forth_back

//    lateinit var pageId: String
    lateinit var pageData: Page
    var isNextPageQuiz= false
    var isNextPageQuizStillValid= true
    var dialogWarning: ContentWarningDialog?= null


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
            bottomView.ll_back_container.setOnClickListener {
                if(pageData.isQuiz && pageData.isQuizStillValid)
                    toast("Anda masih mengerjakan kuis. Harap kumpulkan terlebih dahulu.")
                else
                    act.pageBackward()
            }
            bottomView.ll_forth_container.setOnClickListener {
                if(pageData.isQuiz && pageData.isQuizStillValid){
                    showDialogConfirm("Anda akan mengumpulkan kuis untuk dinilai. Lanjut kumpulkan?",
                        "Kumpulkan")
                    { dialog, view ->
                        dialog.showPb()
                        sendQuizAnswer()
                    }
                } else{
                    if(isNextPageQuiz && isNextPageQuizStillValid){
                        showDialogConfirm("Anda akan memulai kuis. Lanjut mengerjakan kuis?",
                        "Lanjut")
                        { dialog, view ->
                            act.pageForth()
                            dialog.cancel()
                        }
                    } else {
                        act.pageForth()
                    }
                }
            }
            if(isNextPageQuiz){
                bottomView.tv_forth.text= "Lanjut Kuis"
                val tf= bottomView.tv_forth.typeface
                bottomView.tv_forth.setTypeface(tf, Typeface.BOLD)
            } else if(pageData.isQuiz){
                bottomView.tv_forth.text= "Kumpulkan"
                val tf= bottomView.tv_forth.typeface
                bottomView.tv_forth.setTypeface(tf, Typeface.BOLD)
            }
        }
    }

    override fun _initView(layoutView: View) {
        __initTopMiddleBottomView(layoutView)
        onRefreshListener= { downloadContent(pageData.id) }
        downloadContent(pageData.id)
//        downloadPageList()
    }

    fun downloadContent(pageId: String){
        downloadData(Const.REQ_GET_CONTENT, Const.DATA_PAGE_ID to pageId)
        showPb()
    }

    fun sendQuizAnswer(){
        val answerList= rvAdp.getAnswerList()
        downloadData(Const.REQ_SEND_QUESTION_ANSWER,
            Const.DATA_QUESTION_ANSWER to answerList,
            Const.DATA_PAGE_ID to pageData.id
        )
    }


    fun showPb(show: Boolean= true){
        layoutView.findViewById<View>(R.id.pb)?.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<View>(R.id.rv)?.visibility= if(!show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<SwipeRefreshLayout>(R.id.srl)?.isRefreshing= show
    }

    fun showDialogConfirm(mainMsg: String, rightBtnMsg: String,
                          rightBtnTodo: ((ContentWarningDialog, isCancelled: Boolean) -> Unit)?= null){
        val dialog= dialogWarning.orDefault(ContentWarningDialog(context!!))
        dialog.setMainMsg(mainMsg)
        dialog.setRightBtnTxt(rightBtnMsg)
        dialog.onButtonClickListener= { btn, isCancelled ->
            rightBtnTodo?.invoke(dialog, isCancelled)
        }
        dialog.show()
        if(dialogWarning == null)
            dialogWarning= dialog
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_GET_CONTENT -> {
                if(resCode == Const.RES_OK){
                    val data= data!![Const.DATA_CONTENT] as ArrayList<Content>
                    rvAdp.dataList= data
                }
            }
            Const.REQ_SEND_QUESTION_ANSWER -> {
                if(resCode == Const.RES_OK){
                    dialogWarning?.showPb(false)
                    dialogWarning?.cancel()
                    pageData.isQuizStillValid= false
                    toast("Kuis berhasil dikumpulkan")
                    actSimple.asNotNull { act: ViewPagerActBase<ContentFrag> ->
                        act.pageForth()
                        val ind= act.getFragPos(this)
                        loge("act is ViewPagerActBase<ContentFrag> ind= $ind")
                        try{ act.vpFragList[ind-1].isNextPageQuizStillValid= false }
                        catch (e: Exception){}
                    }
                }
            }
        }
    }
}