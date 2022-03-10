package com.example.parsing.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }

    @ApiModel(value = "에러", description = "공통 에러 정보")
    @Getter @ToString
    public static class ApiError {
        @ApiModelProperty(value = "에러 메시지")
        private final String message;
        @ApiModelProperty(value = "http 상태코드")
        private final int status;

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }
    }

    @ApiModel(value = "API 결과 공통", description = "API 결과 공통포맷, 실제 결과 데이터는 response에 응답")
    @Getter @AllArgsConstructor
    public static class ApiResult<T> {
        @ApiModelProperty(value = "정상: true, 에러: false")
        private final boolean success;
        @ApiModelProperty(value = "정상: json, 에러: null")
        private final T response;
        @ApiModelProperty(value = "정상: null, 에러: json")
        private final ApiError error;
    }

}