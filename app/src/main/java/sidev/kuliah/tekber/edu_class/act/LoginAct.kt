package sidev.kuliah.tekber.edu_class.act

import android.view.View
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.page_login.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.presenter.LoginPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util._StorageUtil
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getEtTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTvNote
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.initPasswordField
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setEtHint
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvNoteMode
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvNoteTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTitleTxt
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.android.siframe.tool.util.`fun`.startAct
import sidev.lib.android.siframe.tool.util.`fun`.toast
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull

class LoginAct : SimpleAbsBarContentNavAct(){
    override val contentLayoutId: Int
        get() = R.layout.page_login

    var isPswdShown= false
    var uname= ""
    var pswd= ""

    override fun initPresenter(): Presenter? {
        return LoginPres(this)
    }

    override fun _initActBar(actBarView: View) {
        actBarView.visibility= View.GONE
    }
    override fun _initNavBar(navBarView: BottomNavigationView) {}

    override fun _initView(layoutView: View) {
        setTvTitleTxt(layoutView.comp_uname, "Username")
        setTvTitleTxt(layoutView.comp_pswd, "Password")
        setEtHint(layoutView.comp_uname, "Masukan username")
        setEtHint(layoutView.comp_pswd, "Masukan password")

        initPasswordField(layoutView.comp_pswd)
/*
        showPassword(layoutView.comp_pswd, false)
        _ViewUtil.Comp.getIvPswdIndication?.invoke(layoutView.comp_pswd)
            .notNull { iv ->
                iv.setOnClickListener {
                    isPswdShown= !isPswdShown
                    showPassword(layoutView.comp_pswd, isPswdShown)
                }
            }
 */

        (layoutView.comp_btn_login as Button).text= "Login"
        layoutView.comp_btn_login.setOnClickListener {
            sendReq()
        }
        showPb()
        checkLogin()
    }

    fun checkLogin(){
        _StorageUtil.SharedPref.getSharedPref(this, Const.KEY_PROFILE_ROLE).notNull { role ->
            when(role.toInt()){
                Const.ROLE_STUDENT -> {
                    startAct<StudentMainAct>()
                    finish()
                }
                Const.ROLE_TEACHER -> {
                    startAct<TeacherMainAct>()
                    finish()
                }
            }
        }.isNull { showPb(false) }
    }

    fun showPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
            else View.GONE
        layoutView.comp_btn_login.visibility= if(show) View.GONE
            else View.VISIBLE
    }

    fun initNote(){
        setTvNoteMode(layoutView.comp_uname, _ViewUtil.Comp.MODE_WARNING)
        setTvNoteMode(layoutView.comp_pswd, _ViewUtil.Comp.MODE_WARNING)

        setTvNoteTxt(layoutView.comp_uname, "Masukan username")
        setTvNoteTxt(layoutView.comp_pswd, "Masukan password")

        getTvNote?.invoke(layoutView.comp_uname).notNull { et -> et.visibility= View.GONE }
        getTvNote?.invoke(layoutView.comp_pswd).notNull { et -> et.visibility= View.GONE }
    }

    fun sendReq(){
        uname= getEtTxt(layoutView.comp_uname)!!
        pswd= getEtTxt(layoutView.comp_pswd)!!

        loge("uname= $uname pswd= $pswd")

        val valid= uname.isNotBlank() && pswd.isNotBlank()
        initNote()
        if(!valid){
            if(uname.isBlank())
                getTvNote?.invoke(layoutView.comp_uname).notNull { et -> et.visibility= View.VISIBLE }
            if(pswd.isBlank())
                getTvNote?.invoke(layoutView.comp_pswd).notNull { et -> et.visibility= View.VISIBLE }
        } else{
            sendRequest(Const.REQ_LOGIN,
                Const.DATA_UNAME to uname,
                Const.DATA_PSWD to pswd)
            showPb()
        }
    }


    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_LOGIN -> {
                when(resCode){
                    Const.RES_OK -> {
                        val role= data!![Const.DATA_PROFILE_ROLE] as Int
                        _StorageUtil.SharedPref.setSharedPref(this, Const.KEY_UNAME, uname)
                        _StorageUtil.SharedPref.setSharedPref(this, Const.KEY_PROFILE_ROLE, role.toString())
                        when(role){
                            Const.ROLE_STUDENT -> {
                                startAct<StudentMainAct>()
                                toast("Anda masuk sebagai Pelajar")
                                finish()
                            }
                            Const.ROLE_TEACHER -> {
                                startAct<TeacherMainAct>()
                                toast("Anda masuk sebagai Pengajar")
                                finish()
                            }
                        }
                    }
                    Const.RES_NOT_OK -> {
                        setTvNoteTxt(layoutView.comp_pswd, "Anda salah memasukan username atau password.")
                        getTvNote?.invoke(layoutView.comp_pswd).notNull { tv -> tv.visibility= View.VISIBLE }
                        toast("Username atau password yg Anda masukan salah.")
                    }
                }
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showPb(false)
        toast("Terjadi kesalahan saat login.\nHarap ulangi.")
    }

}