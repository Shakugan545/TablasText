package nava.mayoral.tablas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.widget.AdapterView.OnItemClickListener


class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, TextToSpeech.OnInitListener, OnItemClickListener {
    var tts : TextToSpeech? = null

    var progreso:Int? = null

    var elementos = arrayOfNulls<String>(11)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekControl.setOnSeekBarChangeListener(this)
        calcularTablas(seekControl.progress)
        lvTablas!!.setOnItemClickListener(this)






    }



    fun calcularTablas(progreso: Int){
        tvLeyenda.text = "Tabla del $progreso"
        for (i in 0..10){
            val texto  = "$progreso x $i = ${progreso*i}"
            elementos.set(i,texto)
        }
       val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,elementos)

        lvTablas.adapter = adapter
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        calcularTablas(progress)

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }
    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale("es_MX"))
            if(result != TextToSpeech.LANG_NOT_SUPPORTED || result != TextToSpeech.LANG_MISSING_DATA){

            }else{
                Toast.makeText(this,"no soportado",Toast.LENGTH_SHORT).show()

            }
        }

    }
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var texto = elementos.get(position)!!.replace("*"," por ")
        Toast.makeText(this,"presionado",Toast.LENGTH_SHORT).show()
        tts!!.speak(texto,TextToSpeech.QUEUE_FLUSH,null,null)
    }

    override fun onDestroy() {
        if (tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}
