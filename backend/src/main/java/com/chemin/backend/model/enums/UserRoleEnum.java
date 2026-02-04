package com.chemin.backend.model.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER("用戶", "user"),
    ADMIN("管理員", "admin");

    private final String text;
    private final String value;

    UserRoleEnum(String text, String value){
        this.text = text;
        this.value = value;
    }

    public static UserRoleEnum getEnumByValue(String value){
        if(value == null || value.isEmpty()){
            return null;
        }
        for(UserRoleEnum role : UserRoleEnum.values()){
            if(role.value.equals(value)){
                return role;
            }
        }
        return null;
    }
}
