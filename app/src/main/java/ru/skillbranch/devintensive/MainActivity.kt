package ru.skillbranch.devintensive
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardOpen
//import ru.skillbranch.devintensive.extensions.hideKeyboard
//import ru.skillbranch.devintensive.extensions.isKeyboardOpen
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity() , View.OnClickListener, TextView.OnEditorActionListener {
    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()
        createActionDone()


        val mStatus = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val mQuestion = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        Log.d("M_MainActivity", "onCreate $mStatus $mQuestion")

        benderObj = Bender(Bender.Status.valueOf(mStatus), Bender.Question.valueOf(mQuestion))

        textTxt.text = benderObj.askQuestion()

        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

    }

    private fun initView() {
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send
        sendBtn.setOnClickListener(this)
    }

    private fun createActionDone() {
        et_message.setRawInputType(InputType.TYPE_CLASS_TEXT)
        et_message.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) sendBtn.performClick()
            false
        }
    }

    override fun onRestart() {
        super.onRestart()

        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()

        Log.d("M_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("STATUS", benderObj.status.name)
        outState.putString("QUESTION", benderObj.question.name)

        Log.d("M_MainActivity", "onSaveInstanceState ${benderObj.status.name}  ${benderObj.question.name}")
    }

    private fun makeSendOnActionDone(editText: EditText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) sendBtn.performClick()
            false
        }
    }

    private fun sendAnswer() {
        val (phase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
        messageEt.setText("")
        val(r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phase
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send)
            sendAnswer()
    }
}



