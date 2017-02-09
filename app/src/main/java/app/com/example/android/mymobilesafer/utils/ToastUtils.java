package app.com.example.android.mymobilesafer.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by FJTK on 2017/2/9.
 */

public class ToastUtils {
    public static void showToast(Context context,String msg){
        Toast toast = null;
        if (toast == null) {
            toast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}
