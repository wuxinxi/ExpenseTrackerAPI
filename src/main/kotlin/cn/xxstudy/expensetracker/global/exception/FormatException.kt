package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.HttpCode

/**
 * @date: 2023/7/2 12:44
 * @author: LovelyCoder
 * @remark:
 */
class FormatException : AppException(HttpCode.FORMAT_ERROR.code, HttpCode.FORMAT_ERROR.message)