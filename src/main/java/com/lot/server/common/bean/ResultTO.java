package com.lot.server.common.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Result")
public class ResultTO<T> implements Serializable {
    @Schema(description = "Response Code")
    private Integer code;
    @Schema(description = "error message")
    private String msg;
    @Schema(description = "response data")
    private T data;

    public ResultTO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultTO<T> success(T data) {
        return new ResultTO<>(200, "Success", data);
    }


    public static <T> ResultTO<T> error(String msg) {
        return new ResultTO<>(400, msg, null);
    }

    public static <T> ResultTO<T> error(int code, String msg) {
        return new ResultTO<>(code, msg, null);
    }
}
