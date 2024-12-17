package com.mdd.ela.util.constants;

import java.util.List;

public class Constants {
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int EXCEL_DEFAULT_FONT_SIZE = 12;
    public static final String BEARER = "Bearer ";
    public static final String ACCEPT = "accept";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String UNKNOWN = "unknown";



    private Constants() {}

    public static final String PAYLOAD_TOKEN_USER_ID = "user_id";
    public static final String PAYLOAD_TOKEN_EXPIRATION_NAME = "expire";
    public static final String PAYLOAD_TOKEN_USER_IS_ACTIVE = "is_active";
    public static final String PAYLOAD_TOKEN_USER_EMAIL = "email";
    public static final String PAYLOAD_TOKEN_FIRST_NAME = "first_name";
    public static final String PAYLOAD_TOKEN_LAST_NAME = "last_name";
    public static final String PAYLOAD_TOKEN_USERNAME = "username";
    public static final String PAYLOAD_TOKEN_PHONE_NUMBER = "phone_number";
    public static final String PAYLOAD_TOKEN_ID = "token_id";
    public static final String KEY_ENCRYPT = "emNvYSBzZXJ2aWNlc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS";



    public static class PackagePrice {
        private PackagePrice() {}

        public static final int INCLUDED_IN_PRICE = 1;

        public static int ADDITION_TO_PRICE = 2;

        public static String getName(int price) {
            if (price == INCLUDED_IN_PRICE)
                return "Đã bao gồm mã giá";

            return "Cộng thêm vào mã giá";
        }
    }

    public static class PackageFrequencyUse {
        private PackageFrequencyUse() {}

        public static final int EVERY_NIGHT = 1;

        public static final int EVERY_DAY = 2;

        public static final int EXCEPT_FIRST_DAY = 3;

        public static final int ONLY_FIRST_DAY = 4;

        public static final int ONLY_LAST_DAY = 5;

        public static String getName(int frequencyUse) {
            return switch (frequencyUse) {
                case EVERY_NIGHT -> "[[frequency_use_every_night]]"; // Thêm giao dịch hàng đêm
                case EVERY_DAY -> "[[frequency_use_every_day]]"; // Thêm giao dịch hàng ngày
                case EXCEPT_FIRST_DAY -> "[[frequency_use_except_first_day]]"; // Thêm giao dịch vào các ngày trừ ngày đầu
                case ONLY_FIRST_DAY -> "[[frequency_use_only_first_day]]"; // Thêm giao dịch vào ngày đầu tiên
                default -> "[[frequency_use_only_last_day]]"; // Thêm giao dịch vào ngày cuối cùng
            };
        }
    }

    public static class PackageBillingRule {
        private PackageBillingRule() {}

        public static final int FIX_PRICE = 1;

        public static final int BOTH_ADULT_AND_CHILDREN = 2;

        public static final int ONLY_ADULT = 3;

        public static final int ONLY_CHILDREN = 4;

        public static String getName(int billingRule) {
            return switch (billingRule) {
                case FIX_PRICE -> "[[billing_rule_fix_price]]"; // Giá cố định
                case BOTH_ADULT_AND_CHILDREN -> "[[billing_rule_adult_and_children]]"; // Người lớn + trẻ em
                case ONLY_ADULT -> "[[billing_rule_adult]]";
                default -> "[[billing_rule_children]]"; // Trẻ em
            };
        }
    }

    public static class PackageDisplayInPrice {
        private PackageDisplayInPrice() {
        }

        public static final int NONE = 0;

        public static final int IN_SEPARATE_LINE = 1;

        public static final int IN_ROOM_PRICE_LINE = 2;

        public static String getName(int displayInPrice) {
            return displayInPrice == IN_SEPARATE_LINE ? "[[display_in_price_separate_line]]" : "[[display_in_price_in_room_price_line]]"; // Dòng riêng biệt : Chung với dòng tiền phòng
        }
    }

    public static class TransactionGroup {
        private TransactionGroup() {}

        public static final int REVENUE = 1;

        public static final int PAYMENT = 2;

        public static List<Integer> getAll() {
            return List.of(REVENUE, PAYMENT);
        }

