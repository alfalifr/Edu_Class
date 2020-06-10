package sidev.kuliah.tekber.edu_class

import android.view.View
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct_ViewPager
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag

class MainActivity : SimpleAbsBarContentNavAct_ViewPager<SimpleAbsFrag>() {
    override var vpFragList: Array<SimpleAbsFrag>
        get() = TODO("Not yet implemented")
        set(value) {}
    override var vpFragListStartMark: Array<Int>
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun _initActBar(actBarView: View) {}

    override fun _initNavBar(navBarView: com.google.android.material.bottomnavigation.BottomNavigationView) {}

    override fun _initView(layoutView: View) {}
}
