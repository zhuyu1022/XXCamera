package com.leqi.lwcamera.util

import android.text.method.ReplacementTransformationMethod

/**
 * Created by hasee on 2017/4/20.
 *
 */
class A2bigA : ReplacementTransformationMethod() {
    override fun getOriginal() : CharArray {
        return charArrayOf('a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' , 'w' , 'x' , 'y' , 'z')
    }

    override fun getReplacement() : CharArray {
        return charArrayOf('A' , 'B' , 'C' , 'D' , 'E' , 'F' , 'G' , 'H' , 'I' , 'J' , 'K' , 'L' , 'M' , 'N' , 'O' , 'P' , 'Q' , 'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 'X' , 'Y' , 'Z')
    }
}
