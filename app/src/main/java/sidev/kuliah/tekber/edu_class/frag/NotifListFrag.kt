package sidev.kuliah.tekber.edu_class.frag

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.adp.NotifAdp
import sidev.kuliah.tekber.edu_class.model.Notif
import sidev.kuliah.tekber.edu_class.presenter.NotifPres
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.lib.android.siframe.lifecycle.fragment.RvFrag
import sidev.lib.android.siframe.presenter.Presenter
import sidev.lib.android.siframe.tool.util._ViewUtil.setBgColor
import sidev.lib.android.siframe.tool.util.`fun`.getRootView
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.notNull

class NotifListFrag : RvFrag<NotifAdp>(){
    override val fragTitle: String
        get() = "Notifikasi"

    override fun initPresenter(): Presenter? {
        return NotifPres(this)
    }

    override fun _initView(layoutView: View) {
        getRootView().notNull { v ->
            setBgColor(v, R.color.abu)
            loge("_initView() getRootView() notNull")
        }
        onRefreshListener= { downloadData() }
        downloadData()
    }

    override fun initRvAdp(): NotifAdp {
        return NotifAdp(context!!, null)
    }

    fun downloadData(){
        downloadData(Const.REQ_GET_NOTIF)
        showPb()
    }

    fun showPb(show: Boolean= true){
        layoutView.findViewById<View>(R.id.rl_pb_container)?.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<View>(R.id.rv)?.visibility= if(!show) View.VISIBLE
        else View.GONE
        layoutView.findViewById<SwipeRefreshLayout>(R.id.srl)?.isRefreshing= show
    }

    override fun onPresenterSucc(reqCode: String, resCode: Int, data: Map<String, Any>?) {
        showPb(false)
        when(reqCode){
            Const.REQ_GET_NOTIF -> {
                if(resCode == Const.RES_OK){
                    val data= data!![Const.DATA_NOTIF] as ArrayList<Notif>
                    rvAdp.dataList= data
                }
            }
        }
    }
}