package sidev.kuliah.tekber.edu_class.dialog

import android.content.Context
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.dialog_presence_enter_code.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setBtnHollow
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setBtnSolid
import sidev.lib.android.siframe.view.tool.dialog.DialogAbsView
import sidev.lib.universal.`fun`.notNull

class EnterPresenceCodeDialog(c: Context) : DialogAbsView<EnterPresenceCodeDialog>(c){
    override val layoutId: Int
        get() = R.layout.dialog_presence_enter_code

    var onEnterCodeEndListener: ((dialog: EnterPresenceCodeDialog, code: String, isCancelled: Boolean) -> Unit)?= null

    init{
        layoutView.btn_right.setOnClickListener {
            onEnterCodeEndListener.notNull { l ->
                val code= layoutView.et.text.toString()
                l(this, code, false)
            }
        }
        layoutView.btn_left.setOnClickListener {
            onEnterCodeEndListener.notNull { l ->
                val code= layoutView.et.text.toString()
                l(this, code, true)
            }
        }

        (layoutView.btn_left as Button).text= "Batal"
        (layoutView.btn_right as Button).text= "Kirim"

        setBtnHollow(layoutView.btn_left as Button)
        setBtnSolid(layoutView.btn_right as Button)

        setTitle("Masukan kode presensi")
    }

    fun showPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.rl_btn_container.visibility= if(!show) View.VISIBLE
        else View.GONE
    }
}