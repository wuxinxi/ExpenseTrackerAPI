package cn.xxstudy.expensetracker.constant

enum class HttpCode(val code: Int, val message: String) {
    ERROR(-1, "服务器无法处理此请求"),
    LOGIN_ERROR(-10001, "用户名密码错误"),
    REGISTER_ERROR(-10002, "注册邮箱已存在"),
    RECORD_ERROR(-10003, "保存失败"),
    FORMAT_ERROR(-10004, "格式错误"),
    TOKEN_ERROR(-10005, "Token验证失败"),
    REFRESH_TOKEN_ERROR(-10006, "请重新登陆"),
}