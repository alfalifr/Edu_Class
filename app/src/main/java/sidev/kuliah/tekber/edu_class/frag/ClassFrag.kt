package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.ClassSmtAdp
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter

class ClassFrag : RvFrag<ClassSmtAdp>(){
    override fun initPresenter(): Presenter? {
        return super.initPresenter()
    }

    override fun _initView(layoutView: View) {

    }

    override fun initRvAdp(): ClassSmtAdp {
        return ClassSmtAdp(context!!, null)
    }
}