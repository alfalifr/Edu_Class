package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback

class NewsPres(c: PresenterCallback) : Presenter(c){
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
            Const.REQ_GET_NEWS -> getNews()
        }
    }

    fun getNews(){

    }
}