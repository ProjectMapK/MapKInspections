package com.mapk.common.targets

import com.mapk.common.NoArg

@NoArg
data class TwentyArgs(
    var arg00: Int,
    var arg01: Int,
    var arg02: Int,
    var arg03: Int,
    var arg04: Int,
    var arg05: Int,
    var arg06: Int,
    var arg07: Int,
    var arg08: Int,
    var arg09: Int,
    var arg10: Int,
    var arg11: Int,
    var arg12: Int,
    var arg13: Int,
    var arg14: Int,
    var arg15: Int,
    var arg16: Int,
    var arg17: Int,
    var arg18: Int,
    var arg19: Int
) {
    constructor(arg: Int) : this(
        arg, arg, arg, arg, arg, arg, arg, arg, arg, arg,
        arg, arg, arg, arg, arg, arg, arg, arg, arg, arg
    )
}
