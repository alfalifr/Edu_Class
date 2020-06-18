package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.ClassSmtAdp
import sidev.kuliah.tekber.edu_class.model.SemesterClass
import sidev.kuliah.tekber.edu_class.presenter.ClassPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.adapter.RvAdp
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.android.siframe.tool.util.`fun`.startSingleFragAct_config
import sidev.lib.android.siframe.tool.util.`fun`.toast

class ClassFrag : RvFrag<ClassSmtAdp>(){
    override val fragTitle: String
        get() = "Kelas"

    override fun initPresenter(): Presenter? {
        return ClassPres(this)
    }

    override fun _initView(layoutView: View) {
        loge("_initView() MULAI")
        onRefreshListener= {
            downloadData(Const.REQ_GET_CLASS_SEMESTER)
            showPb(true)
        }
        downloadData(Const.REQ_GET_CLASS_SEMESTER)
        showPb(true)
        rvAdp.onClassItemClickListener= { smt, cls, pos ->
            loge("rvAdp.onClassItemClickListener cls.id= ${cls.id}")
            startSingleFragAct_config<ModuleListFrag>(
                Const.DATA_CLASS_IN_SMT to cls
            )
        }
        rvAdp.rv= layoutView.findViewById(R.id.rv)!!
        loge("_initView() AKHIR")
//        R.layout._sif_page_rv
    }

    override fun initRvAdp(): ClassSmtAdp {
        return ClassSmtAdp(context!!, null)
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
            Const.REQ_GET_CLASS_SEMESTER -> {
                if(resCode == Const.RES_OK){
                    val data= data!![Const.DATA_CLASS_IN_SMT] as ArrayList<SemesterClass>
                    rvAdp.dataList= data
                }
            }
        }
    }
    override fun onPresenterFail(reqCode: String, resCode: Int, msg: String?, e: Exception?) {
        showPb(false)
        toast("Terjadi kesalahan saat download data kelas.\nHarap refresh.")
    }
}