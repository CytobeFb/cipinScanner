package com.cy.cipinscanner.utils.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * permissionUtils
 */

public class PerUtils {

    public static Intent openApplicationSettings(String packageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            return intent;
        }
        return new Intent();
    }

    public static void openApplicationSettings(Context context, String packageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            Intent intent = openApplicationSettings(packageName);
            context.startActivity(intent);
        }
    }
}
