package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback

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

    }
}