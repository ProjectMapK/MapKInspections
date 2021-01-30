package com.mapk.common.targets

import com.mapk.common.NoArg

@NoArg
data class FiveArgs(
    var arg0: Int,
    var arg1: Int,
    var arg2: Int,
    var arg3: Int,
    var arg4: Int
)

// MapStructは無引数コンストラクタが有るとコンストラクタ呼び出しが機能しない
data class FiveArgs2(
    var arg0: Int,
    var arg1: Int,
    var arg2: Int,
    var arg3: Int,
    var arg4: Int
)
