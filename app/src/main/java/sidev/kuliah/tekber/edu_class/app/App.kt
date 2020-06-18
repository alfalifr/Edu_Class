package sidev.kuliah.tekber.edu_class.app

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.act.SimpleSingleFragActImpl
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.customizable._init._Constant
import sidev.lib.android.siframe.lifecycle.app.BaseApp
import sidev.lib.android.siframe.tool.`var`._SIF_Config
import sidev.lib.android.siframe.tool.util._ViewUtil

class App : BaseApp(){
    init {
        _SIF_Config.CLASS_SINGLE_FRAG_ACT= SimpleSingleFragActImpl::class.java
        _Config.TEMPLATE_VIEW_ACT_BAR_TYPE= _Constant.TEMPLATE_VIEW_TYPE_ACT_BAR_SQUARE
        _Config.DRAW_PSWD_HIDDEN

        _ViewUtil.Comp.getEt= { v ->
            if(v is EditText) v
            else v.findViewById(R.id.ed_fill)
        }

        _ViewUtil.Comp.getRv= { v ->
            if(v is RecyclerView) v
            else v.findViewById(R.id.rv)
                ?: v.findViewById(R.id.rv_presence)
                ?: v.findViewById(R.id.rv_schedule)
        }
        _ViewUtil.Comp.getTv= { v ->
            if(v is TextView) v
            else v.findViewById(R.id.tv_title)
                ?: v.findViewById(R.id.tv)
                ?: v.findViewById(R.id.tv_header)
        }
        _ViewUtil.Comp.getTvDesc= { v ->
            if(v is TextView) v
            else v.findViewById(R.id.tv_desc)
        }
        _ViewUtil.Comp.getTvTitle= { v ->
            if(v is TextView) v
            else v.findViewById(R.id.tv_title)
        }
        _ViewUtil.Comp.getTvNote= { v ->
            if(v is TextView) v
            else v.findViewById(R.id.tv_note)
        }

        _ViewUtil.Comp.getIv= { v ->
            if(v is ImageView) v
            else v.findViewById(R.id.iv_action)
                ?: v.findViewById(R.id.iv_show_password)
                ?: v.findViewById(R.id.iv_indication)
                ?: v.findViewById(R.id.iv_indication_top)
                ?: v.findViewById(R.id.iv_indication_bottom)
        }
        _ViewUtil.Comp.getIvPswdIndication= { v ->
            v.findViewById(R.id.iv_show_password)
        }
        _ViewUtil.Comp.getIvAction= { v ->
            v.findViewById(R.id.iv_action)
        }
    }
}