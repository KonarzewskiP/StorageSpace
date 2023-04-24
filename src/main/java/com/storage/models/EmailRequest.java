package com.storage.models;


public record EmailRequest(String to, String recipientName, String subject,
                           String content) {
}
