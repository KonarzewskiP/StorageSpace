package com.storage.model;


import com.storage.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@AllArgsConstructor
public class QuoteResponse {

     String firstName;
     String email;
     DeliveryStatus status;
}

