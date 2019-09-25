package Entidades

class Evento {

    var iD_EVENTO = 0
    var descripcioN_EVENTO = ""
    var diA_EVENTO = ""

    fun Evento() {}

    fun Evento(ID_EVENTO:Int, DESCRIPCION_EVENTO:String, DIA_EVENTO:String) {
        this.iD_EVENTO = ID_EVENTO
        this.descripcioN_EVENTO = DESCRIPCION_EVENTO
        this.diA_EVENTO = DIA_EVENTO
    }

}
