package sidev.kuliah.tekber.edu_class.adp

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.core.util.set
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.comp_item_fill_choice_radio.view.*
import kotlinx.android.synthetic.main.comp_question.view.*
import kotlinx.android.synthetic.main.comp_read.view.*
import kotlinx.android.synthetic.main.comp_video.view.*
import sidev.kuliah.tekber.edu_class.R
import sidev.kuliah.tekber.edu_class.intfc.Content
import sidev.kuliah.tekber.edu_class.model.ContentQuestion
import sidev.kuliah.tekber.edu_class.model.ContentRead
import sidev.kuliah.tekber.edu_class.model.ContentVideo
import sidev.kuliah.tekber.edu_class.util.Const
import sidev.kuliah.tekber.edu_class.util.ViewUtil.Comp.enableEd
import sidev.kuliah.tekber.edu_class.util.getSelectedInd
import sidev.lib.android.siframe.adapter.RvMultiViewAdp
import sidev.lib.android.siframe.customizable._init._Config
import sidev.lib.android.siframe.tool.util._NetworkUtil
import sidev.lib.android.siframe.tool.util._ViewUtil
import sidev.lib.android.siframe.tool.util._ViewUtil.Comp.getTvNote
import sidev.lib.android.siframe.tool.util._ViewUtil.setColor
import sidev.lib.android.siframe.tool.util.`fun`.detachFromParent
import sidev.lib.android.siframe.tool.util.`fun`.inflate
import sidev.lib.android.siframe.tool.util.`fun`.loge
import sidev.lib.universal.`fun`.*
import java.lang.Exception

