package com.solvek.reliablefcm.utils

import java.text.DateFormat

private val FORMAT_TIME = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)

fun Long.formatTime() = FORMAT_TIME.format(this)