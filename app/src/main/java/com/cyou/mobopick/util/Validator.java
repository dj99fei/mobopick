
package com.cyou.mobopick.util;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;


/**
 * This class is for form validation.It contains some common method to test if
 * the input data from user is valid
 * 
 * @author chengfei
 */
public class Validator {

    private MyApplication mApp;

    public static final int USERNAME_LENGTH_MAX = 20;
    public static final int USERNAME_LENGTH_MIN = 3;

    public Validator() {
        this.mApp = MyApplication.getInstance();
    }

    /**
     * To test if the data is not empty.
     * 
     * @param data The data to be test.
     * @param resId The resId of the prompt when the test has not pass.
     * @return The validator itself.
     * @throws ValidateException If the data is empty a new ValidateException
     *             will be throw.
     */
    public Validator notEmpty(String data, int resId) throws ValidateException {
        if ("".equals(data)) {
            throw new ValidateException(mApp.getString(
                    R.string.verify_not_empty, mApp.getString(resId)));
        }
        return this;
    }

    /**
     * To test if the data's length between min and max.
     * 
     * @param data The data to be test.
     * @param min The minimum length the data could be.
     * @param max The maximum length the data could be.
     * @param resId A custom string explaining what data is beyond the give range.
     * @return The validator itself.
     * @throws ValidateException If the data'length doesn't between the given
     *             range,a new ValidateException will be throw.
     */
    public Validator isLengthValid(String data, int min, int max, int resId)
            throws ValidateException {
        if (data != null && (data.length() < min || data.length() > max)) {
            throw new ValidateException(mApp.getString(
                    R.string.verify_length, mApp.getString(resId).toLowerCase(), min,
                    max));
        }
        return this;
    }

    /**
     * This class is for validation. If any test condition is not met,this
     * exception will be throw.
     * 
     * @author chengfei
     */
    public static class ValidateException extends Exception {

        private static final long serialVersionUID = -7281950545855178907L;

        public ValidateException(String msg) {
            super(msg);
        }

        public ValidateException(int res) {
            super(MyApplication.getInstance().getResources().getString(res));
        }

    }

}
