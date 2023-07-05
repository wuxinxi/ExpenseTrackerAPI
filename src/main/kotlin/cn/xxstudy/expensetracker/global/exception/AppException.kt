package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.HttpCode

open class AppException(val code: Int, val msg: String) : RuntimeException() {
    constructor(httpCode: HttpCode) : this(httpCode.code, httpCode.message)
}