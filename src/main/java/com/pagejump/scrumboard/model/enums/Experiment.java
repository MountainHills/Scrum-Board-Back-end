package com.pagejump.scrumboard.model.enums;

import org.apache.commons.lang3.EnumUtils;

public class Experiment {
    public static void main(String[] args) {
        String todo = "TODO ";

        System.out.println(
                EnumUtils.isValidEnum(TaskStatus.class, todo)
        );
    }
}
