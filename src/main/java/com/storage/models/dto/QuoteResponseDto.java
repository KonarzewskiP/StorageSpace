package com.storage.models.dto;


import com.storage.models.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class QuoteResponseDto {

     String email;
     DeliveryStatus status;
}

