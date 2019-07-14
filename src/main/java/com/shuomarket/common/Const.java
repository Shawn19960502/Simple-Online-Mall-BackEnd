package com.shuomarket.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * created by shawn
 */
public class Const {
    public static final String CURRENT_USER="currentUser";

    public static final String EMAIL="email";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public static final String USERNAME="username";
    public interface Role {
        int ROLE_CUSMTER = 0; // normal user.
        int ROLE_ADMIN = 1; // Administrator.
    }
    public enum ProductStatusEnum{
        ON_SALE(1,"On Sale");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
