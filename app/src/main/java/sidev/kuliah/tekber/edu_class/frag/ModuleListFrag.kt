package sidev.kuliah.tekber.edu_class.frag

import android.content.Intent
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.act.ContentAct
import sidev.kuliah.tekber.edu_class.adp.ModuleAdp
import sidev.kuliah.tekber.edu_class.model.ClassModel
import sidev.kuliah.tekber.edu_class.model.Module
import sidev.kuliah.tekber.edu_class.presenter.ModulePres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util.`fun`.*
import sidev.lib.universal.`fun`.asNotNull

class ModuleListFrag : RvFrag<ModuleAdp>(){
    lateinit var cls: ClassModel

    override fun _initDataFromIntent(intent: Intent) {
        super._initDataFromIntent(intent)
        cls= intent.getExtra(Const.DATA_CLASS_IN_SMT)!! // ?: clsId
        loge("_initDataFromIntent clsId= $cls")
    }

    override fun initPresenter(): Presenter? {
        return ModulePres(this)
    }

    override fun initRvAdp(): ModuleAdp {
        return ModuleAdp(context!!, null)
    }

    override fun _initView(layoutView: View) {
        actSimple.asNotNull { act: SimpleAbsBarContentNavAct ->
            act.setActBarTitle(cls.name)
        }
        onRefreshListener= {
            downloadData(Const.REQ_GET_MODULE, Const.DATA_CLASS_IN_SMT_ID to cls.id)
            showPb(true)
        }
        downloadData(Const.REQ_GET_MODULE, Const.DATA_CLASS_IN_SMT_ID to cls.id)
        showPb(true)
        rvAdp.setOnItemClickListener { v, pos, data ->
            startAct<ContentAct>(Const.DATA_MODULE to data)
        }
        rvAdp.rv= layoutView.findViewById(R.id.rv)
    }

    fun showPb(show: Boolean= true){
        layoutView.findViewById<View>(R.id.pb)?.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<View>(R.id.rv)?.visibility= if(!show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<SwipeRefreshLayout>(R.id.srl)?.isRefreshing= show
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_GET_MODULE -> {
                val data= data!![Const.DATA_MODULE] as ArrayList<Module>
                rvAdp.dataList= data

                loge("onPresenterSucc() data berhasil data.size= ${data.size}")
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showPb(false)
        toast("Terjadi kesalahan saat download data modul.\nHarap refresh.")
    }
}