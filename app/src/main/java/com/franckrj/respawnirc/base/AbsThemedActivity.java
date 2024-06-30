package com.franckrj.respawnirc.base;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import com.franckrj.respawnirc.R;
import com.franckrj.respawnirc.utils.ThemeManager;

public abstract class AbsThemedActivity extends AbsBaseActivity {
    protected static ActivityManager.TaskDescription generalTaskDesc = null;
    protected static @ColorInt int colorUsedForGenerateTaskDesc = 0;
    protected ThemeManager.ThemeName lastThemeUsed = null;
    protected boolean toolbarTextColorIsInverted = false;
    protected @ColorInt int lastHeaderColorUsed = 0;
    protected int lastPrimaryColorUsed = -1;
    protected int lastTopicNameAndAccentColorUsed = -1;
    protected boolean statusBarNeedToBeTransparent = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ThemeManager.changeActivityTheme(this);
        ThemeManager.changeActivityToolbarTextColor(this);
        ThemeManager.changeActivityThemedColorsIfNeeded(this);
        lastThemeUsed = ThemeManager.getThemeUsed();
        toolbarTextColorIsInverted = ThemeManager.getToolbarTextColorIsInvertedForThemeLight();
        lastHeaderColorUsed = ThemeManager.getHeaderColorUsedForThemeLight();
        lastPrimaryColorUsed = ThemeManager.getPrimaryColorIdUsedForThemeLight();
        lastTopicNameAndAccentColorUsed = ThemeManager.getTopicNameAndAccentColorIdUsedForThemeLight();

        super.onCreate(savedInstanceState);

        if (generalTaskDesc == null || colorUsedForGenerateTaskDesc != ThemeManager.getColorInt(R.attr.colorPrimary, this)) {
            Bitmap appIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_rirc);
            colorUsedForGenerateTaskDesc = ThemeManager.getColorInt(R.attr.colorPrimary, this);
            generalTaskDesc = new ActivityManager.TaskDescription(getString(R.string.app_name), appIcon, colorUsedForGenerateTaskDesc);
        }
        setTaskDescription(generalTaskDesc);

        /* Pour corriger un bug de la SL 27.1.0. */
        if (statusBarNeedToBeTransparent) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((lastThemeUsed != ThemeManager.getThemeUsed()) || (lastHeaderColorUsed != ThemeManager.getHeaderColorUsedForThemeLight()) ||
                (lastPrimaryColorUsed != ThemeManager.getPrimaryColorIdUsedForThemeLight()) ||
                (lastTopicNameAndAccentColorUsed != ThemeManager.getTopicNameAndAccentColorIdUsedForThemeLight()) ||
                (toolbarTextColorIsInverted != ThemeManager.getToolbarTextColorIsInvertedForThemeLight())) {
            recreate();
        }
    }
}
