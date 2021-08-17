package com.example.CustomFanControllerApp

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

    private val SELECTION_COUNT = 2 //definisce il numero di posizioni diverse che il controller può assumere
    private var Selection = 0//stabilisce la posizione attuale del controller
    private var mButtonOnColor = Color.GREEN //definisce il colore che assume il controller quando è acceso
    private var mButtonOffColor = Color.RED //definisce il colore che assume il controller quando è sulla posizione 0

    //creiamo i due oggetti di tipo paint che definiscono il testo e i contenuto della Custom View, i cerchi da disegnare quindi
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

        // Get the custom attributes (fanOnColor and fanOffColor) if available.
        if (attributeSet != null) {
            val typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.OnOffButtonView, 0, 0)
            // Set the fan on and fan off colors from the attribute values.
            mButtonOnColor = typedArray.getColor(R.styleable.OnOffButtonView_ButtonOnColor, mButtonOnColor)
            mButtonOffColor = typedArray.getColor(R.styleable.OnOffButtonView_ButtonOffColor, mButtonOffColor)
            typedArray.recycle()
        }

        mTextPaint.color = Color.BLACK
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.textSize = 100f //30f
        mTextPaint.style = Paint.Style.FILL_AND_STROKE //la scrittura la facciamo piena e non solo sui contorni

        mButtonlPaint.color = mButtonOffColor //quindi il colore della posizione 0

        //Selection = 0 //anche se è già stato inizializzato neglia ttributi della classe

        //inizializziamo un Listener che permette di csmbiare la posizione del dial, quindi del quadrato
        setOnClickListener() {

            //ogni volta che clickiamo sul dial, la rotellina viene eseguito quersto blocco di codice
            Selection = (Selection + 1) % SELECTION_COUNT //quando si arriva all'ultima posizione bisogna tornare alla prima quindi facciamo il modulo del numero totale delle posizioni

            //in base alla posizione in cui si trova bisognerà cambiare il colore del dial

            if(Selection == 1) { //quindi se è attivo
                mButtonlPaint.color = mButtonOnColor
            } else{
                mButtonlPaint.color = mButtonOffColor
            }

            invalidate() //ogni volta che cambiamo il valore della posizione di mActiveSelection dobbiamo ridisegnare l'intera custom view quindi chiamiamo invalidate() che ridisegna da zero tutto
        }//END_listener
    }//END_init

    //metodo onDraw() che sovrascrive il metodo onDraw() della classe View ed è la callbacj chiamata per disegnare la view
    override fun onDraw(canvas: Canvas?) { //canvas è la bitmap dove va disegnata la view
        super.onDraw(canvas)

        // Distanza rettangolo dal Canvas
        val left = 0f
        val top = 0f
        val right = 400f // larghezza
        val bottom = 400f // altezza

        //val bottone = RectF(left, top, right, bottom)

        // DISEGNO IL BOTTONE
        if (canvas != null) {
            canvas.drawRect(left, top, right, bottom, mButtonlPaint)
            canvas.drawText("OFF", 550F, 300F, mTextPaint)
            canvas.drawText("ON", 1000F, 1000F, mTextPaint)
        }


        /**
        if (canvas != null) {
            canvas.drawText("OFF", 550F, 300F, mTextPaint)
        }
        if (canvas != null) {
            canvas.drawText("ON", 550F, 300F, mTextPaint)
        }**/

        /**
        //la prima cosa che disegniamo è il cerchio principale centrato nella posizione h/2 e w/2
        val mWidth = width.toFloat()
        val mHeight = height.toFloat()
        val mRadius = (Math.min(mWidth, mHeight) / 2 * 0.5).toFloat()

                if (canvas != null) {
                    canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mButtonlPaint) // G mettere i quadrato
                }

                if (canvas != null) {
                   canvas.drawText("OFF", 920F, 1000F, mTextPaint)
                }
                if (canvas != null) {
                    canvas.drawText("ON", 180F, 1000F, mTextPaint)
                }
        **/
    }//End_onDraw

}//END_Class
