package com.joker.jokerlibrary.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Joker on 2016/4/21.
 */
public class InputToggleUtils
{
    public static void toggleInput(Context context)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
