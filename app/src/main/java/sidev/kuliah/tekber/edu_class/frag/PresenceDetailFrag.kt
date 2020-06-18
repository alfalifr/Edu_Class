package sidev.kuliah.tekber.edu_class.frag

import android.content.Intent
import android.view.View
import kotlinx.android.synthetic.main.comp_item_presence_class.view.*
import kotlinx.android.synthetic.main.page_presence_detail.view.*
//import kotlinx.android.synthetic.main.page_presence_detail.view.rl_main_container
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.PresenceAdp
import sidev.kuliah.tekber.edu_class.adp.PresenceScheduleAdp
import sidev.kuliah.tekber.edu_class.dialog.EnterPresenceCodeDialog
import sidev.kuliah.tekber.edu_class.dialog.PresenceNewsDialog
import sidev.kuliah.tekber.edu_class.model.PresenceClass
import sidev.kuliah.tekber.edu_class.model.Presence_
import sidev.kuliah.tekber.edu_class.presenter.PresencePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util.`fun`.firstObj
import sidev.lib.android.siframe.tool.util.`fun`.getExtra
import sidev.lib.android.siframe.tool.util.`fun`.toObjList
import sidev.lib.android.siframe.tool.util.`fun`.toast
import sidev.lib.universal.`fun`.asNotNull


class PresenceDetailFrag : SimpleAbsFrag(){
    override val layoutId: Int
        get() = R.layout.page_presence_detail


    lateinit var classScheduleAdp: PresenceScheduleAdp
    lateinit var presenceAdp: PresenceAdp
    lateinit var dialogEnterCode: EnterPresenceCodeDialog
    lateinit var dialogNews: PresenceNewsDialog
    var presenceBeingChanged: Presence_?= null
    var newsBeingWritten: String?= null

    lateinit var presenceCls: PresenceClass
/*
    <19 Juni 2020> => e: org.jetbrains.kotlin.codegen.CompilationException: Back-end (JVM) Internal error: wrong bytecode generated
        : Couldn't transform method node:
    var presenceCls: PresenceClass?= null
        set(v){
            field= v
            if(v != null){
                layoutView.comp_header_class
                classScheduleAdp.dataList= v.scheduleList.toObjList()
                layoutView.tv_teacher.text= v.clazz.firstObj()!!.teacher
            }
        }
 */


    override fun _initDataFromIntent(intent: Intent) {
        super._initDataFromIntent(intent)
        presenceCls= intent.getExtra<PresenceClass>(Const.DATA_PRESENCE_CLASS)!!
    }

    override fun initPresenter(): Presenter? {
        return PresencePres(this)
    }

    override fun _initView(layoutView: View) {
        actSimple.asNotNull { act: SimpleAbsBarContentNavAct ->
            act.setActBarTitle("Presensi")
        }

        classScheduleAdp= PresenceScheduleAdp(context!!, presenceCls!!.scheduleList.toObjList())
        classScheduleAdp.rv= layoutView.comp_header_class.rv_schedule
        updateHeaderInfo(presenceCls)

        presenceAdp= PresenceAdp(context!!, null)
        presenceAdp.onStatusChangeListener= { presence, status, pos ->
            presenceBeingChanged= presence
            when(status){
                Const.STATUS_PRESENCE_PRESENT -> {
                    dialogEnterCode.show()
                }
                Const.STATUS_PRESENCE_IJIN -> {
                    //!!!Belm ada dialog untuk ijin.
                }
            }
        }
        presenceAdp.onNewsClickListener= { presence, news, pos ->
            presenceBeingChanged= presence
            dialogNews.showWithNews(news)
        }
        presenceAdp.rv= layoutView.rv_presence

        dialogEnterCode= EnterPresenceCodeDialog(context!!)
        dialogNews= PresenceNewsDialog(context!!)

        dialogEnterCode.onEnterCodeEndListener= { dialog, code, isCancelled ->
            if(isCancelled) dialog.cancel()
            else{
                savePresenceCode(code)
                dialog.showPb()
            }
        }
        dialogNews.onNewsEndListener= { dialog, news, isCancelled ->
            if(isCancelled) dialog.cancel()
            else{
                saveNews(news)
                dialog.showPb()
            }
        }

        layoutView.srl.setOnRefreshListener { downloadData() }
        downloadData()
    }


    fun showPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<View>(R.id.rl_main_container)?.visibility= if(!show) View.VISIBLE
        else View.GONE
    }

    fun downloadData(){
        downloadData(Const.REQ_GET_PRESENCE_DETAIL, Const.DATA_CLASS_ID to presenceCls!!.id)
        showPb()
    }

    fun savePresenceCode(code: String){
        sendRequest(Const.REQ_SEND_PRESENCE_CODE, Const.DATA_PRESENCE_CODE to code)
    }
    fun saveNews(news: String){
        newsBeingWritten= news
        sendRequest(Const.REQ_SEND_PRESENCE_NEWS, Const.DATA_PRESENCE_NEWS to news)
    }

    fun updateHeaderInfo(presenceCls: PresenceClass){
        this.presenceCls= presenceCls
        layoutView.comp_header_class
        classScheduleAdp.dataList= presenceCls.scheduleList.toObjList()
        layoutView.tv_teacher.text= presenceCls.clazz.firstObj()!!.teacher

        layoutView.tv_present_count.text= presenceCls.presentCount.toString()
        layoutView.tv_ijin_count.text= presenceCls.ijinCount.toString()
        layoutView.tv_alpha_count.text= presenceCls.alphaCount.toString()
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_GET_PRESENCE_DETAIL -> {
                if(resCode == Const.RES_OK){
                    val data= data!![Const.DATA_PRESENCE] as ArrayList<Presence_>
                    presenceAdp.dataList= data
                }
            }
            Const.REQ_SEND_PRESENCE_CODE -> {
                if(resCode == Const.RES_OK){
                    dialogEnterCode.showPb(false)
                    presenceBeingChanged?.status= Const.STATUS_PRESENCE_PRESENT
                    presenceAdp.notifyDataSetChanged_()
                    toast("Absensi berhasil")
                }
            }
            Const.REQ_SEND_PRESENCE_NEWS -> {
                if(resCode == Const.RES_OK){
                    dialogNews.showPb(false)
                    presenceBeingChanged?.news= newsBeingWritten
                    toast("Berita acara berhasil disimpan")
                }
            }
        }
    }
}
