package cn.xxstudy.expensetracker.global.exception

import cn.xxstudy.expensetracker.constant.HttpCode
import cn.xxstudy.expensetracker.data.Response
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun <T>  exceptionHandler(request: HttpServletRequest, response: HttpServletResponse, exception: Exception): Response<T> {
        if (exception is AppException) {
            return Response.failed(exception.code, exception.msg)
        }
        if(exception is ExpiredJwtException){
            response.status=HttpStatus.UNAUTHORIZED.value()
            return Response.failed(HttpCode.TOKEN_ERROR.code, HttpCode.TOKEN_ERROR.message)
        }
        return Response.failed(-1, exception.message+","+exception.toString()+"->"+exception.cause.toString())
    }

    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    fun <T> handleMethodNotAllowed(): Response<T> {
        return Response.failed(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase)
    }


}