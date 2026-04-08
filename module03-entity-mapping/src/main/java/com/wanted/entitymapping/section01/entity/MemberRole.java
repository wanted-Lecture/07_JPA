package com.wanted.entitymapping.section01.entity;

/* COMMENT.
*   Enum 타입 선언
*   - Enum 클래스는 Java의 타입 안정성을 제공하며,
*   - 제한 된 값만 사용하기 위해 강제화 된 클래스이다.
* */
public enum MemberRole {

    ROLE_MEMBER("회원"),
    ROLE_ADMIN("관리자");

    private final String description;

    MemberRole(String description) {
        this.description = description;
    }
}
