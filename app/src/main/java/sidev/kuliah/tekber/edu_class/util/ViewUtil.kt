package sidev.kuliah.tekber.edu_class.util

import android.view.View
import android.widget.ImageView
import sidev.kuliah.tekber.edu_class.R
import sidev.lib.android.siframe.model.PictModel
import sidev.lib.android.siframe.tool.util._ResUtil
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.enableFillTxt
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getEt
import sidev.lib.universal.`fun`.notNull
import sidev.lib.universal.tool.util.FileUtil

object ViewUtil {
    fun setImg(iv: ImageView, img: PictModel?){
        if(img == null) return
        if(img.bm != null)
            iv.setImageBitmap(img.bm)
        else if(img.dir != null)
            _ViewUtil.loadImageToImageView(iv, img.dir)
    }

    object Comp{
        fun enableEd(compView: View, enable: Boolean = true){
            enableFillTxt(compView, enable)
            getEt?.invoke(compView).notNull { et ->
                et.setTextColor(_ResUtil.getColor(et.context, R.color.black))
                if(enable)
                    et.setBackgroundResource(R.drawable.shape_cursor)
                else
                    et.background= null
            }
        }
    }
}