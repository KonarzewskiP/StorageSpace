package com.storage.model;


import com.storage.model.enums.*;
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

