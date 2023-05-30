package com.storage.models.requests;

import lombok.NonNull;

public record UserAuthenticationRequests(
        @NonNull
        String emailAddress,
        @NonNull
        String password
) {
}
