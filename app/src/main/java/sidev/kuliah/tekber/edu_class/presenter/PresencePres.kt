package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class._cob.*
import sidev.kuliah.tekber.edu_class.model.PresenceClass
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback
import sidev.lib.android.siframe.tool.util._StorageUtil
import sidev.lib.android.siframe.tool.util.`fun`.firstId
import sidev.lib.android.siframe.tool.util.`fun`.toObjList
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.`fun`.search
import sidev.lib.universal.`fun`.toArrayList
import sidev.lib.universal.tool.util.ThreadUtil

class PresencePres(c: PresenterCallback) : Presenter(c){
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
            Const.REQ_GET_PRESENCE_CLASS_IN_SMT -> getPresenceClassInSmt()
            Const.REQ_GET_PRESENCE_DETAIL -> {
                val classId= data!![Const.DATA_CLASS_ID] as String
                getPresenceDetail(classId)
            }
            Const.REQ_SEND_PRESENCE_CODE -> {
                val presenceCode= data!![Const.DATA_PRESENCE_CODE] as String
                val uname= _StorageUtil.SharedPref.getSharedPref(ctx!!, Const.DATA_UNAME)!!
                savePresenceCode(uname, presenceCode)
            }
            Const.REQ_SEND_PRESENCE_NEWS -> {
                val presenceNews= data!![Const.DATA_PRESENCE_NEWS] as String
                val uname= _StorageUtil.SharedPref.getSharedPref(ctx!!, Const.DATA_UNAME)!!
                savePresenceNews(uname, presenceNews)
            }
//            Const.REQ_GET_PRESENCE_TIME_NOW -> getTimeNow()
//            Const.REQ_GET_PRESENCE_UPCOMING_CLASS -> getUpcomingClass()
        }
    }

    fun getPresenceClassInSmt(){
        ThreadUtil.delayRun(3000){
            val presenceClass= dumm_presence_class_smt.toArrayList()
            val timeNow= dumm_time_now
            val upcomingClass= dumm_upcoming_class
            postSucc(Const.RES_OK,
                mapOf(
                    Const.DATA_PRESENCE_CLASS_SMT to presenceClass,
                    Const.DATA_PRESENCE_TIME_NOW to timeNow,
                    Const.DATA_PRESENCE_UPCOMING_CLASS to upcomingClass
                )
            )
        }
    }
    fun getTimeNow(){}
    fun getUpcomingClass(){}

    fun getPresenceDetail(classId: String){
        ThreadUtil.delayRun(3000){
            dumm_presence_class.search { presenceClass ->
                presenceClass.clazz.firstId() == classId
            }.notNull { presenceClass ->
                val presenceList= presenceClass.presenceList.toObjList()!!
                postSucc(Const.RES_OK, mapOf(Const.DATA_PRESENCE to presenceList))
            }
        }
    }

    fun savePresenceCode(uname: String, presenceCode: String){
        ThreadUtil.delayRun(3000){
            postSucc(Const.RES_OK, null)
        }
    }

    fun savePresenceNews(uname: String, news: String){
        ThreadUtil.delayRun(3000){
            postSucc(Const.RES_OK, null)
        }
    }
}