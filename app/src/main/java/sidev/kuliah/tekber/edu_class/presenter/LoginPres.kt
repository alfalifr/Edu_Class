package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class._cob.dumm_profile
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.`fun`.search
import sidev.lib.universal.tool.util.ThreadUtil

class LoginPres(callback: PresenterCallback) : Presenter(callback){
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
            Const.REQ_LOGIN -> {
                val uname= data!![Const.DATA_UNAME] as String
                val pswd= data!![Const.DATA_PSWD] as String
                login(uname, pswd)
            }
        }
    }

    fun login(uname: String, pswd: String){
        val res= Const.RES_OK
        when(res){
            Const.RES_OK -> {}
            Const.RES_NOT_OK -> {}
        }
        ThreadUtil.delayRun(2000){
            dumm_profile.search{ prof -> prof.uname == uname}
                .notNull { prof ->
                    postSucc(Const.RES_OK, null)
                }.isNull {
                    postSucc(Const.RES_NOT_OK, null)
                }
        }
    }
}