package sidev.kuliah.tekber.edu_class.util

import android.view.View
import android.widget.ImageView
import sidev.lib.android.siframe.model.PictModel
import sidev.lib.android.siframe.tool.util._ViewUtil
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
            _ViewUtil.Comp.getEt?.invoke(compView)
                .notNull { ed ->
                    ed.isEnabled= enable
                }
        }
    }
}