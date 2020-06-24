package sidev.kuliah.tekber.edu_class.presenter

import org.json.JSONArray
import sidev.kuliah.tekber.edu_class._cob.dumm_profile
import sidev.kuliah.tekber.edu_class.model.Profil
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback
import sidev.lib.android.siframe.tool.util._NetworkUtil
import sidev.lib.android.siframe.tool.util._StorageUtil
import sidev.lib.universal.`fun`.iff
import sidev.lib.universal.`fun`.isNull
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.`fun`.search
import sidev.lib.universal.tool.util.ThreadUtil

class ProfilePres(callback: PresenterCallback) : Presenter(callback){
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
            Const.REQ_GET_PROFILE -> {
                val uname= _StorageUtil.SharedPref.getSharedPref(ctx!!, Const.KEY_UNAME)!!
                getProfile(uname)
            }
        }
    }

    fun getProfile(uname: String){
        _NetworkUtil.Loopj.get(ctx!!, Const.URL_PROFILE, null){ code, response ->
            if(response != null){
                val jsonArray= JSONArray(response)
                var isProfileFound= false
                for(i in 0 until jsonArray.length()){
                    val jObj= jsonArray.getJSONObject(i)
                    val jUname= jObj.getString("uname")
                    if(uname == jUname){
                        val id= jObj.getString("id")
                        val role= jObj.getString("role")
                        val name= jObj.getString("name")
                        val nrp= jObj.getString("nrp")
                        val email= jObj.getString("email")

                        val profile= Profil(id, role.toInt(), uname, name, nrp, email)
                        val data= mapOf(Const.DATA_PROFILE to profile)
                        postSucc(Const.RES_OK, data)
                        isProfileFound= true
                    }
                }
                if(!isProfileFound)
                    postSucc(Const.RES_NOT_OK, null)
            }
        }
/*
        ThreadUtil.delayRun(2000){
            dumm_profile.toList().search { prof -> prof.uname == uname }
                .notNull { prof ->
                    val data= mapOf(Const.DATA_PROFILE to prof)
                    postSucc(Const.RES_OK, data)
                }.isNull {
                    postSucc(Const.RES_NOT_OK, null)
                }
        }
 */
    }
}