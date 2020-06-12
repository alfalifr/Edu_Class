package sidev.kuliah.tekber.edu_class.act

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.frag.ClassFrag
import sidev.kuliah.tekber.edu_class.frag.HomeFrag
import sidev.lib.android.siframe.lifecycle.activity.SimpleAbsBarContentNavAct_ViewPager
import sidev.lib.android.siframe.lifecycle.fragment.SimpleAbsFrag

class StudentMainAct : SimpleAbsBarContentNavAct_ViewPager<SimpleAbsFrag>(){
    override var isVpTitleFragBased: Boolean= true
    override var vpFragList: Array<SimpleAbsFrag> =
        arrayOf(
            HomeFrag(),
            ClassFrag()
        )
    override var vpFragListStartMark: Array<Int> =
        arrayOf(0)

    override fun _initActBar(actBarView: View) {}

    override fun _initNavBar(navBarView: BottomNavigationView) {
        navBarView.inflateMenu(R.menu.main_student)
        setupVpWithNavBar(navBarView)
    }

    override fun _initView(layoutView: View) {
        TODO("Not yet implemented")
    }
}