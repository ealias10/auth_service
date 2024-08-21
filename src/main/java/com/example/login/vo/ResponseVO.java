package com.example.login.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseVO<T> {
    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("total_count")
    private long totalCount;

    @JsonInclude(Include.NON_NULL)
    @JsonProperty("error")
    private ErrorVO error;

    public ResponseVO() {
        super();
        this.data = new ArrayList<>();
        this.status = 200;
        this.message = "success";
    }
    public void addData(T data) {
        if (data != null) {
            this.data.add(data);
        }
    }
}