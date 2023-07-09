package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.ErrorCode

open class AppException(val code: Int, val msg: String) : RuntimeException() {
    constructor(errorCode: ErrorCode) : this(errorCode.code, errorCode.message)
}