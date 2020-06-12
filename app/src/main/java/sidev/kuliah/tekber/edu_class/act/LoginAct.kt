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
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.initPasswordField
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setEtHint
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setTvTitleTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.showPassword
import sidev.lib.universal.`fun`.notNull

class LoginAct : SimpleAbsBarContentNavAct(){
    override val contentLayoutId: Int
        get() = R.layout.page_login

    var isPswdShown= false

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
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        when(reqCode){
            Const.REQ_LOGIN -> {

            }
        }
    }
}