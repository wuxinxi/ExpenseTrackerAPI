package cn.xxstudy.expensetracker.global.exception

open class AppException(val code: Int, val msg: String) : RuntimeException() {

}