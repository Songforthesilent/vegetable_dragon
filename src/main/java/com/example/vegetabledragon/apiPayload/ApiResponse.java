package com.example.vegetabledragon.apiPayload;
// 원래는 성공한 경우에도 해당 필드를 사용해야하나,
// 일단 실패한 경우만 처리하도록 함.

/*
* API Response
* {
* "isSuccess" : Boolean,
* "code" : HTTP 상태 코드 + 세부
* "message" : "어떤 결과인지 설명",
* "result" : 응답에 필요한 DTO
* }
*/

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final boolean isSuccess;
    private final String code;
    private final String message;
    private final T result;

    // 실패했을 경우
    public static <T> ApiResponse<T> onFailure(String code, String message, T data){
        return new ApiResponse<T>(false, code, message, data);
    }
}
