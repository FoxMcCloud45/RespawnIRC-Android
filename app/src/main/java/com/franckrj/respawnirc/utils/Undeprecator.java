package com.franckrj.respawnirc.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import android.text.Html;
import android.text.Spanned;
import android.webkit.CookieManager;
import android.webkit.WebSettings;

public class Undeprecator {
    @ColorInt
    public static int resourcesGetColor(Resources resources, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= 23) {
            return resources.getColor(colorId, null);
        } else {
            //noinspection deprecation
            return resources.getColor(colorId);
        }
    }

    public static Drawable resourcesGetDrawable(Resources resources, @DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return resources.getDrawable(drawableId, null);
        } else {
            //noinspection deprecation
            return resources.getDrawable(drawableId);
        }
    }

    public static Spanned htmlFromHtml(String source) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            //noinspection deprecation
            return Html.fromHtml(source);
        }
    }

    public static Spanned htmlFromHtml(String source, Html.ImageGetter imageGetter, Html.TagHandler tagHandler) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY, imageGetter, tagHandler);
        } else {
            //noinspection deprecation
            return Html.fromHtml(source, imageGetter, tagHandler);
        }
    }

    public static void webSettingsSetSaveFormData(WebSettings settings, boolean newVal) {
        //noinspection deprecation
        settings.setSaveFormData(newVal);
    }

    public static void webSettingsSetSavePassword(WebSettings settings, boolean newVal) {
        //noinspection deprecation
        settings.setSavePassword(newVal);
    }

    public static void cookieManagerRemoveAllCookiesAndSetDefault(CookieManager manager) {
        if (Build.VERSION.SDK_INT >= 21) {
            manager.removeAllCookies(null);
        } else {
            //noinspection deprecation
            manager.removeAllCookie();
        }
        manager.setCookie("http://www.jeuxvideo.com", "euconsent=set");
    }

    public static void vibratorVibrate(Vibrator vibratorService, long[] pattern, int repeat) {
        if (Build.VERSION.SDK_INT >= 26) {
            vibratorService.vibrate(VibrationEffect.createWaveform(pattern, repeat));
        } else {
            //noinspection deprecation
            vibratorService.vibrate(pattern, repeat);
        }
    }
}