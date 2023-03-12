package com.storage.models.dto.postcode;

import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PostcodeDTO extends PostcodeResponse {
    private PostcodeDetails result;
}
