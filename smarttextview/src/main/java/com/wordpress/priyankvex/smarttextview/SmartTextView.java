package com.wordpress.priyankvex.smarttextview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by @priyankvex on 27/1/16.
 * SmartTextView class that is based on TextView.
 * It detects various patterns in text like phone numbers and emails etc
 * and sets clickable intents on them.
 */
public class SmartTextView extends TextView {

    private Context mContext;
    private String mEmailColorCode;
    private String mUrlColorCode;
    private String mPhoneNumberColorCode;
    private SmartTextCallback mSmartTextCallback;

    public SmartTextView(Context context){
        super(context);
        this.mContext = context;
    }

    public SmartTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mEmailColorCode = "#3344ff";
        this.mUrlColorCode = "#3344ff";
        this.mPhoneNumberColorCode = "#3344ff";
    }

    public void setText(String text){
        super.setText(text);
        Log.d("test", "Custom set text");

        SpannableString ss = new SpannableString(text);
        // Splitting the words by spaces
        String[] words = text.split(" ");
        for (String word : words){
            Log.d("test", word);
            word = word.replace("\n", "");
            if (word.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")){
                // the word is email. Set the email span
                int startIndex = text.indexOf(word);
                int endIndex = startIndex + word.length();
                final String finalWord = word;
                ClickableSpan emailClickSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (mSmartTextCallback == null){
                            Toast.makeText(mContext, "Email Clicked", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mSmartTextCallback.emailClick(finalWord);
                        }
                    }
                };
                ForegroundColorSpan emailColorSpan = new ForegroundColorSpan(Color.parseColor(mEmailColorCode));
                ss.setSpan(emailClickSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(emailColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Log.d("test", "Email matched" + startIndex + " : " + endIndex);
            }
            else if (word.matches("(http:\\/\\/|https:\\/\\/|www.).{3,}")){
                // word is a URL
                int startIndex = text.indexOf(word);
                int endIndex = startIndex + word.length();
                ClickableSpan urlClickSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(mContext, "Url Clicked", Toast.LENGTH_SHORT).show();
                    }
                };
                ForegroundColorSpan urlColorSpan = new ForegroundColorSpan(Color.parseColor(mUrlColorCode));
                ss.setSpan(urlClickSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(urlColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Log.d("test", "Url matched" + startIndex + " : " + endIndex);
            }
            else if (word.matches("\\b\\d{3}[-.]?\\d{3}[-.]?\\d{4}\\b")){
                // word is a phone number
                int startIndex = text.indexOf(word);
                int endIndex = startIndex + word.length();
                ClickableSpan numberClickSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(mContext, "Phone Number Clicked", Toast.LENGTH_SHORT).show();
                    }
                };
                ForegroundColorSpan numberColorSpan = new ForegroundColorSpan(Color.parseColor(mPhoneNumberColorCode));
                ss.setSpan(numberClickSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(numberColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                Log.d("test", "Phone Number matched" + startIndex + " : " + endIndex);
            }
        }

        super.setText(ss);
        super.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void setEmailColorCode(String emailColorCode) {
        this.mEmailColorCode = emailColorCode;
    }

    public void setUrlColorCode(String urlColorCode){
        this.mUrlColorCode = urlColorCode;
    }

    public void setPhoneNumberColorCode(String phoneNumberColorCode){
        this.mPhoneNumberColorCode = phoneNumberColorCode;
    }

    public void setmSmartTextCallback(SmartTextCallback mSmartTextCallback) {
        this.mSmartTextCallback = mSmartTextCallback;
    }
}
