package cn.xxstudy.expensetracker.data;

import cn.xxstudy.expensetracker.constant.HttpCode;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;

public class Response<T> {
    private int code = HttpStatus.OK.value();

    private String msg = "success";

    @Nullable
    private final T data;

    public Response(int code, String msg, @Nullable T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<T>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    public static <T> Response<T> failed(int code, String msg) {
        return new Response<>(code, msg, null);
    }

    public static <T> Response<T> failed(HttpCode httpCode) {
        return new Response<>(httpCode.getCode(), httpCode.getMessage(), null);
    }


    public static <T> Response<T> failed() {
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Nullable
    public T getData() {
        return data;
    }
}
