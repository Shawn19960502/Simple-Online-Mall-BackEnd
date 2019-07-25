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

    public interface Cart{
        int CHECKED = 1;
        int UN_CHECKED = 0;

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
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

    public enum OrderStatusEnum{
        CANCELED(0,"Cancelled"),
        NO_PAY(10,"Not Payed"),
        PAID(20,"payed"),
        SHIPPED(40,"on the way"),
        ORDER_SUCCESS(50,"finish"),
        ORDER_CLOSE(60,"close");


        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("no corresponding enum");
        }
    }

    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"Only Pay");

        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }


        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("No corresponding enum");
        }
    }
}
