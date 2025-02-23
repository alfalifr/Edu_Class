package sidev.kuliah.tekber.edu_class.act

import android.view.View
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.frag.ClassFrag
import sidev.kuliah.tekber.edu_class.frag.HomeFrag
import sidev.kuliah.tekber.edu_class.frag.NotifListFrag
import sidev.kuliah.tekber.edu_class.frag.PresenceClassListFrag
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct_ViewPager
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag

class StudentMainAct : SimpleAbsBarContentNavAct_ViewPager<SimpleAbsFrag>() {
    override var isVpTitleFragBased: Boolean= true
    override var isVpBackOnBackPressed: Boolean= false

    override var menuId: Int? = R.menu.main_student
    override val isNavBarVisible: Boolean
        get() = true
    override var vpFragList: Array<SimpleAbsFrag> =
        arrayOf(
            HomeFrag(),
            ClassFrag(),
            NotifListFrag(),
            PresenceClassListFrag()
        )
    override var vpFragListStartMark: Array<Int> =
        arrayOf(0)


    override fun _initView(layoutView: View) {}

    override fun _initActBar(actBarView: View) {
        isActBarViewFromFragment= true
    }
    override fun _initNavBar(navBarView: com.google.android.material.bottomnavigation.BottomNavigationView) {
        setupVpWithNavBar(navBarView)
    }
}
