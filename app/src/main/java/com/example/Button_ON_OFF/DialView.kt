package com.example.Button_ON_OFF

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlin.math.cos
import kotlin.math.sin

class DialView : View {

    private val SELECTION_COUNT = 2 //definisce il numero di stati diversi che il controller può assumere
    private var Selection = 0//stabilisce lo stato attuale del controller
    private var mButtonOnColor = Color.GREEN //definisce il colore che assume il controller quando è acceso
    private var mButtonOffColor = Color.RED //definisce il colore che assume il controller quando è spento

    //creiamo i due oggetti di tipo paint che definiscono il testo e i contenuto della Custom View, i rettangoli da disegnare quindi
    private var mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG) //lo inizializiamo aplicando l'anti alias per evitare l'effetto quadrettatura
    private var mButtonlPaint = Paint(Paint.ANTI_ALIAS_FLAG) //il secondo paint è defiito per l'oggetto


    constructor(context: Context?) : super(context){
        init(null)
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet){
        init(attributeSet)
    }

    constructor(context: Context?, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr){
        init(attributeSet)
    }

    private fun init(attributeSet: AttributeSet?){ //funzione che esegue l'inizializzazione degli oggetti utilizzati per disegnare la custom view (oggetti di tipo paint)

        if (attributeSet != null) {
            val typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.OnOffButtonView, 0, 0)
            mButtonOnColor = typedArray.getColor(R.styleable.OnOffButtonView_ButtonOnColor, mButtonOnColor)
            mButtonOffColor = typedArray.getColor(R.styleable.OnOffButtonView_ButtonOffColor, mButtonOffColor)
            typedArray.recycle()
        }

        mTextPaint.color = Color.BLACK
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.textSize = 100f
        mTextPaint.style = Paint.Style.FILL_AND_STROKE //la scrittura la facciamo piena e non solo sui contorni

        mButtonlPaint.color = mButtonOffColor //il colore della posizione 0

        //inizializziamo un Listener che permette di csmbiare la posizione del bottone, quindi del quadrato
        setOnClickListener() {

            //ogni volta che clickiamo sul bottone, viene eseguito quersto blocco di codice
            Selection = (Selection + 1) % SELECTION_COUNT

            //in base alla posizione in cui si trova bisognerà cambiare il colore del bottone

            if(Selection == 1) { //quindi se è attivo
                mButtonlPaint.color = mButtonOnColor
            } else{
                mButtonlPaint.color = mButtonOffColor
            }

            invalidate() //ogni volta che cambiamo il valore della posizione di mActiveSelection dobbiamo ridisegnare l'intera custom view quindi chiamiamo invalidate() che ridisegna da zero tutto
        }//END_listener
    }//END_init

    //metodo onDraw() che sovrascrive il metodo onDraw() della classe View ed è la callback chiamata per disegnare la view
    override fun onDraw(canvas: Canvas?) { //canvas è la bitmap dove va disegnata la view
        super.onDraw(canvas)

        // Distanza rettangolo dal Canvas
        val left = 0f
        val top = 0f
        val right = 400f // larghezza
        val bottom = 400f // altezza

        // DISEGNO IL BOTTONE
        if (canvas != null) {
            canvas.drawRect(left, top, right, bottom, mButtonlPaint)
        }

        if (canvas != null && Selection == 1) {
            canvas.drawText("ON", 550F, 300F, mTextPaint)
        }
        if (canvas != null&& Selection == 0) {
            canvas.drawText("OFF", 550F, 300F, mTextPaint)
        }
    }//End_onDraw

}//END_Class
