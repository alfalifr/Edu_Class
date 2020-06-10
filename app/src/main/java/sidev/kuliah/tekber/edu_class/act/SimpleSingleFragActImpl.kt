package sidev.kuliah.tekber.edu_class.act

import android.view.View
import androidx.fragment.app.Fragment
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.lifecycle.activity.SingleFragDrawerAct_BarContentNav

class SimpleSingleFragActImpl : SingleFragDrawerAct_BarContentNav(){
    override lateinit var fragment: Fragment
    override val endDrawerLayoutId: Int
        get() = _Config.INT_EMPTY
    override val startDrawerLayoutId: Int
        get() = _Config.INT_EMPTY

    override fun _initActBar(actBarView: View) {}
    override fun _initEndDrawerView(endDrawerView: View) {}
    override fun _initNavBar(navBarView: com.google.android.material.bottomnavigation.BottomNavigationView) {}
    override fun _initStartDrawerView(startDrawerView: View) {}
}