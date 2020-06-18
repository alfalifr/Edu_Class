package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import kotlinx.android.synthetic.main.page_home.*
import kotlinx.android.synthetic.main.page_home.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Profil
import sidev.kuliah.tekber.edu_class.presenter.ProfilePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.kuliah.tekber.edu_class.util.ViewUtil.Comp.enableEd
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
import sidev.lib.android.siframe.tool.util.`fun`.toast
import sidev.lib.universal.`fun`.asNotNull

class HomeFrag : SimpleActBarFrag(){
    override val actBarId: Int
        get() = _Config.INT_EMPTY
    override val layoutId: Int
        get() = R.layout.page_home

    lateinit var profile: Profil
    override val fragTitle: String
        get() = "Beranda"
    //    lateinit var uname: String //ini nti diambil dari login

    override fun initPresenter(): Presenter? {
        return ProfilePres(this)
    }

    override fun _initActBar(actBarView: View) {}

    override fun _initView(layoutView: View) {
        setTvTitleTxt(comp_name, "Nama")
        setTvTitleTxt(comp_nrp, "NRP")
        setTvTitleTxt(comp_email, "Email")

        enableEd(comp_name, false)
        enableEd(comp_nrp, false)
        enableEd(comp_email, false)

        downloadData(Const.REQ_GET_PROFILE)
        showPb(true)
    }

    fun showPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
            else View.GONE
        layoutView.rl_info_container.visibility= if(show) View.GONE
            else View.VISIBLE
    }

    fun updateProfile(profil: Profil){
        profile= profil

        setEtTxt(comp_name, profil.name)
        setEtTxt(comp_nrp, profil.nrp)
        setEtTxt(comp_email, profil.email)
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_GET_PROFILE -> {
                if(resCode == Const.RES_OK)
                    updateProfile(data!![Const.DATA_PROFILE] as Profil)
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showPb(false)
        toast("Terjadi kesalahan saat download data profil.\nHarap refresh.")
    }
}