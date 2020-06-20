package sidev.kuliah.tekber.edu_class.util

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import sidev.lib.android.siframe.tool.util.`fun`.loge

object Util {
    fun pickFile(act: Any, title: String, reqCode: Int){
        val i= Intent()
        i.type= "*/*"
        i.action= Intent.ACTION_GET_CONTENT

        val iNew= Intent.createChooser(i, title)
        when(act){
            is Activity -> act.startActivityForResult(iNew, reqCode)
            is Fragment -> act.startActivityForResult(iNew, reqCode)
            else -> loge("pickFile() gagal!!!")
        }
    }

    fun cutStrLen(strIn: String, maxLen: Int): String{
        if(strIn.length <= maxLen) return strIn

        val strMid= " ... "

        val strInMidIndex= strIn.length /2
        val strLenDiff= strIn.length -maxLen

        val lenOfEachSideIsCut= (strLenDiff + strMid.length) /2
        val lenOfEachSide= strInMidIndex -lenOfEachSideIsCut

        val strPrefix= strIn.substring(0, lenOfEachSide)
        val strSuffix= strIn.substring(strIn.lastIndex -lenOfEachSide)

        return strPrefix +strMid +strSuffix
    }
}