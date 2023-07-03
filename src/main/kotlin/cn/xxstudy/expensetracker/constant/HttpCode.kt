package cn.xxstudy.expensetracker.constant

enum class HttpCode(val code: Int, val message: String) {
    LOGIN_ERROR(-10001, "用户名密码错误"),
    REGISTER_ERROR(-10002, "注册邮箱已存在"),
    RECORD_ERROR(-10003, "保存失败"),
    FORMAT_ERROR(-10004, "格式错误"),
    TOKEN_ERROR(-10005, "Token验证失败"),
}