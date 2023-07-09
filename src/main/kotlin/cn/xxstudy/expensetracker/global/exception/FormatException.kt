package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.ErrorCode

/**
 * @date: 2023/7/2 12:44
 * @author: LovelyCoder
 * @remark:
 */
class FormatException : AppException(ErrorCode.FORMAT_ERROR.code, ErrorCode.FORMAT_ERROR.message)