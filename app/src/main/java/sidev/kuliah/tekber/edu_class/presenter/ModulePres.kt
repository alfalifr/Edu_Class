package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class._cob.dumm_class
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.`fun`.search
import sidev.lib.universal.`fun`.toArrayList
import sidev.lib.universal.tool.util.ThreadUtil

class ModulePres(c: PresenterCallback) : Presenter(c){
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
            Const.REQ_GET_MODULE -> {
                val idClass= data!![Const.DATA_CLASS_IN_SMT_ID] as String
                loge("processRequest() Const.REQ_GET_MODULE idClass= $idClass")
                getModule(idClass)
            }
        }
    }

    fun getModule(idClass: String){
        ThreadUtil.delayRun(3000){
            loge("getModule() idClass= $idClass")
            dumm_class.search { clazz -> clazz.id == idClass }.notNull { cls ->
                val moduleList= cls.moduleList!!.toObj!!.toArrayList()

                loge("getModule() moduleList.size= ${moduleList.size}")
                postSucc(Const.RES_OK, mapOf(Const.DATA_MODULE to moduleList))
            }.isNull {
                loge("getModule() SEARCH NULL")
            }
        }
    }
}