package com.binodnme.epl.utils;

import android.content.Context;

/**
 * Created by binodnme on 2/23/16.
 */
public class DrawableUtils {

    public static int getDrawableResourceId(Context context, String name){
        name = name.toLowerCase().replace(' ','_');
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }
}
