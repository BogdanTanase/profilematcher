package com.gameloft.profilematcher.model.campaign;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public record Matcher(
        Level level,
        @JsonProperty("has")Map<String, List<String>> has,
        @JsonProperty("does_not_have")Map<String, List<String>> doesNotHave
) {
}
