package sidev.kuliah.tekber.edu_class.presenter

import sidev.kuliah.tekber.edu_class._cob.dumm_module
import sidev.kuliah.tekber.edu_class._cob.dumm_page
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.presenter.PresenterCallback
import sidev.lib.android.siframe.tool.util.`fun`.toObjList
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.`fun`.search
import sidev.lib.universal.tool.util.ThreadUtil

class PageContentPres(c: PresenterCallback) : Presenter(c){
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
            Const.REQ_GET_PAGE -> {
                val moduleId= data!![Const.DATA_MODULE_ID] as String
                getPage(moduleId)
            }
            Const.REQ_GET_CONTENT -> {
                val pageId= data!![Const.DATA_PAGE_ID] as String
                getContent(pageId)
            }
        }
    }

    fun getPage(moduleId: String){
        ThreadUtil.delayRun(3000){
            dumm_module.search { module -> module.id == moduleId }.notNull { module ->
                val page= module.pageList.toObjList()!!
                postSucc(Const.RES_OK, mapOf(Const.DATA_PAGE to page))
            }
        }
    }

    fun getContent(pageId: String){
        ThreadUtil.delayRun(3000){
            dumm_page.search { page -> page.id == pageId }.notNull { page ->
                val content= page.contentList.toObjList()!!
                postSucc(Const.RES_OK, mapOf(Const.DATA_CONTENT to content))
            }
        }
    }
}