class ContentAdp(c: Context, data: ArrayList<Content>?)
    : RvMultiViewAdp<Content, LinearLayoutManager>(c, data){

    val rvCheckBoxList= SparseArray<QuestionCheckBoxAdp>()
    val radioSelIndexList= SparseArray<Int>()
    val radioViewList= SparseArray<List<RadioButton>>()
    var isEditable= false
        set(v){
            field= v
            notifyDataSetChanged_()
        }
    val screenWidth= if(c is Activity) _ViewUtil.getScreenWidth(c)
    else ViewGroup.LayoutParams.MATCH_PARENT

    private val questionIndex= ArrayList<Int>()

    init{
        val cName= c.classSimpleName()
        loge("init{} cName= $cName")
    }

    override fun getItemViewType(pos: Int, data: Content): Int {
        return when(data){
            is ContentRead -> R.layout.comp_read
            is ContentVideo -> R.layout.comp_video
            is ContentQuestion -> {
                if(questionIndex.indexOf(pos) < 0) questionIndex.add(pos)
                R.layout.comp_question
            }
            else -> _Config.INT_EMPTY
        }
    }

    override fun bindVhMulti(vh: SimpleViewHolder, pos: Int, viewType: Int, data: Content) {
        val v= vh.itemView
//        val parName= v.parent?.classSimpleName()
//        loge("bindVhMulti() parName= $parName")
        v.findViewById<View>(_Config.ID_VG_CONTENT_CONTAINER).notNull { container ->
            container.layoutParams.width= screenWidth
            loge("bindVhMulti() setting parent width to MATCH_PARENT screenWidth= $screenWidth")
        }
        when(data){
            is ContentRead -> {
                val titleVis= if(data.title != null){
                    (v.et_title as EditText).setText(data.title!!)
                    View.VISIBLE
                } else
                    View.GONE
                v.et_title.visibility= titleVis
                (v.et_desc as EditText).setText(data.desc)
                enableEd(v.et_title, isEditable)
                enableEd(v.et_desc, isEditable)
                loge("bindVhMulti() ContentRead isEditable= $isEditable")
            }
            is ContentVideo -> {
                loge("video url= ${data.link}")
                val mc= MediaController(ctx)
                mc.setAnchorView(v.vv)
                v.vv.setMediaController(mc)
                v.vv.setVideoURI(data.link.toUri())

                v.pb.visibility= View.VISIBLE
                v.iv_play.visibility= View.GONE
                setColor(v.iv_play, R.color.white_transer)

                v.vv.setOnPreparedListener { mp ->
                    var isPlaying= false
                    v.pb.visibility= View.GONE
                    v.iv_play.visibility= View.VISIBLE
                    setColor(v.iv_play, R.color.white_trans)
                    v.iv_play.setOnClickListener {
                        if(!isPlaying){
                            mp.start()
                            it.visibility= View.GONE
                            isPlaying= true
                        } else {
                            mp.pause()
                            it.visibility= View.VISIBLE
                            isPlaying= false
                        }
                    }
                }

                v.vv.setOnErrorListener { mp, what, extra ->
                    v.pb.visibility= View.GONE
                    v.iv_play.setImageResource(R.drawable.ic_cross)
                    setColor(v.iv_play, R.color.merah)
                    true
                }

                getTvNote?.invoke(v).notNull { tv ->
                    if(data.note != null){
                        tv.text= data.note!!
                        tv.visibility= View.VISIBLE
                    } else
                        tv.visibility= View.GONE
                }
            }
            is ContentQuestion -> {
                (v.tv_question as TextView).text= data.question

                v.cv_text_container.visibility= View.GONE
                v.checkbox_container.visibility= View.GONE
                v.rg_container.visibility= View.GONE

                if(data.answerByReader == null)
                    data.answerByReader= ArrayList()

                when(data.answerKind){
                    Const.QUESTION_KIND_FILL -> {
                        vh.setIsRecyclable(false) //Karena akan merepotkan jika vh di posisi ini bisa di recycle karena ada TextWatcher nya. !!!
                        data.answerByReader!!.add("")
                        v.cv_text_container.visibility= View.VISIBLE
                        v.cv_text_container.et_fill.addTextChangedListener(object : TextWatcher{
                            override fun afterTextChanged(s: Editable?) {
                                data.answerByReader!![0]= s.toString() //
                            }
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                        })
                    }
                    Const.QUESTION_KIND_MULTIPLE -> {
                        v.checkbox_container.visibility= View.VISIBLE
                        rvCheckBoxList[pos].notNull { adp ->
                            adp.rv= v.checkbox_container
                        }.isNull {
                            try {
                                for(i in data.answerChoice!!.indices)
                                    data.answerByReader!!.add("")
                            } catch (e: Exception){ }

                            val adp= QuestionCheckBoxAdp(ctx, data.answerChoice)
                            adp.rv= v.checkbox_container
                            adp.onCheckedChangeListener= { isChecked, pos ->
                                data.answerByReader!![pos]=
                                    if(isChecked) pos.toString()
                                    else ""
                            }
                            rvCheckBoxList[pos]= adp
                        }
                    }
                    Const.QUESTION_KIND_PILGAN -> {
                        v.rg_container.visibility= View.VISIBLE
                        fillRadioView(v.rg_container, pos, data)
                    }
                }
            }

        }
    }

    override fun setupLayoutManager(): LinearLayoutManager = LinearLayoutManager(ctx)

    /**
     * @return map [question.id, question.answerByReader] di mana question.answerByReader yg berupa string kosong (")
     *          sudah dihilangkan.
     */
    fun getAnswerList(): Map<String, List<String>>{
        //jika pembaca tidak menjawab, maka value dari map memiliki size == 0
        //map [key, value] => [question.id, question.answerByReader]
        val map= HashMap<String, ArrayList<String>>()
        for(i in questionIndex){
            val quest= dataList!![i] as ContentQuestion
            when(quest.answerKind){
                Const.QUESTION_KIND_PILGAN -> {
                    val answer= ArrayList<String>()
                    radioSelIndexList[i].asNotNull { str: String -> answer.add(str) }
                    map[quest.id]= answer
                }
                Const.QUESTION_KIND_FILL -> {
                    if(quest.answerByReader!!.first().isBlank())
                        quest.answerByReader!!.clear()
                    map[quest.id]= quest.answerByReader!!
                }
                Const.QUESTION_KIND_MULTIPLE -> {
                    val removedInd= ArrayList<Int>()
                    for((i, answer) in quest.answerByReader!!.withIndex()){
                        if(answer.isBlank())
                            removedInd.add(i)
                    }
                    for(i in removedInd)
                        quest.answerByReader!!.removeAt(i)
                    map[quest.id]= quest.answerByReader!!
                }
            }
        }
        return map
    }

    fun clearAllAnswer(){
        dataList.notNull { list ->
            for((i, data) in list.withIndex())
                if(data is ContentQuestion){
                    data.answerByReader= null
                    radioSelIndexList.removeAt(i)
                }
        }
    }

    private fun fillRadioView(rg: RadioGroup, pos: Int, data: ContentQuestion){
        rg.removeAllViews()
        rg.setOnCheckedChangeListener(null)
        radioViewList[pos].notNull {
            for(view in it){
                view.detachFromParent()
                rg.addView(view)
            }

            val childInd= radioSelIndexList[pos].notNullTo { it }
                .isNullTo {
                    try{ data.answerByReader!!.first().toInt() }
                    catch (e: Exception){null}
                }
            if(childInd != null)
                rg.getChildAt(childInd).asNotNull { rb: RadioButton -> rb.isChecked= true }
        }.isNull {
            data.answerChoice.notNull { choiceList ->
                val list= ArrayList<RadioButton>()
                for(choice in choiceList){
                    ctx.inflate(R.layout.v_rb, rg).notNull { choiceV ->
                        (choiceV as RadioButton).text= choice
                        rg.addView(choiceV)
                        list.add(choiceV)
                    }
                }
                data.answerByReader.notNull { ansList ->
                    try{
                        val checkedInd= ansList.first().toInt()
                        list[checkedInd].isChecked= true
                    } catch (e: Exception){}
                }
                radioViewList[pos]= list
            }
        }
        rg.setOnCheckedChangeListener { group, checkedId ->
            val ind= group.getSelectedInd()
            if(ind >= 0){
                radioSelIndexList[pos]= ind
                data.answerByReader.notNull { arrList ->
                    if(arrList.isEmpty()) arrList.add(ind.toString())
                    else arrList[0]= ind.toString()
//                    loge("data.answerByReader.size= ${data.answerByReader!!.size} ind= $ind")
                }
            }
        }
    }
}