package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class._cob.dumm_smt_class
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback
import sidev.lib.universal.`fun`.toArrayList
import sidev.lib.universal.tool.util.ThreadUtil

class ClassPres(c: PresenterCallback) : Presenter(c){
    override fun checkDataIntegrity(
        reqCode: String,
        direction: Direction,
        data: Map<String, Any>?
    ): Boolean {
        return when(reqCode){
            else -> true
        }
    }

    override fun processRequest(reqCode: String, data: Map<String, Any>?) {
        when(reqCode){
            Const.REQ_GET_CLASS_SEMESTER -> getClassInSemester()
        }
    }

    fun getClassInSemester(){
        ThreadUtil.delayRun(3000){
            postSucc(Const.RES_OK, mapOf(Const.DATA_CLASS_IN_SMT to dumm_smt_class.toArrayList()))
        }
    }
}