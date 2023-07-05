package cn.xxstudy.expensetracker.constant

enum class HttpCode(val code: Int, val message: String) {
    ERROR(-1, "服务器无法处理此请求"),
    LOGIN_ERROR(-10001, "用户名密码错误"),
    REGISTER_ERROR(-10002, "注册邮箱已存在"),
    RECORD_ERROR(-10003, "保存失败"),
    FORMAT_ERROR(-10004, "格式错误"),
    TOKEN_ERROR(-10005, "Token验证失败"),
    REFRESH_TOKEN_ERROR(-10006, "请重新登陆"),
    ID_ERROR(-10007, "ID不存在"),
    CATEGORY_ERROR(-10008, "分类已存在"),
    MEMBER_ERROR(-10008, "成员已存在"),
}