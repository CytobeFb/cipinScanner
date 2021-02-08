package com.cy.cipinscanner.utils.permission;

import java.util.ArrayList;

/**
 */

public interface PerimissionsCallback {

    void onGranted(ArrayList<PermissionEnum> grantedList);

    void onDenied(ArrayList<PermissionEnum> deniedList);
}
