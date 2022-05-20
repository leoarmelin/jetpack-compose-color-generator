package com.example.colorgenerator.extensions

fun String.zeroBeforeNumeral(): String {
    return if (this.length <= 1) "0$this" else this
}