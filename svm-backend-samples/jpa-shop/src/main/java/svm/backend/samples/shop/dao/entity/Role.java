package svm.backend.samples.shop.dao.entity;

import svm.backend.data.annotation.Predefined;

public class Role extends svm.backend.data.jpa.security.dao.entity.Role {
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final @Predefined Role MANAGER = predefined(Role.class, ROLE_MANAGER);
}
