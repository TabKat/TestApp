package com.lv.demo.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorMessage {
    LocalDateTime timeStamp;
    Integer error;
    String message;
    String path;
}
