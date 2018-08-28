package com.example.joshuavaley.solicitudeshttpkt

import android.os.AsyncTask
import android.os.StrictMode
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

//Esta clase es para implementar las solicitudes HTTP en un thread diferente
//al principal.(Por eso es asincrono)
class DescargaUrl(var completadoListener: CompletadoListener?):AsyncTask<String, Void,String>(){


    //Metod implementado de clase Asincrona
    //Insertar info que se quiere ejecutar en este thread
    override fun doInBackground(vararg params: String): String? {
        //procesa la descargas de los datos.
        try {

            return descargarDatos(params[0])
        }catch (e:IOException){
            return null
        }

    }

    /***
     * Nos permite ejecutar el codigo, luego de que se hayan descargado los datos.
     */
    //manda los datos.
    override fun onPostExecute(result: String) {

        try {
                completadoListener?.descargaCompleta(result)
        }catch (e:Exception){

        }
    }

    @Throws(IOException::class)/*Previene que truene la app en casos inesperados, Por falta de conectividad, que se corte la coneccion*/
    private fun descargarDatos(url:String):String {

        var inputStream: InputStream? = null //Contener un objeto coon flujo de datos.

        try {
            val url = URL(url) //Metodo de libreria java.net.URL -> Crear una url
            //Abrir una coneccion tipo http
            val conn = url.openConnection() as HttpURLConnection //mapeada como HttpURLConnection
            //Metodo
            conn.requestMethod = "GET"
            conn.connect() //abrir la coneccion -> manda a llamar la URL

            //Contener el flujo de datos de arriba en el inputStream de abajo

            inputStream = conn.inputStream //dame el flujo de datos mapeado a mi variable input
            //Regresar el flujo de datos en forma del metodo readText() que lo enviara como STRING.
            return inputStream.bufferedReader().use {
                it.readText()
            }
        }finally {
            if (inputStream != null)
                inputStream.close()
        }
    }

}