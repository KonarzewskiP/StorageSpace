package com.storage.models;


import com.storage.models.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class QuoteResponse {

     String email;
     DeliveryStatus status;
}

