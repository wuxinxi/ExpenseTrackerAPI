package cn.xxstudy.expensetracker.constant

enum class ErrorCode(val code: Int, val message: String) {
    ERROR(-1, "服务器无法处理此请求"),
    LOGIN_ERROR(-10001, "用户名密码错误"),
    REGISTER_ERROR(-10002, "注册邮箱已存在"),
    RECORD_ERROR(-10003, "保存失败"),
    FORMAT_ERROR(-10004, "格式错误"),
    TOKEN_ERROR(-10005, "Token验证失败"),
    TOKEN_INVALID(-10006, "缺少Authorization请求头"),
    REFRESH_TOKEN_ERROR(-10007, "请重新登陆"),
    ID_ERROR(-10008, "ID不存在"),
    CATEGORY_ERROR(-10009, "分类已存在"),
    MEMBER_ERROR(-10010, "成员已存在"),
    MAIL_ERROR(-10011, "邮件发送失败，请检查邮箱地址是否正确"),
}