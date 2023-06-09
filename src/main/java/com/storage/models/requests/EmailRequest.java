package com.storage.models.requests;


public record EmailRequest(String to, String recipientName, String subject,
                           String content) {
}
