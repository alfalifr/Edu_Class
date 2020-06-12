package sidev.kuliah.tekber.edu_class.app

import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.act.SimpleSingleFragActImpl
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.lifecycle.app.BaseApp
import sidev.lib.android.siframe.tool.`var`._SIF_Config
import sidev.lib.android.siframe.tool.util._ViewUtil

class App : BaseApp(){
    init {
        _SIF_Config.CLASS_SINGLE_FRAG_ACT= SimpleSingleFragActImpl::class.java
        _Config.DRAW_PSWD_HIDDEN
        _ViewUtil.Comp.getEt= { v ->
            v.findViewById(R.id.ed_fill)
        }

        _ViewUtil.Comp.getTv= { v ->
            v.findViewById(R.id.tv_title)
                ?: v.findViewById(R.id.tv)
        }
        _ViewUtil.Comp.getTvNote= { v ->
            v.findViewById(R.id.tv_note)
        }

        _ViewUtil.Comp.getIv= { v ->
            v.findViewById(R.id.iv_action)
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