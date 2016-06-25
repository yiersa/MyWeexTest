package com.tools.taojike.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ScreenUtil {

	private static final String TAG = "WXTBUtil";

	private static boolean isSupportSmartBar = false;
	private static boolean isSupportNavigationBar = false;

	static {
		isSupportSmartBar = isSupportSmartBar();
		isSupportNavigationBar = isSupportNavigationBar();
	}

	public static int getDisplayWidth(AppCompatActivity activity) {
		int width = 0;
		if (activity != null && activity.getWindowManager() != null && activity.getWindowManager().getDefaultDisplay() != null) {
			Point point = new Point();
			activity.getWindowManager().getDefaultDisplay().getSize(point);
			width = point.x;
		}
		return width;
	}

	public static int getDisplayHeight(AppCompatActivity activity) {
		int height = 0;
		if (activity != null && activity.getWindowManager() != null && activity.getWindowManager().getDefaultDisplay() != null) {
			Point point = new Point();
			activity.getWindowManager().getDefaultDisplay().getSize(point);
			height = point.y;
		}

		Log.e(TAG, "isSupportSmartBar:" + isSupportSmartBar);
		Log.e(TAG, "isSupportNavigationBar:" + isSupportNavigationBar);

		if (isSupportSmartBar) {
			int smartBarHeight = getSmartBarHeight(activity);
			Log.e(TAG, "smartBarHeight:" + smartBarHeight);
			height -= smartBarHeight;
		}

		if (isSupportNavigationBar) {
			int navigationBarHeight = getNavigationBarHeight(activity);
			Log.e(TAG, "navigationBarHeight:" + navigationBarHeight);
			height -= navigationBarHeight;
		}

		if (activity != null && activity.getSupportActionBar() != null) {
			int actionbar = activity.getSupportActionBar().getHeight();
			if (actionbar == 0) {
				TypedArray actionbarSizeTypedArray = activity.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
				actionbar = (int) actionbarSizeTypedArray.getDimension(0, 0);
			}
			Log.d(TAG, "actionbar:" + actionbar);
			height -= actionbar;
		}
		int status = getStatusBarHeight(activity);
		Log.d(TAG, "status:" + status);
		height -= status;

		Log.d(TAG, "height:" + height);
		return height;
	}

	private static int getStatusBarHeight(AppCompatActivity activity) {
		Resources resources = activity.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		int height = resources.getDimensionPixelSize(resourceId);
		Log.v(TAG, "Status height:" + height);
		return height;
	}

	private static int getNavigationBarHeight(AppCompatActivity activity) {
		Resources resources = activity.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		return resources.getDimensionPixelSize(resourceId);
	}

	private static int getSmartBarHeight(AppCompatActivity activity) {
		ActionBar actionbar = activity.getSupportActionBar();
		if (actionbar != null) {
			try {
				Class c = Class.forName("com.android.internal.R$dimen");
				Object obj = c.newInstance();
				Field field = c.getField("mz_action_button_min_height");
				int height = Integer.parseInt(field.get(obj).toString());
				return activity.getResources().getDimensionPixelSize(height);
			} catch (Exception e) {
				e.printStackTrace();
				actionbar.getHeight();
			}
		}
		return 0;
	}

	public static boolean isSupportNavigationBar() {
		Context context = MyApplication.mContext;
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception ignored) {
		}
		return hasNavigationBar;
	}

	private static boolean isSupportSmartBar() {
		try {
			final Method method = Build.class.getMethod("hasSmartBar");
			return method != null;
		} catch (final Exception e) {
			return false;
		}
	}

}
