package sidev.kuliah.tekber.edu_class.dialog

import android.content.Context
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.dialog_presence_ijin.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.util.Util
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setBtnHollow
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.setBtnSolid
import sidev.lib.android.siframe.view.tool.dialog.DialogAbsView
import sidev.lib.universal.`fun`.notNull

class PresenceIjinDialog(c: Context) : DialogAbsView<PresenceIjinDialog>(c){
    override val layoutId: Int
        get() = R.layout.dialog_presence_ijin

    var onIjinEndListener: ((dialog: PresenceIjinDialog, reason: String, isCancelled: Boolean) -> Unit)?= null
    var onAttachmentClickListener: ((dialog: PresenceIjinDialog) -> Unit)?= null

    init{
        layoutView.btn_right.setOnClickListener {
            onIjinEndListener.notNull { l ->
                val code= layoutView.et.text.toString()
                l(this, code, false)
            }
        }
        layoutView.btn_left.setOnClickListener {
            onIjinEndListener.notNull { l ->
                val code= layoutView.et.text.toString()
                l(this, code, true)
            }
        }
        layoutView.rl_attachment_container.setOnClickListener {
            onAttachmentClickListener.notNull { l ->
                l(this)
            }
        }

        (layoutView.btn_left as Button).text= "Batal"
        (layoutView.btn_right as Button).text= "Kirim"

        setBtnHollow(layoutView.btn_left as Button)
        setBtnSolid(layoutView.btn_right as Button)

        layoutView.iv_action

        setTitle("Masukan ijin")
    }

    fun showPb(show: Boolean= true){
        layoutView.pb.visibility= if(show) View.VISIBLE
        else View.GONE
        layoutView.rl_btn_container.visibility= if(!show) View.VISIBLE
        else View.GONE
    }

    fun showWithReason(news: String?){
        layoutView.et.setText(news)
        show()
    }

    /**
     * @param name adalah nama file full.
     */
    fun setFileName(name: String){
        val finalName= Util.cutStrLen(name, 30)
        layoutView.tv.text= finalName
    }

    fun clearField(){
        layoutView.et.setText("")
        layoutView.tv.text= "Pilih surat ijin"
    }
}