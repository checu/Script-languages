import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Label
import tornadofx.*
import java.lang.Math.pow




class MyApp: App(MyView::class)

class MyView: View() {

    var liczba=""
    lateinit var labelek: Label

    fun updateValueIn() {
        labelek.bind(SimpleStringProperty(liczba))
    }

    override var root = vbox {
        var a =label()
        labelek = a
        hbox {
            button("1") {
                prefWidth = 30.0
                action {
                    println("pressed!")
                    liczba=liczba+"1"
                    updateValueIn()
                    Action('1') //to powinno byc nad wyswietlaniem
                }
            }
            button("2") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"2"
                    updateValueIn()
                    Action('2')
                }
            }
            button("3") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"3"
                    updateValueIn()
                    Action('3')
                }
            }
            button("+") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"+"
                    updateValueIn()
                    Action('+')
                }
            }
            button("CE") {
                prefWidth = 30.0
                action {
                    println("pressed!")
                    liczba=""
                    updateValueIn()
                    ValStack= Stack<Double>()
                    OpeStack= Stack<Char>()
                    flagaN=false
                    elm =0.0
                }
            }

        }
        hbox {
            button("4") {
                prefWidth = 30.0
                action {

                    liczba=liczba+"4"
                    updateValueIn()
                    Action('4')
                }
            }
            button("5") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"5"
                    updateValueIn()
                    Action('5')
                }
            }
            button("6") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"6"
                    updateValueIn()
                    Action('6')
                }
            }
            button("-") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"-"
                    updateValueIn()

                    Action('-')
                }
            }
            button(",") {
                prefWidth = 30.0
                action {
                    liczba=liczba+","
                    updateValueIn()
                    Action(',')
                }
            }

        }
        hbox {
            button("7") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"7"
                    updateValueIn()
                    Action('7')
                }
            }
            button("8") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"8"
                    updateValueIn()
                    Action('8')
                }
            }
            button("9") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"9"
                    updateValueIn()
                    Action('9')
                }
            }
            button("*") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"*"
                    updateValueIn()
                    Action('*')
                }
            }
            button("=") {
                prefWidth = 30.0
                prefHeight=60.0
                action {
                    liczba=liczba+"="
                    updateValueIn()
                    println("pressed!")
                }
            }

        }
        hbox {
            button("0") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"0"
                    updateValueIn()
                    Action('0')
                }
            }
            button("(") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"("
                    updateValueIn()
                    Action('(')
                }
            }
            button(")") {
                prefWidth = 30.0
                action {
                    liczba=liczba+")"
                    updateValueIn()
                    Action(')')
                }
            }
            button("/") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"/"
                    updateValueIn()
                    Action('/')
                }
            }

        }

        label("Stack:")
    }

    var ValStack= Stack<Double>()
    var OpeStack= Stack<Char>()
    var flagaN=false
    var flagPrze=false
    var elm: Double = 0.0
    var inkrementator:Int=0

    fun Action(character:Char) {

//        var pos: Int =0

//        while (pos<character.length){

            var spot= character
//            println(spot)



            if (spot.isDigit()) {
                if (flagPrze==true) {
                    inkrementator+=1;
                    spot=spot-48
                    println("SPOT"+spot)
                    elm= elm+spot.toDouble()*pow(10.0,(-1*inkrementator).toDouble())
                    println("WYNIK"+elm)

                }
                else {
                    elm = (elm * 10) + (spot.toInt() - 48)
                }
            }
            else if(spot==','){
                flagPrze=true
                if(elm== null){
                    elm=0.0
                }

            }

            else if (isOperator(spot)){
                inkrementator=0
                flagPrze=false
                if(spot=='('){
                    if (!OpeStack.isEmpty()){
                        flagaN=true
                    }
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
                    if(flagaN==false){
                        println("zamykajacy nawias")
                        println(ValStack)
                        flagaN=false
                        ValStack.push(elm)
                        while (OpeStack.peek()!='('){
                            spot= OpeStack.pop()!!
                            elm= ValStack.pop()!!
                            val prev=ValStack.pop()
                            elm=calculation(prev!!,elm,spot)
                            ValStack.push(elm)
                        }
                        OpeStack.pop()
                        ValStack.pop()

                    }
                    else{
                    println("zamykajacy nawias")
                    println(ValStack)
                    ValStack.push(elm)
                    while (OpeStack.peek()!='('){
                        spot= OpeStack.pop()!!
                        elm= ValStack.pop()!!
                        val prev=ValStack.pop()
                        elm=calculation(prev!!,elm,spot)
//                        ValStack.push(elm)

                    }
                        OpeStack.pop()
                        println("wartosci o nawiasi"+ValStack)
                        println("wartoscipo nawiasie"+OpeStack)
                        flagaN=false
                    }

                }

                else {
                    if(flagaN==false){
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
                        var preops = OpeStack.pop()
                        var preelm = ValStack.pop()
                        preelm=calculation(preelm!!,elm, preops!!)
                        ValStack.push(preelm!!)
                        OpeStack.push(spot)
                        println("wartosci" + ValStack)
                        println("operatory" + OpeStack)
                        elm = 0.0
                    }
                    }
                    else{
                        if(OpeStack.peek()=='('){
                            OpeStack.push(spot)
                            ValStack.push(elm)
                            elm=0.0
                        }
                        else{
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
                                var preops = OpeStack.pop()
                                var preelm = ValStack.pop()
                                preelm=calculation(preelm!!,elm, preops!!)
                                ValStack.push(preelm!!)
                                OpeStack.push(spot)
                                println("wartosci" + ValStack)
                                println("operatory" + OpeStack)
                                elm = 0.0
                            }
                        }


                    }
                }

            }


//        ValStack.push(elm)
        return println("wynik "+elm)

    }

}

class MyViewModel : ItemViewModel<MyView>() {
    val liczba = bind(MyView::liczba)
    val labelek = bind(MyView::labelek)
    val root = bind(MyView::root)
    val ValStack = bind(MyView::ValStack)
    val OpeStack = bind(MyView::OpeStack)
    val flagaN = bind(MyView::flagaN)
    val flagPrze = bind(MyView::flagPrze)
    val elm = bind(MyView::elm)
}


fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}