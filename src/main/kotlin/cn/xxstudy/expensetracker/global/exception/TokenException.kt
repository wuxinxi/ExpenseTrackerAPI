package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.ErrorCode

/**
 * @date: 2023/7/3 21:40
 * @author: LovelyCoder
 * @remark:
 */
class TokenException(code: Int = ErrorCode.TOKEN_ERROR.code, message: String = ErrorCode.TOKEN_ERROR.message) :
    AppException(code, message)