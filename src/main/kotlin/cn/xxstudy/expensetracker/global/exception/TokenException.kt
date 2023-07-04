package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.HttpCode

/**
 * @date: 2023/7/3 21:40
 * @author: LovelyCoder
 * @remark:
 */
class TokenException(code: Int = HttpCode.TOKEN_ERROR.code, message: String = HttpCode.TOKEN_ERROR.message) :
    AppException(code, message)