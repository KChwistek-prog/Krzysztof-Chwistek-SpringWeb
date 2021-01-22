package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Mail {
    String mailTo;
    String subject;
    String message;
    String toCc;
}