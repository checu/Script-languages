import javafx.application.Application
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import tornadofx.*
import java.util.*
import javax.print.attribute.IntegerSyntax


// stack implementation
class Stack<T>{
    val elements: MutableList<T> = mutableListOf()
    fun isEmpty() = elements.isEmpty()
    fun count() = elements.size
    fun push(item: T) = elements.add(item)
    fun pop() : T? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }
    fun peek() : T? = elements.lastOrNull()

    override fun toString(): String = elements.toString()
}

//fun <T> Stack<T>.push(items: Collection<T>) = items.forEach { this.push(it) }


fun isOperator(charter:Char): Boolean{
        return(charter=='+'||charter=='-'||charter=='*'||charter=='/'||charter=='('||charter==')')
}

fun isOperatorG(charter:Char): Boolean
{
    return(charter=='+'||charter=='-'||charter=='*'||charter=='/')
}

fun calculation(number1:Double, number2:Double, operator:Char): Double{
    return when(operator) {
        '+'-> number1+number2
        '-' -> number1-number2
        '*' -> number1*number2
        else -> number1/number2
    }
}

fun Priority(charter: Char): Int
{
    return when(charter) {
        '+','-' -> 1
        '*','/' -> 2
        '(',')' -> 3
        else -> -1
    }
}

fun Action(expresion:String) {
    val ValStack= Stack<Double>()
    val OpeStack= Stack<Char>()
    var flagaN=false

    var elm: Double =0.0
    var pos: Int =0

    while (pos<expresion.length){

        var spot= expresion[pos]
        println(spot)



        if (spot.isDigit()) {

            if(pos==expresion.length-1){
                elm = (elm * 10) + (spot.toInt() - 48)
                var preops=OpeStack.pop()
                var preelm=ValStack.pop()
                preelm=calculation(preelm!!,elm, preops!!)
                ValStack.push(preelm!!)
                println("wartosci"+ValStack)
                println("operatory"+OpeStack)
                elm= ValStack.pop()!!
            }
            else {
                // sprawdz czy nastepny wyraz to liczba i jesli tak to dodaj do elemt
                elm = (elm * 10) + (spot.toInt() - 48)
            }
            //funkcja do znaku = , ale zeby dzialaloo tutaj jest potrzebna

//            if(expresion.length-1==pos){
//                var preops1=OpeStack.pop()
//                var preelm1=ValStack.pop()
//                elm=calculation(preelm1!!,elm, preops1!!)
//                ValStack.push(elm!!)
//            }
        }

        else if (isOperator(spot)){
             if(spot=='('){
                 flagaN=true
                 println("otwarcie nawiasu")
                 OpeStack.push(spot)
                 println("wartosci"+ValStack)
                 println("operatory"+OpeStack)
                 elm=0.0// i o co chodzi temu gownu
                }
             else if (ValStack.isEmpty()){
                 println("pusty stack")
                 ValStack.push(elm)
                 println("wartosci"+ValStack)
                 OpeStack.push(spot)
                 println("operatory"+OpeStack)
                 elm=0.0
             }
             else if (spot==')'){
                 println("zamykajacy nawias")
                 flagaN=false
                 ValStack.push(elm)
                 while (OpeStack.peek()!='('){
                     spot= OpeStack.pop()!!
                     elm= ValStack.pop()!!
                     val prev=ValStack.pop()
                     elm=calculation(prev!!,elm,spot)
                     ValStack.push(elm)
                 }
                 if(pos==expresion.length-1 ){
                     OpeStack.pop()
                     var preops=OpeStack.pop()
                     println("poprzednioperator "+preops)
                     ValStack.pop()
                     var preelm=ValStack.pop()
                     println("poprzedniZnak "+preelm)
                     preelm=calculation(preelm!!,elm, preops!!)
                     ValStack.push(preelm!!)
                     println("wartosci"+ValStack)
                     println("operatory"+OpeStack)
                     elm= ValStack.pop()!!
                 }

                 else {
                     OpeStack.pop()
                     ValStack.pop()
                 }
            }
            else {
                 val prev =OpeStack.peek()
                 if(Priority(spot)>Priority(prev!!)){
                     println("wyzszy priorytet")
                     ValStack.push(elm)
                     println("wartosci"+ValStack)
                     OpeStack.push(spot)
                     println("operatory"+OpeStack)
                     elm=0.0
                 }
                 else{
                     if(flagaN){
                         ValStack.push(elm)
                         OpeStack.push(spot)
//                         var preops = OpeStack.pop()
//                         var preelm = ValStack.pop()
//                         preelm=calculation(preelm!!,elm, spot!!)
//                         ValStack.push(preelm!!)
//                         OpeStack.push(preops!!)// zmienilam ze spot--preops dla nawiasu
                         println("wartosci" + ValStack)
                         println("operatory" + OpeStack)
                         elm = 0.0
                     }

                     else {
                         var preops = OpeStack.pop()
                         var preelm = ValStack.pop()
                         preelm = calculation(preelm!!, elm, preops!!)
//                     preelm=calculation(preelm!!,elm, spot!!)-- jak byl nawias
                         ValStack.push(preelm!!)
                         OpeStack.push(spot)// zmienilam ze spot--preops dla nawiasu
                         println("wartosci" + ValStack)
                         println("operatory" + OpeStack)
                         elm = 0.0
                     }
                 }
             }

        }
        pos += 1


    }

    while(OpeStack.count()!=0)
    {
        elm= ValStack.pop()!!
        val prev=ValStack.pop()
        val spot=OpeStack.pop()
        elm= calculation(prev!!,elm, spot!!)
        println("wartosci" + ValStack)
        println("operatory" + OpeStack)
    }

    return println("wynik "+elm)
    }




fun main(args: Array<String>) {
    //Application.launch(MyApp::class.java, *args)
    Action("2*(2*3+4)")
}


