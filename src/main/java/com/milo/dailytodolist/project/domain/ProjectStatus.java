package com.milo.dailytodolist.project.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public enum ProjectStatus {
    SUBMITTED,
    STARTED,
    COMPLETED,
    ON_HOLD;

    public static Optional<ProjectStatus> parseString(String value) {
        return Arrays.stream(values())
                .filter(status -> StringUtils.equalsIgnoreCase(status.name(), value))
                .findFirst();
    }
}
