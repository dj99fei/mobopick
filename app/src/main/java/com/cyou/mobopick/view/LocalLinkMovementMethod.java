
package com.cyou.mobopick.view;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class LocalLinkMovementMethod extends LinkMovementMethod
{
    static LocalLinkMovementMethod sInstance;
    private OnLinkClickListener mListener;

    public LocalLinkMovementMethod()
    {
    }

    public LocalLinkMovementMethod(OnLinkClickListener paramOnLinkClickListener)
    {
        this.mListener = paramOnLinkClickListener;
    }

    public static LocalLinkMovementMethod getInstance()
    {
        if (sInstance == null)
            sInstance = new LocalLinkMovementMethod();
        return sInstance;
    }

    public boolean onTouchEvent(TextView paramTextView, Spannable paramSpannable,
            MotionEvent paramMotionEvent)
    {
        int i = paramMotionEvent.getAction();
        ClickableSpan[] arrayOfClickableSpan = null;
        if ((i == 1) || (i == 0))
        {
            int j = (int) paramMotionEvent.getX();
            int k = (int) paramMotionEvent.getY();
            int m = j - paramTextView.getTotalPaddingLeft();
            int n = k - paramTextView.getTotalPaddingTop();
            int i1 = m + paramTextView.getScrollX();
            int i2 = n + paramTextView.getScrollY();
            Layout localLayout = paramTextView.getLayout();
            int i3 = localLayout.getOffsetForHorizontal(localLayout.getLineForVertical(i2), i1);
            arrayOfClickableSpan = (ClickableSpan[]) paramSpannable.getSpans(i3, i3,
                    ClickableSpan.class);
            if ((arrayOfClickableSpan.length != 0)
                    && ((i == 1) && ((this.mListener != null) && (this.mListener.onLinkClick(
                            paramTextView, arrayOfClickableSpan[0])))))
                ;
        }
//        try
//        {
//            arrayOfClickableSpan[0].onClick(paramTextView);
//            while (true)
//            {
//                label151: if ((paramTextView instanceof HtmlTextView))
//                    ((HtmlTextView) paramTextView).linkHit = true;
//                return true;
//                if (i == 0)
//                    Selection.setSelection(paramSpannable,
//                            paramSpannable.getSpanStart(arrayOfClickableSpan[0]),
//                            paramSpannable.getSpanEnd(arrayOfClickableSpan[0]));
//            }
//            Selection.removeSelection(paramSpannable);
//            Touch.onTouchEvent(paramTextView, paramSpannable, paramMotionEvent);
//            return false;
//            return Touch.onTouchEvent(paramTextView, paramSpannable, paramMotionEvent);
//        } catch (Exception localException)
//        {
//            break label151;
//        }
        return true;//add by chengfei
    }

    public static abstract interface OnLinkClickListener
    {
        public abstract boolean onLinkClick(TextView paramTextView, ClickableSpan paramClickableSpan);
    }
}
