package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.HttpCode

/**
 * @date: 2023/7/3 21:40
 * @author: LovelyCoder
 * @remark:
 */
class TokenException :AppException(HttpCode.TOKEN_ERROR.code,HttpCode.TOKEN_ERROR.message)