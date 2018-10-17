package com.example.felipehernandez.introduccionkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Imprimir
        println("Hola desde kotlin")

        //Variables y constantes
        var edad =  28
        edad = 30

        val nombre = "Felipe"
         //nombre  = "Oscar" //no se puede


        //Tipos explicitos
        val explicitdato: String  = "Felipe Hernandez"
        var edad2: Int = 28
        val estatura: Double = 1.79


        //combinacion de tipos
        val carrera  = "Sistemas"
        val generacion = 2017
        val datoCompleto = carrera +" Generacion "+2017

        //string interpolacion
        val numero1  =  3
        val numero2 = 5
        val respuesta = " La suma de $numero1 + $numero2 el resultado es ${numero1 + numero2}"


        //arreglos
        val nombres  =  arrayOf("felipe","oscar","hector","pedro")

        for (i in  nombres.indices){
            println("La persona ${i+1} se llama ${nombres[i]}")
        }

        //for
        for(index in 1..5){
            println("Tabla del 5 x $index ${index * 5}")
        }

        //Arreglos
        /*
        * ByteArray
        ShortArray
        LongArray
        FloatArray
        DoubleArray
        BooleanArray
        CharArray
        * */

        val calificaciones: IntArray = IntArray(5)

        calificaciones[0] = 10
        calificaciones[1] = 8
        calificaciones[2] = 7
        calificaciones[3] = 10
        calificaciones[4] = 8


        for(i in 0..4) {
            println("tu calificacion es ${calificaciones[i]}")
        }

        //Arrays
        val nombres2  =  arrayOf("felipe","oscar","hector","pedro")
        nombres2[1] = "Jesus"

        //maps
        val ocupaciones =  mutableMapOf("Felipe" to "sistemas",
                                        "Oscar" to "Arquitectura")
        ocupaciones["Pedro"] = "recursos Humanos"

        println(ocupaciones)

        //Inicializacion de Lista de objetos
        val arregloVacio = arrayOf<String>()
        val mapvacio  =  mapOf<String, Float>()


        //if y if como expression

        //if
        val valor1 = 35
        val valor2 = 40

        if (valor1 > valor2)
            println("El mayor valor es $valor1")
        else
            println("El mayor valor es $valor2")

        //if como expression
        val mayor = if (valor1 > valor2) valor1 else valor2
        println("El mayor entre $valor1 y $valor2 es $mayor")

       //while , do while siguen siendo lo mismo que en otros lenguajes

        //instrucion when

        var  semestre  = 2

        when (semestre) {
            1 -> println("Primer Semestre")
            2 -> println("Segundo Semestre")
            3 -> println("Tercer Semestre")
            4 -> println("Cuarto Semestre")
            else -> println("No te encuentras en los rangos del 1 al 4")
        }

        //Instrucion  when como funcion
        var tipoAlumno = "Bueno" //malo, Regular
        var nombreAlumno = "Felipe Hernandez"

        nombreAlumno += when {
            (tipoAlumno.equals("Bueno")==true)  -> {
                println("Saco 10  de calificacion")
                " porque saco 10 de calificacion"
            }
            (tipoAlumno.equals("Regular")==true)  -> {
                println("Saco 8  de calificacion")
                " porque saco 8 de calificacion"
            }
            else -> {
                println("Saco 5  de calificacion")
                " porque saco 5 de calificacion"
            }
        }

        println(nombreAlumno)


        //funciones
        println(misDatos("felipe","hernandez",28))


        var laSuma  = suma(8,7)
        println("la suma de los dos numero fue $laSuma")


        imprimirNombre("felipe hernandez")

        //funciones  como expression
        println("la suma de tres numeros es ${suma(3,6,8)}");


        //funciones valores por default
        resta(3)

        //funciones con parametros nombrados
        println(suma(numero2= 5,numero1 = 8,numero3 = 5))

        //funciones internas locales
        evaluacionAlumno(8,"Felipe Hernandez")


        //clases
        var alumno : Alumno = Alumno()
        alumno.inicio("Felipe",28,"Sistemas")
        alumno.imprimirDatos()
        alumno.cuantosSemestres()


        //Clase de con contructor
        var persona = Persona("Felipe",28,"Casado")
        persona.imprimirDatos()
        persona.esMayorEdad()

    }

    fun evaluacionAlumno(calificacion:Int, alumno:String)
    {
        fun BuenooMalo(cal: Int) =  if (cal <= 10 && cal >= 8) "bueno" else "Malo"

        println("el alumno $alumno se considera que es" +
                " ${BuenooMalo(calificacion)} porque saco $calificacion")

    }

    fun imprimirNombre (nombre : String){
        println("Tu nombre es  $nombre")
    }

    fun misDatos(nombre: String, apellido: String, edad: Int): String {
        return "Mi nombre es $nombre mi apellido es $apellido  y mi edad es $edad"
    }

    fun suma(numero1:Int,numero2:Int):Int{
        println(numero1 + numero2)
        return numero1 + numero2
    }

    fun suma(numero1: Int, numero2:Int,numero3:Int) = numero1 + numero2 + numero3

    fun resta(numero1 : Int, numero2 : Int = 2){
        println("la resta de los dos numeros es ${numero1 - numero2}")
    }


    //creacion de clases
    class Alumno {
        var nombre: String = ""
        var carrera: String = ""
        var edad: Int = 0

        fun inicio(nombre: String, edad: Int, carrera: String) {
            this.nombre = nombre
            this.edad = edad
            this.carrera =  carrera
        }

        fun imprimirDatos() {
            println("tu nombre es $nombre y tienes una edad de " +
                    "$edad y actualmente cursas esta carrera $carrera")
        }

        fun cuantosSemestres() {
            if (carrera.equals("Sistemas"))
                println("Los semestres a cursar son 8")
            else
                println("No se tiene registro de otra carrera")
        }
    }
}
