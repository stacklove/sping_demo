package com.rzx.test.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OrgDept {

    @NonNull
    private String name;
    @NonNull
    private String code;
}
