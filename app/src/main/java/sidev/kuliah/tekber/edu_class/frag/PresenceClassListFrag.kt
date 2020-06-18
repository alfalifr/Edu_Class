package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import kotlinx.android.synthetic.main.page_presence_main.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.PresenceClassSmtAdp
import sidev.kuliah.tekber.edu_class.model.ClassModel
import sidev.kuliah.tekber.edu_class.model.PresenceClassSmt
import sidev.kuliah.tekber.edu_class.model.WeekTime
import sidev.kuliah.tekber.edu_class.presenter.PresencePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.lifecycle.fragment.SimpleActBarFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util.`fun`.startSingleFragAct_config
import sidev.lib.android.siframe.tool.util.`fun`.toast

class PresenceClassListFrag : SimpleActBarFrag(){
    override val fragTitle: String
        get() = "Presensi"

    override val actBarId: Int
        get() = _Config.INT_EMPTY
    override val layoutId: Int
        get() = R.layout.page_presence_main

    lateinit var presenceClassAdp: PresenceClassSmtAdp
    var timeNow: WeekTime?= null
        set(v){
            field= v
            if(v != null){
                layoutView.tv_time_now.text= v.date
                layoutView.tv_week_now.text= "Week ${v.week}"
            }
        }
    var upcomingClass: ClassModel?= null
        set(v){
            field= v
            if(v != null){
                layoutView.tv_upcoming_class.text= v.name
            }
        }


    override fun initPresenter(): Presenter? {
        return PresencePres(this)
    }

    override fun _initActBar(actBarView: View) {}

    override fun _initView(layoutView: View) {
        presenceClassAdp= PresenceClassSmtAdp(context!!, null)
        presenceClassAdp.rv= layoutView.rv
        presenceClassAdp.onClassItemClickListener= { presenceClass, pos ->
            startSingleFragAct_config<PresenceDetailFrag>(Const.DATA_PRESENCE_CLASS to presenceClass)
        }

        layoutView.srl.setOnRefreshListener {
            downloadData()
        }
        downloadData()
    }


    fun showRvPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.rv.visibility= if(!show) View.VISIBLE
        else View.GONE
    }
    fun showHeaderPb(show: Boolean= true){
        layoutView.pb_header.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.ll_header_container.visibility= if(!show) View.VISIBLE
        else View.INVISIBLE
    }

    fun downloadData(){
        downloadData(Const.REQ_GET_PRESENCE_CLASS_IN_SMT)
        showRvPb()
        showHeaderPb()
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        layoutView.srl.isRefreshing= false
        when(reqCode){
            Const.REQ_GET_PRESENCE_CLASS_IN_SMT -> {
                if(resCode == Const.RES_OK){
                    val presenceClass= data!![Const.DATA_PRESENCE_CLASS_SMT] as ArrayList<PresenceClassSmt>
                    timeNow= data[Const.DATA_PRESENCE_TIME_NOW] as WeekTime
                    upcomingClass= data[Const.DATA_PRESENCE_UPCOMING_CLASS] as ClassModel

                    presenceClassAdp.dataList= presenceClass
                    showRvPb(false)
                    showHeaderPb(false)
                }
            }
            Const.REQ_GET_PRESENCE_TIME_NOW -> {
                val data= data!![Const.DATA_PRESENCE_TIME_NOW] as WeekTime
                timeNow= data
                showHeaderPb(false)
            }
            Const.REQ_GET_PRESENCE_UPCOMING_CLASS -> {
                val data= data!![Const.DATA_PRESENCE_UPCOMING_CLASS] as ClassModel
                upcomingClass= data
                showHeaderPb(false)
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        layoutView.srl.isRefreshing= false
        when(reqCode){
            Const.REQ_GET_PRESENCE_CLASS_IN_SMT -> {
                showRvPb(false)
                showHeaderPb(false)
                toast("Terjadi kesalahan saat load data presensi kelas.\nHarap ulangi.")
            }
            Const.REQ_GET_PRESENCE_TIME_NOW -> {
                showHeaderPb(false)
                toast("Terjadi kesalahan saat load data week kini.\nHarap ulangi.")
            }
            Const.REQ_GET_PRESENCE_UPCOMING_CLASS -> {
                showHeaderPb(false)
                toast("Terjadi kesalahan saat load data kelas yg akan datang.\nHarap ulangi.")
            }
            else -> toast("Terjadi kesalahan.\nHarap ulangi halaman ini.")
        }
    }
}