package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import kotlinx.android.synthetic.main.page_home.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.model.Profil
import sidev.kuliah.tekber.edu_class.presenter.ProfilePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.kuliah.tekber.edu_class.util.ViewUtil.Comp.enableEd
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.enableFillTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setEtTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvDescTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTitleTxt

class HomeFrag : SimpleAbsFrag(){
    override val layoutId: Int
        get() = R.layout.page_home

    lateinit var profile: Profil
    lateinit var uname: String //ini nti diambil dari login

    override fun initPresenter(): Presenter? {
        return ProfilePres(this)
    }

    override fun _initView(layoutView: View) {
        setTvTitleTxt(comp_name, "Nama")
        setTvTitleTxt(comp_nrp, "NRP")
        setTvTitleTxt(comp_email, "Email")

        enableFillTxt(comp_name, false)
        enableFillTxt(comp_nrp, false)
        enableFillTxt(comp_email, false)

        downloadData(Const.REQ_GET_PROFILE,
            Const.DATA_UNAME to uname
        )
    }

    fun updateProfile(profil: Profil){
        profile= profil

        setEtTxt(comp_name, profil.name)
        setEtTxt(comp_nrp, profil.nrp)
        setEtTxt(comp_email, profil.email)
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        when(reqCode){
            Const.REQ_GET_PROFILE -> {
                updateProfile(data!![Const.DATA_PROFILE] as Profil)
            }
        }
    }
}