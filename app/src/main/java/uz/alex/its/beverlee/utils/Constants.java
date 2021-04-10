package uz.alex.its.beverlee.utils;

public class Constants {
    /* application constants */
    public static final String MD5 = "MD5";
    public static final String BEARER_TOKEN = "bearer_token";
    public static final String FCM_TOKEN = "fcm_token";

    /* user constants */
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MIDDLE_NAME = "middle_name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String COUNTRY_ID = "country_id";
    public static final String CITY = "city";
    public static final String ADDRESS = "address";
    public static final String POSITION = "position";

    /* authorization constants */
    public static final String PASSWORD = "password";
    public static final String PASSWORD_CONFIRMATION = "password_confirmation";
    public static final String PINCODE = "pin";
    public static final String CODE = "code";

    /* auth & registration checkers */
    public static final String PHONE_VERIFIED = "phone_verified";
    public static final String PIN_ASSIGNED = "pin_assigned";
    public static final String FINGERPRINT_ON = "fingerprint_on";

    /* intent data */
    public static final int INTENT_PICK_IMAGE = 0x01;

    /* permission data */
    public static final int REQUEST_CODE_READ_CONTACTS = 0x101;
    public static final int REQUEST_CODE_PROVIDE_AUTHENTICATOR = 0x102;

    /* local storage */
    public static final String DATABASE_NAME = "beverlee_local_database.db";
    public static final String SHARED_PREFS_NAME = "beverlee_shared_prefs";

    /* push */
    public static final String PACKAGE_NAME = "package_name";
    public static final String PUSH_INTENT = "push_intent";
    public static final String PUSH = "push";
    public static final String PUSH_ID = "id";
    public static final String PUSH_TITLE = "title";
    public static final String PUSH_BODY = "body";

    /* push status */
    public static final String PUSH_STATUS = "push_status";
    public static final int NOT_DELIVERED = 0;
    public static final int DELIVERED = 1;
    public static final int READ = 2;

    /* notification channel */
    public static final String PUSH_CHANNEL_ID = "channel_id";
    public static final String DEFAULT_CHANNEL_ID = "101";
    public static final String NEWS_CHANNEL_ID = "102";
    public static final String BONUS_CHANNEL_ID = "103";
    public static final String INCOME_CHANNEL_ID = "104";
    public static final String PURCHASE_CHANNEL_ID = "105";
    public static final String REPLENISH_CHANNEL_ID = "106";
    public static final String WITHDRAWAL_CHANNEL_ID = "107";

    public static final String DEFAULT_CHANNEL_NAME = "default channel";
    public static final String NEWS_CHANNEL_NAME = "news_channel";
    public static final String BONUS_CHANNEL_NAME = "bonus_channel";
    public static final String INCOME_CHANNEL_NAME = "income_channel";
    public static final String PURCHASE_CHANNEL_NAME = "purchase_channel";
    public static final String REPLENISH_CHANNEL_NAME = "replenish_channel";
    public static final String WITHDRAWAL_CHANNEL_NAME = "withdrawal_channel";

    /* errors */
    public static final String REQUEST_ERROR = "request_error";
    public static final String UNKNOWN_ERROR = "Unknown error";
    public static final String NO_PIN = "no_pin";
    public static final String CURRENT_BALANCE = "current_balance";
}
