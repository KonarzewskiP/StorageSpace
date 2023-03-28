package com.storage.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailRequest {
    String to;
    String recipientName;
    String subject;
    String content;
}
