package com.cyou.mobopick.util;

public class Constant {

    public static final String KEY_RESULT = "key_result";

    public static final class INTENT_KEY {

        public static final String APPMODEL = "intent_key_appmodel";
        public static final String IMAGE_ID = "intent_key_image_id";
        public static final String DES_ID = "intent_key_des_id";
        public static final String IS_LAST = "intent_key_is_last";
        public static final String VERSION = "intent_key_version";
        public static final String ID = "intent_key_id";
        public static final String IMAGE_URL = "intent_key_image_url";
        public static final String TEXT = "intent_key_text";
        public static final String IMAGE_TEXT = "intent_key_image_text";
        public static final String IMAGE_TEXT_LIST = "intent_key_image_text_list";
        public static final String POSITION = "intent_key_position";

    }

    public static final String SCHEMA = "http://";
    public static final String HOST = "www.mobo123.com";

    public static String getBaseUrl() {
        return new StringBuilder(SCHEMA).append(HOST).append("/").toString();
    }

}
