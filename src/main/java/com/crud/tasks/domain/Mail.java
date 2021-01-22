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
//package com.crud.tasks.domain;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//
//@Getter
//@AllArgsConstructor
//public class Mail {
//    private final String mailTo;
//    private final String subject;
//    private final String message;
//    private final String toCc;
//}