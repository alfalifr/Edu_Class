package sidev.kuliah.tekber.edu_class.app

import sidev.kuliah.tekber.edu_class.act.SimpleSingleFragActImpl
import sidev.lib.android.siframe.lifecycle.app.BaseApp
import sidev.lib.android.siframe.tool.`var`._SIF_Config

class App : BaseApp(){
    init {
        _SIF_Config.CLASS_SINGLE_FRAG_ACT= SimpleSingleFragActImpl::class.java
    }
}