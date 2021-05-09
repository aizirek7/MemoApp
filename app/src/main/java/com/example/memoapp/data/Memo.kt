package com.example.memoapp.data

import java.io.Serializable
import java.util.*

data class Memo(val memoTitle:String, val memoDescription:String, val date : StringBuilder) : Serializable
