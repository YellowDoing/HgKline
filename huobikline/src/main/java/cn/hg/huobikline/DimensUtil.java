package cn.hg.huobikline;

import android.content.Context;

class DimensUtil {


    /**
     * px转换成dp
     */
     static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
