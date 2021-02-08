package com.cy.cipinscanner.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.cy.cipinscanner.app.CipinScannerApp;


/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/4/26 16:03
 * @Version:1.0
 * @Description: toast单利模式。
 */

public enum ToastUtil 
{
    INSTANCE;
    private Toast mToast;
    private static Toast toast;

    private static Toast initToast(CharSequence message, int duration) {
        if (toast == null) {
            toast = Toast.makeText(CipinScannerApp.getAppContext(), message, duration);
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return toast;
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (TextUtils.isEmpty(message)){
            return;
        }
        initToast(message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (TextUtils.isEmpty(message)){
            return;
        }
        initToast(message, Toast.LENGTH_LONG).show();
    }


    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
//		Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
        initToast(CipinScannerApp.getAppContext().getResources().getText(strResId), Toast.LENGTH_SHORT).show();
    }

    public  void toastBottom(Context context, String message) 
    {
      showApprovalSuccess(context,message,Toast.LENGTH_SHORT);
    }

    public  void ToastLongMessage(Context context, String message)
    {
        showApprovalSuccess(context,message,Toast.LENGTH_LONG);
    }

    public  void ToastFailureMessage(Context context)
    {
        showApprovalSuccess(context,"提交失败，请重试!",Toast.LENGTH_SHORT);
    }

    public void cancelToast()
    {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public  void ToastTopMsg(Context context, String message)
    {
        if (context == null || TextUtils.isEmpty(message)) 
        {
            return;
        }

        if (mToast==null)
        {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 200);
            mToast.show();
        }else
        {
            mToast.setText(message);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 200);
            mToast.show();
        }
    }

    
    private  void showApprovalSuccess(Context context, String message, int duration)
    {
        if (context == null || TextUtils.isEmpty(message)) {
            return;
        }
        if (mToast==null)
        {
            mToast = Toast.makeText(context, message, duration);
        }else
        {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

   /* public  void showApprovalSuccess(Context context,String message)
    {
        if (context == null )
            return;
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.totast_approval_success, null);
        TextView tv_message = (TextView) view.findViewById(R.id.tv_approve_message);
        tv_message.setText(message);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }*/
}
