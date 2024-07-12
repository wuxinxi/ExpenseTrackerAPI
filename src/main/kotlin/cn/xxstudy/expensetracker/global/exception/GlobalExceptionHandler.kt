package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.ErrorCode
import cn.xxstudy.expensetracker.data.Response
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.mail.MailSendException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException


@ControllerAdvice
class GlobalExceptionHandler {


//    @ExceptionHandler(AppException::class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    fun <T> handleAppException(e: AppException): Response<T> {
//        return Response.failed(e.code, e.msg)
//    }
//
//    @ExceptionHandler(ExpiredJwtException::class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ResponseBody
//    fun <T> handleExpiredJwtException(e: ExpiredJwtException): Response<T> {
//        return Response.failed(ErrorCode.TOKEN_ERROR);
//    }
//
//    @ExceptionHandler(MailSendException::class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    fun <T> handleMailSendException(e: MailSendException): Response<T> {
//        return Response.failed(ErrorCode.MAIL_ERROR)
//    }
//
//    @ExceptionHandler(Exception::class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ResponseBody
//    fun <T>handleException(e: Exception): Response<T> {
//        return Response.failed(ErrorCode.INTERNAL_SERVER_ERROR.code, ErrorCode.INTERNAL_SERVER_ERROR.msg)
//    }


    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun <T> exceptionHandler(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: Exception
    ): Response<T> {
        response.status = HttpStatus.BAD_REQUEST.value()
        if (exception is AppException) {
            return Response.failed(exception.code, exception.msg)
        }
        if (exception is ExpiredJwtException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return Response.failed(ErrorCode.TOKEN_ERROR)
        }
        if (exception is MailSendException) {
            return Response.failed(ErrorCode.MAIL_ERROR)
        }
        if (exception is ConstraintViolationException) {
            return Response.failed(-2, exception.constraintViolations.first().message.toString())
        }
        return Response.failed(-1, exception.message + "," + exception.toString() + "->" + exception.javaClass.name)
    }

    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun <T> handleMethodNotAllowed(): Response<T> {
        return Response.failed(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase)
    }

    @ExceptionHandler(BindException::class)
    fun <T> handleValidException(e: BindException): Response<T> {
        return Response.failed(-1, e.bindingResult.allErrors[0].defaultMessage.toString())
    }

    @ResponseBody
    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun <T> handleValidException(e: MethodArgumentNotValidException): Response<T> {
        return Response.failed(-1, getCommonResult(e)?:"error")
    }

    private fun getCommonResult(e: BindException): String? {
        val bindingResult = e.bindingResult
        var message:String? = null
        if (bindingResult.hasErrors()) {
            val fieldError = bindingResult.fieldError
            if (fieldError != null) {
                message = fieldError.defaultMessage
            }
        }
        return message
    }

}