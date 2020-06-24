package sidev.kuliah.tekber.edu_class.frag

import android.app.ProgressDialog
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.page_home.*
import kotlinx.android.synthetic.main.page_home.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Profil
import sidev.kuliah.tekber.edu_class.presenter.ProfilePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.kuliah.tekber.edu_class.util.ViewUtil.Comp.enableEd
import sidev.kuliah.tekber.edu_class.util.inflate
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.intfc.lifecycle.LifecycleBase
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.lifecycle.fragment.SimpleActBarFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.enableFillTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setEtTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvDescTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTitleTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.setBgColor
import sidev.lib.android.siframe.tool.util._ViewUtil.setColor
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.android.siframe.tool.util.`fun`.toast
import sidev.lib.universal.`fun`.asNotNull
import sidev.lib.universal.`fun`.notNull

class HomeFrag : SimpleActBarFrag(){
    override val actBarId: Int
        get() = R.layout._sif_comp_action_bar_default
    override val layoutId: Int
        get() = R.layout.page_home

    lateinit var profile: Profil
    override val fragTitle: String
        get() = "Beranda"

    lateinit var loadingDialog: ProgressDialog
    //    lateinit var uname: String //ini nti diambil dari login

    override fun initPresenter(): Presenter? {
        return ProfilePres(this)
    }

    override fun _initActBar(actBarView: View) {
        loge("_initActBar() AWAL")
        setBgColor(actBarView, R.color.colorPrimaryDark)
        setTvTitleTxt(actBarView, fragTitle)
        actBarView.findViewById<ImageView>(R.id.iv_action).notNull { iv ->
            iv.setImageResource(R.drawable.ic_logout)
            setColor(iv, R.color.white)
            iv.setOnClickListener {
                showLoadingDialog(msg = "Logout...")
                sendRequest(Const.REQ_LOGOUT)
            }
        }
        actBarView.findViewById<ImageView>(R.id.iv_back).notNull { iv ->
            actSimple?.registerBackBtnView(iv)
        }
        loge("_initActBar()")
    }

    override fun _initView(layoutView: View) {
        loadingDialog= ProgressDialog(context!!)
        loadingDialog.setCancelable(false)

        setTvTitleTxt(comp_name, "Nama")
        setTvTitleTxt(comp_nrp, "NRP")
        setTvTitleTxt(comp_email, "Email")

        enableEd(comp_name, false)
        enableEd(comp_nrp, false)
        enableEd(comp_email, false)

        layoutView.srl.setOnRefreshListener { downloadData() }
        downloadData()
    }

    fun showPb(show: Boolean= true){
        layoutView.rl_pb_container.visibility= if(show) View.VISIBLE
            else View.GONE
        layoutView.ll_fill_container.visibility= if(!show) View.VISIBLE
            else View.GONE
        layoutView.srl.isRefreshing= show
    }

    fun showLoadingDialog(show: Boolean= true, msg: String?= null){
        if(show){
            loadingDialog.setMessage(msg!!)
            loadingDialog.show()
        } else
            loadingDialog.dismiss()
    }

    fun downloadData(){
        downloadData(Const.REQ_GET_PROFILE)
        showPb(true)
    }

    fun updateProfile(profil: Profil){
        profile= profil

        setEtTxt(comp_name, profil.name)
        setEtTxt(comp_nrp, profil.nrp)
        setEtTxt(comp_email, profil.email)
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        showLoadingDialog(false)
        when(reqCode){
            Const.REQ_GET_PROFILE -> {
                if(resCode == Const.RES_OK)
                    try{ updateProfile(data!![Const.DATA_PROFILE] as Profil) } catch (e: Exception){}
            }
            Const.REQ_LOGOUT -> {
                if(resCode == Const.RES_OK){
                    toast("Berhasil logout")
                    actSimple!!.finish()
                }
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showPb(false)
        showLoadingDialog(false)
        toast("Terjadi kesalahan saat download data profil.\nHarap refresh.")
    }
}