package com.example.joshuavaley.solicitudeshttpkt

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.IOException

class MainActivity : AppCompatActivity(),CompletadoListener/*Herencia de Interface*/{

    override fun descargaCompleta(resultado: String) {

        Log.d("descargaCompleta", resultado)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Instancia recursos Nativos
        //--------------------- Instancia de Recursos XML ------------------
        val btnValidar = findViewById<Button>(R.id.btnValidarHttp)
        val btnSolicitudNativa = findViewById<Button>(R.id.btnSolicitudNativa)
        //endregion

        // Solicitud Volley: Instancia del Boton
        val btnVolley = findViewById<Button>(R.id.btnVolley)
        // Solicitud OkHttp: Instancia de boton
        val btnOk = findViewById<Button>(R.id.btnOkHttp)

        //region OnClick OkHttp
        btnOk.setOnClickListener {

            if (Network.hayRed(this))
                solicitudOkHttp("http://www.google.com")
            else
                Toast.makeText(this, "No hay coneccion", Toast.LENGTH_LONG).show()
        }
        //endregion


        //region onclick Volley
        //----------------- Onclick btnVolley -------------
        btnVolley.setOnClickListener {
            //Validar que la red exista usando clase Network
            if (Network.hayRed(this))
                solicitudHttpVolley("http://www.google.com")
            else
            Toast.makeText(this,"No hay coneccion",Toast.LENGTH_SHORT).show()
        }
//endregion

        //region Metodos Onclick Nativos

        //--------------------- OnClick btnValidar ---------------------
        //Solo valida la coneccion y muestra un Toast
        btnValidar.setOnClickListener{

            if (Network.hayRed(this))
                Toast.makeText(this, "Si hay red", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "No hay una conexión a internet.", Toast.LENGTH_LONG).show()
        }

        //--------------------- OnClick Solicitud HTTP ---------------------
        //Hace la solicitud HTTP
        btnSolicitudNativa.setOnClickListener {
            // paso 1 -> validar la red.

            if (Network.hayRed(this)) {
                // Log.d("btnSolicitudOnClick",descargarDatos("http://www.google.com"))
                DescargaUrl(this).execute("http://www.google.com") //Ejecutar el codigo de otro thread
            }
            else
                Toast.makeText(this, "No hay una conexión a internet.", Toast.LENGTH_LONG).show()


        }

        //endregion

    }


    //Metodo para Volley
    private fun solicitudHttpVolley(url:String){

        var queue = Volley.newRequestQueue(this)
                        //Metodo de volley: Solicitud en forma de String.
        var solicitud = StringRequest(Request.Method.GET,url, Response.Listener<String>{
            response ->
            try {
                    Log.d("solicitudHttpVolley", response)
            }catch (e:Exception){

            }
        }, Response.ErrorListener {  })

        queue.add(solicitud)//añadir la solicitud a la cola(queue)
    }
    //Metodo para okHttp
    private fun solicitudOkHttp(url:String){
        var cliente = OkHttpClient()
        var solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object :okhttp3.Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                //Implementar Error
            }

            override fun onResponse(call: Call?, response: okhttp3.Response) {
                //Mandar un Log con el resultado
                var result = response.body().string()

                this@MainActivity.runOnUiThread{
                    try {
                            Log.d("SolicitudHttpOkHttp", result)
                    }catch (e:Exception)
                    {

                    }
                }
            }
        })
    }
}
