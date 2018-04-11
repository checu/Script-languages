import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Label
import tornadofx.*
import java.lang.Math.pow
import java.math.BigDecimal


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
        var stack=label()
        hbox {
            button("1") {
                prefWidth = 30.0
                action {
                    println("pressed!")
                    liczba=liczba+"1"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('1').toString()))
//                    stack.bind(SimpleStringProperty("1"))
//                    Action('1') //to powinno byc nad wyswietlaniem
                }
            }
            button("2") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"2"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('2').toString()))
                }
            }
            button("3") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"3"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('3').toString()))
                }
            }
            button("+") {
                prefWidth = 30.0
                action {
                    signReplacement(stack,'+')
                }
            }
            button("CE") {
                prefWidth = 30.0
                action {
                    println("pressed!")
                    liczba=""
                    updateValueIn()
                    stack.bind(SimpleStringProperty("0"))
                    ValStack= Stack<Double>()
                    OpeStack= Stack<Char>()
                    flagaN=false
                    flagPrze=false
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
                    stack.bind(SimpleStringProperty(Action('4').toString()))
//                    stack.bind(SimpleStringProperty("4"))
//                    Action('4')
                }
            }
            button("5") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"5"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('5').toString()))
                }
            }
            button("6") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"6"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('6').toString()))
//                    stack.bind(SimpleStringProperty("6"))
//                    Action('6')
                }
            }
            button("-") {
                prefWidth = 30.0
                action {
                    signReplacement(stack,'-')
//                    liczba=liczba+"-"
//                    updateValueIn()
//
//                    stack.bind(SimpleStringProperty(Action('-').toString()))
                }
            }
            button(",") {
                prefWidth = 30.0
                    action {
                        if (liczba[liczba.length - 1] != ',')
                        {
                        if (liczba.length == 0 || isOperator(liczba[liczba.length - 1])) {
                            liczba = liczba + "0,"
                            updateValueIn()
                            stack.bind(SimpleStringProperty("0,"))
                        } else {
                            liczba = liczba + ","
                            updateValueIn()
                        }
                        stack.bind(SimpleStringProperty(Action(',').toString()))
                        }

                    }

            }

        }
        hbox {
            button("7") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"7"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('7').toString()))
//                    Action('7')
                }
            }
            button("8") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"8"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('8').toString()))
//                    Action('8')
                }
            }
            button("9") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"9"
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('9').toString()))
//                    Action('9')
                }
            }
            button("*") {
                prefWidth = 30.0
                action {
                    signReplacement(stack,'*')
//                    liczba=liczba+"*"
//                    updateValueIn()
//                    stack.bind(SimpleStringProperty(Action('*').toString()))
                }
            }
            button("=") {
                prefWidth = 30.0
                prefHeight=60.0
                action {
                    stack.bind(SimpleStringProperty(EqualSign().toString()))
                    liczba=""
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
                    stack.bind(SimpleStringProperty(Action('0').toString()))
//                    stack.bind(SimpleStringProperty("0"))
//                    Action('0')
                }
            }
            button("(") {
                prefWidth = 30.0
                action {
                    liczba=liczba+"("
                    updateValueIn()
                    stack.bind(SimpleStringProperty(Action('(').toString()))
                }
            }
            button(")") {
                prefWidth = 30.0
                action {
                    if (liczba.contains('(')) {
                        if (liczba[liczba.length - 1] == '(') {
                            OpeStack.pop()
                            liczba = liczba + "0)"
                            updateValueIn()
                            stack.bind(SimpleStringProperty(Action('0').toString()))
                        } else {
                            liczba = liczba + ")"
                            updateValueIn()
                            stack.bind(SimpleStringProperty(Action(')').toString()))
                        }
                    }
                    else
                    {
                        return@action
                    }
                }
            }
            button("/") {
                prefWidth = 30.0
                action {
                    signReplacement(stack,'/')
//                    liczba=liczba+"/"
//                    updateValueIn()
//                    stack.bind(SimpleStringProperty(Action('/').toString()))
                }
            }

        }


    }

    private fun signReplacement(stack: Label,sign: Char) {
        if (isOperatorG(liczba[liczba.length - 1]) && (liczba[liczba.length - 1] != sign)) {
            OpeStack.pop()
            elm = ValStack.pop()!!
            liczba=liczba.replace(liczba[liczba.length - 1], sign)

        } else if (liczba[liczba.length - 1] == sign) {
            liczba = liczba
            return
        } else {
            liczba = liczba + sign.toString()
        }
        updateValueIn()
        stack.bind(SimpleStringProperty(Action(sign).toString()))
    }

    var ValStack= Stack<Double>()
    var OpeStack= Stack<Char>()
    var flagaN=false
    var flagPrze=false
    var elm: Double=0.0
    var inkrementator:Int=0


    fun EqualSign():Double?
    {
        var oper:Char

        if (liczba[liczba.length - 1].isDigit())
        {
        while(OpeStack.count()!=0)
        {
//            elm= ValStack.pop()!!
            val prev=ValStack.pop()
            val spot=OpeStack.pop()
            elm= calculation(prev!!,elm, spot!!)
            println("wartosci=" + ValStack)
            println("operatory=" + OpeStack)
        }
        }
        else if(isOperatorG(liczba[liczba.length - 1]))
        {
            oper = OpeStack.pop()!!
            elm= ValStack.pop()!!
            println("po zabraniu operatora"+OpeStack)
            if(!OpeStack.isEmpty())
            {
            while(OpeStack.count()!=0)
            {
//                elm= ValStack.pop()!!
                val prev=ValStack.pop()
                val spot=OpeStack.pop()
                elm= calculation(prev!!,elm, spot!!)
                println("wartosci=" + ValStack)
                println("operatory=" + OpeStack)
            }
            }
            when(oper){
                '+'-> elm += elm
                '-'-> elm -= elm
                '*'-> elm *= elm
                '/'-> elm /= elm
            }
        }
        else
        {
            OpeStack.pop()
            liczba.dropLast(1)
            println(liczba)
            EqualSign()
        }
        return elm

    }


    fun Action(character:Char): Double? {

//        var pos: Int =0

//        while (pos<character.length){

            var spot= character
//            println(spot)



            if (spot.isDigit()) {
                if (flagPrze==true) {
                    inkrementator+=1;
                    spot=spot-48
                    println(pow(10.0,(-1*inkrementator).toDouble()))
                    println(spot.toDouble())
                    elm= elm+spot.toDouble()*pow(10.0,(-1*inkrementator).toDouble())
                    println("wartosc elm float"+elm)
                    return elm

                }
                else {
                    elm = (elm * 10) + (spot.toInt() - 48)
                    return elm
                }
            }
            else if(spot==','){
                flagPrze=true
                if(elm== null){
                    elm=0.0
                    return elm
                }
                else if (elm==0.0){
                    return elm
                }
                else
                {
                    return elm
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
                        return elm
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
        return if(ValStack.isEmpty()) {
            elm
        } else {
            ValStack.peek()!!
        }
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