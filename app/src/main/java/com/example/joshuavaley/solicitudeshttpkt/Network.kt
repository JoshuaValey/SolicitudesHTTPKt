package com.example.joshuavaley.solicitudeshttpkt

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity

class Network {


    //Forma de trabajar con static
    companion object {
        /***
         * @see Metodo para verificar si hay una red disponible
         * @see connectivityManager administra temas de red
         * @see networkInfo devuelve la informacion de la red
         * @return bool para verificar si hay o no red
         */
        fun hayRed(activity: AppCompatActivity):Boolean{

            val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE)as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            //Esto es como un if else para devolper el valor true o false.
            return networkInfo != null && networkInfo.isConnected //si el valor de la red es diferente de null y esta conectado = true
                                                                 //si no esta conectado es false
        }
    }
}