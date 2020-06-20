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
import sidev.kuliah.tekber.edu_class.dialog.PresenceIjinDialog
import sidev.kuliah.tekber.edu_class.dialog.PresenceNewsDialog
import sidev.kuliah.tekber.edu_class.model.PresenceClass
import sidev.kuliah.tekber.edu_class.model.Presence_
import sidev.kuliah.tekber.edu_class.presenter.PresencePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.kuliah.tekber.edu_class.util.Util
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util.`fun`.*
import sidev.lib.universal.`fun`.asNotNull
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull
import java.io.File
import java.net.URI


class PresenceDetailFrag : SimpleAbsFrag(){
    override val layoutId: Int
        get() = R.layout.page_presence_detail


    lateinit var classScheduleAdp: PresenceScheduleAdp
    lateinit var presenceAdp: PresenceAdp
    lateinit var dialogEnterCode: EnterPresenceCodeDialog
    lateinit var dialogNews: PresenceNewsDialog
    lateinit var dialogIjin: PresenceIjinDialog
    var presenceBeingChanged: Presence_?= null
    var newsBeingWritten: String?= null
    var reasonBeingWritten: String?= null
    var suratIjinFile: File?= null

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
                    if(presence.attachment != null && presence.attachment!!.isNotEmpty()){
                        presence.attachment!!.first().asNotNull { file: File ->
                            dialogIjin.setFileName(file.name)
                        }
                    }
                    dialogIjin.showWithReason(presence.news)
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
        dialogIjin= PresenceIjinDialog(context!!)

        dialogEnterCode.onEnterCodeEndListener= { dialog, code, isCancelled ->
            if(isCancelled){
                dialog.clearField()
                dialog.cancel()
            } else{
                dialog.showPb()
                savePresenceCode(presenceBeingChanged!!.id, code)
            }
        }
        dialogNews.onNewsEndListener= { dialog, news, isCancelled ->
            if(isCancelled) {
                dialog.clearField()
                dialog.cancel()
            } else{
                dialog.showPb()
                saveNews(presenceBeingChanged!!.id, news)
            }
        }
        dialogIjin.onIjinEndListener= { dialog, reason, isCancelled ->
            if(isCancelled) {
                dialog.clearField()
                dialog.cancel()
            } else{
                dialog.showPb()
                saveIjin(presenceBeingChanged!!.id, reason)
            }
        }
        dialogIjin.onAttachmentClickListener= { dialog ->
            Util.pickFile(this, "Masukan surat ijin", Const.REQ_PICK_FILE)
        }

        layoutView.srl.setOnRefreshListener { downloadData() }
        downloadData()
    }


    fun showPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<View>(R.id.rl_main_container)?.visibility= if(!show) View.VISIBLE
        else View.GONE
        layoutView.srl.isRefreshing= show
    }

    fun downloadData(){
        downloadData(Const.REQ_GET_PRESENCE_DETAIL, Const.DATA_CLASS_ID to presenceCls!!.id)
        showPb()
    }

    fun savePresenceCode(presenceId: String, code: String){
        if(code.isNotBlank()){
            sendRequest(Const.REQ_SEND_PRESENCE_CODE,
                Const.DATA_PRESENCE_ID to presenceId,
                Const.DATA_PRESENCE_CODE to code
            )
        } else {
            dialogEnterCode.showPb(false)
            toast("Kode belum dimasukkan")
        }
    }
    fun saveNews(presenceId: String, news: String){
        if(news.isNotBlank()){
            newsBeingWritten= news
            sendRequest(Const.REQ_SEND_PRESENCE_NEWS,
                Const.DATA_PRESENCE_ID to presenceId,
                Const.DATA_PRESENCE_NEWS to news
            )
        } else {
            dialogNews.showPb(false)
            toast("Berita acara belum dimasukkan")
        }
    }
    fun saveIjin(presenceId: String, reason: String){
        if(reason.isNotBlank()){
            reasonBeingWritten= reason
            suratIjinFile.notNull { file ->
                sendRequest(Const.REQ_SEND_PRESENCE_IJIN,
                    Const.DATA_PRESENCE_ID to presenceId,
                    Const.DATA_PRESENCE_IJIN_REASON to reason,
                    Const.DATA_PRESENCE_IJIN_FILE to file
                )
            }.isNull {
                dialogIjin.showPb(false)
                toast("Masukan surat ijin terlebih dahulu")
            }
        } else{
            dialogIjin.showPb(false)
            toast("Mohon isi keterangan ijin")
        }
    }

    fun updateHeaderInfo(presenceCls: PresenceClass){
        this.presenceCls= presenceCls
        classScheduleAdp.dataList= presenceCls.scheduleList.toObjList()
        layoutView.comp_header_class.tv_class.text= presenceCls.clazz.firstObj()!!.name
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
                    dialogEnterCode.clearField()
                    dialogEnterCode.cancel()
                    presenceBeingChanged?.status= Const.STATUS_PRESENCE_PRESENT
                    presenceAdp.notifyDataSetChanged_()
                    toast("Absensi berhasil")
                } else{
                    toast("Kode yg anda masukan salah")
                }
            }
            Const.REQ_SEND_PRESENCE_NEWS -> {
                if(resCode == Const.RES_OK){
                    dialogNews.showPb(false)
                    presenceBeingChanged?.news= newsBeingWritten
                    toast("Berita acara berhasil disimpan")
                }
            }
            Const.REQ_SEND_PRESENCE_IJIN -> {
                if(resCode == Const.RES_OK){
                    dialogIjin.showPb(false)
                    dialogIjin.clearField()
                    dialogIjin.cancel()
                    presenceBeingChanged?.status= Const.STATUS_PRESENCE_IJIN
                    presenceBeingChanged?.excuseReason= reasonBeingWritten
                    presenceBeingChanged?.attachment.notNull { attList ->
                        attList.clear()
                        attList.add(suratIjinFile!!)
                    }.isNull {
                        val attList= ArrayList<Any>()
                        attList.add(suratIjinFile!!)
                        presenceBeingChanged?.attachment= attList
                    }
                    presenceAdp.notifyDataSetChanged_()
                    toast("Surat ijin berhasil dikirim")
                }
            }
        }
    }

    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showPb(false)
        val str= when(reqCode){
            Const.REQ_GET_PRESENCE_DETAIL -> " saat load data presensi"
            Const.REQ_SEND_PRESENCE_CODE -> " saat mengirim kode presensi"
            Const.REQ_SEND_PRESENCE_NEWS -> " saat menyimpan berita acara"
            Const.REQ_SEND_PRESENCE_IJIN -> " saat mengirim durat ijin"
            else -> ""
        }
        toast("Terjadi kesalahan$str.\nHarap ulangi.")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            Const.REQ_PICK_FILE -> {
                data?.data.notNull { uri ->
                    suratIjinFile= File(uri.path)
                    dialogIjin.setFileName(suratIjinFile!!.name)
                }
            }
        }
    }
}