        public static String getName(int templateParameterType) {
            return templateParameterType == REVENUE ? "Doanh thu" : "Thanh toán";
        }
    }

    public static class TransactionType {
        private TransactionType() {}

        public static final int RENT_ROOM = 1;

        public static final int FOOD_AND_DRINK = 2;

        public static final int SPA = 3;

        public static final int KARAOKE = 4;

        public static final int SHOP = 5;

        public static final int TELEPHONE = 6;

        public static final int MINIBAR = 7;

        public static final int TAX = 8;

        public static final int NOT_REVENUE = 9;

        public static final int OTHER = 10;

        public static final int TRANSFER = 11;

        public static String getTransactionTypeName(long transactionType) {
            return switch ((int) transactionType) {
                case RENT_ROOM -> "Thuê phòng"; // [[transaction_type_lodging]]
                case FOOD_AND_DRINK -> "Đồ ăn và đồ uống"; // [[transaction_type_food_and_beverage]]
                case SPA -> "Spa"; //[[transaction_type_spa]]
                case KARAOKE -> "Karaoke"; // [[transaction_type_karaoke]]
                case SHOP -> "Shop"; // [[transaction_type_shop]]
                case TELEPHONE -> "Điện thoại"; // [[transaction_type_telephone]]
                case MINIBAR -> "Minibar"; // [[transaction_type_minibar]]
                case TAX -> "Thuế"; // [[transaction_type_tax]]
                case NOT_REVENUE -> "Không là doanh thu"; // [[transaction_type_non_revenue]]
                case OTHER -> "Khác"; // [transaction_type_other]
                case TRANSFER -> "Chuyển"; // [[transaction_type_transfer]]
                default -> "";
            };
        }
    }

    public static class PackageLogObject {
        private PackageLogObject() {}

        public static final int PACKAGE_CATEGORY = 1;

        public static final int PACKAGE_GROUP = 2;

        public static final int PACKAGE = 3;

        public static final int PACKAGE_DETAIL = 4;
    }

    public static class PackageLogAction {
        private PackageLogAction() {}

        public static final int ADD = 1;

        public static final int UPDATE = 2;

        public static final int DELETE = 3;

        public static final int STOP_SELLING = 4;
    }

    public static class TemplateParameterType {
        private TemplateParameterType() {}

        public static final int HOTEL_INFO = 1;

        public static final int SPECIAL_SERVICE = 2;

        public static final int BOOKING_INFO = 3;

        public static final int PAYMENT = 4;

        public static final int PAYMENT_DISCOUNT_LIST = 5;

        public static List<Integer> getAll() {
            return List.of(HOTEL_INFO, SPECIAL_SERVICE, BOOKING_INFO, PAYMENT, PAYMENT_DISCOUNT_LIST);
        }

        public static String getName(int templateParameterType) {
            return switch (templateParameterType) {
                case HOTEL_INFO -> "Thông tin khách sạn";
                case SPECIAL_SERVICE -> "Dịch vụ đặc biệt";
                case BOOKING_INFO -> "Thông tin đặt phòng";
                case PAYMENT -> "Thanh toán";
                default -> "Danh sách giảm giá thanh toán";
            };
        }
    }

    public static class TemplateLayout {
        private TemplateLayout() {}

        public static final int PORTRAIT = 1;

        public static final int LANDSCAPE = 2;
    }

    public static class AssignBusinessDistrictType {
        private AssignBusinessDistrictType() {}

        public static final int DEFAULT = 1;

        public static final int ROOM = 2;

        public static final int SERVICE = 3;

        public static String getName(int templateParameterType) {
            return switch (templateParameterType) {
                case DEFAULT -> "Giao dịch mặc định";
                case ROOM -> "Cấu hình giao dịch cho tiền phòng/bàn";
                default -> "Cấu hình giao dịch cho sản phẩm/dịch vụ";
            };
        }
    }

    //Exchange Ticket
    public static final String THANG_MAY_DOI = "THANGMAY_FREE";

    public static final String ZIPLINE_TICKET = "ZIPLINE";
    public static final String ZIPLINE_POINT = "ZIPLINE";
}
