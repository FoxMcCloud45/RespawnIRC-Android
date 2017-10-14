package com.franckrj.respawnirc.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.SimpleArrayMap;

import com.franckrj.respawnirc.MainActivity;
import com.franckrj.respawnirc.R;
import com.franckrj.respawnirc.jvctopic.jvctopicviewers.AbsShowTopicFragment;

public class PrefsManager {
    private static SharedPreferences currentPrefs = null;
    private static SharedPreferences.Editor currentPrefsEdit = null;
    private static SimpleArrayMap<BoolPref.Names, BoolPref> listOfBoolPrefs = new SimpleArrayMap<>();
    private static SimpleArrayMap<IntPref.Names, IntPref> listOfIntPrefs = new SimpleArrayMap<>();
    private static SimpleArrayMap<StringPref.Names, StringPref> listOfStringPrefs = new SimpleArrayMap<>();
    private static SimpleArrayMap<LongPref.Names, LongPref> listOfLongPrefs = new SimpleArrayMap<>();

    private static void addBoolPref(BoolPref.Names nameOfPref, String prefStringValue, boolean prefDefautlValue) {
        listOfBoolPrefs.put(nameOfPref, new BoolPref(prefStringValue, prefDefautlValue));
    }

    private static void addIntPref(IntPref.Names nameOfPref, String prefStringValue, int prefDefautlValue) {
        listOfIntPrefs.put(nameOfPref, new IntPref(prefStringValue, prefDefautlValue));
    }

    private static void addStringPref(StringPref.Names nameOfPref, String prefStringValue, String prefDefautlValue) {
        listOfStringPrefs.put(nameOfPref, new StringPref(prefStringValue, prefDefautlValue));
    }

    private static void addStringPref(StringPref.Names nameOfPref, String prefStringValue, String prefDefautlValue, int newMinVal, int newMaxVal) {
        listOfStringPrefs.put(nameOfPref, new StringPref(prefStringValue, prefDefautlValue, newMinVal, newMaxVal));
    }

    private static void addLongPref(LongPref.Names nameOfPref, String prefStringValue, long prefDefautlValue) {
        listOfLongPrefs.put(nameOfPref, new LongPref(prefStringValue, prefDefautlValue));
    }

    @SuppressLint("CommitPrefEdits")
    public static void initializeSharedPrefs(Context currentContext) {
        currentPrefs = currentContext.getSharedPreferences(currentContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        currentPrefsEdit = currentPrefs.edit();

        addBoolPref(BoolPref.Names.IS_FIRST_LAUNCH, "pref.isFirstLaunch", true);
        addBoolPref(BoolPref.Names.USER_IS_MODO, "pref.userIsModo", false);
        addBoolPref(BoolPref.Names.WEBVIEW_CACHE_NEED_TO_BE_CLEAR, "pref.webviewCacheNeedToBeClear", false);

        addIntPref(IntPref.Names.LAST_ACTIVITY_VIEWED, "pref.lastActivityViewed", MainActivity.ACTIVITY_SELECT_FORUM_IN_LIST);
        addIntPref(IntPref.Names.CURRENT_TOPIC_MODE, "pref.currentTopicMode", AbsShowTopicFragment.MODE_FORUM);
        addIntPref(IntPref.Names.FORUM_FAV_ARRAY_SIZE, "pref.forumFavArraySize", 0);
        addIntPref(IntPref.Names.TOPIC_FAV_ARRAY_SIZE, "pref.topicFavArraySize", 0);
        addIntPref(IntPref.Names.LAST_ROW_SELECTED_INSERTSTUFF, "pref.lastRowSelecetdInsertstuff", 1);

        addStringPref(StringPref.Names.PSEUDO_OF_USER, "pref.pseudoUser", "");
        addStringPref(StringPref.Names.COOKIES_LIST, "pref.cookiesList", "");
        addStringPref(StringPref.Names.LAST_MESSAGE_SENDED, "pref.lastMessageSended", "");
        addStringPref(StringPref.Names.FORUM_FAV_NAME, "pref.forumFavName.", "");
        addStringPref(StringPref.Names.FORUM_FAV_LINK, "pref.forumFavLink.", "");
        addStringPref(StringPref.Names.TOPIC_FAV_NAME, "pref.topicFavName.", "");
        addStringPref(StringPref.Names.TOPIC_FAV_LINK, "pref.topicFavLink.", "");
        addStringPref(StringPref.Names.TOPIC_URL_TO_FETCH, "pref.topicUrlToFetch", "");
        addStringPref(StringPref.Names.FORUM_URL_TO_FETCH, "pref.forumUrlToFetch", "");
        addStringPref(StringPref.Names.PSEUDO_OF_AUTHOR_OF_TOPIC, "pref.pseudoOfAuthorOfTopic", "");
        addStringPref(StringPref.Names.OLD_URL_FOR_TOPIC, "pref.oldUrlForTopic", "");
        addStringPref(StringPref.Names.LAST_TOPIC_TITLE_SENDED, "pref.lastTopicTitleSended", "");
        addStringPref(StringPref.Names.LAST_TOPIC_CONTENT_SENDED, "pref.lastTopicContentSended", "");
        addStringPref(StringPref.Names.LAST_SURVEY_TITLE_SENDED, "pref.lastSurveyTitleSended", "");
        addStringPref(StringPref.Names.LAST_SURVEY_REPLY_SENDED_IN_A_STRING, "pref.lastSurveyReplySendedInAString", "");
        addStringPref(StringPref.Names.IGNORED_PSEUDOS_IN_LC_LIST, "pref.ignoredPseudosInLCList", "");

        addLongPref(LongPref.Names.OLD_LAST_ID_OF_MESSAGE, "pref.oldLastIdOfMessage", 0);

        addBoolPref(BoolPref.Names.TRANSFORM_STICKER_TO_SMILEY, currentContext.getString(R.string.settingsTransformStickerToSmiley), false);
        addBoolPref(BoolPref.Names.SHOW_OVERVIEW_ON_IMAGE_CLICK, currentContext.getString(R.string.settingsShowOverviewOnImageClick), true);
        addBoolPref(BoolPref.Names.USE_DIRECT_NOELSHACK_LINK, currentContext.getString(R.string.settingsUseDirectNoelshackLink), false);
        addBoolPref(BoolPref.Names.SHORTEN_LONG_LINK, currentContext.getString(R.string.settingsShortenLongLink), true);
        addBoolPref(BoolPref.Names.USE_INTERNAL_NAVIGATOR, currentContext.getString(R.string.settingsUseInternalNavigator), false);
        addBoolPref(BoolPref.Names.SHOW_SIGNATURE_MODE_FORUM, currentContext.getString(R.string.settingsShowSignatureModeForum), true);
        addBoolPref(BoolPref.Names.SHOW_SIGNATURE_MODE_IRC, currentContext.getString(R.string.settingsShowSignatureModeIRC), false);
        addBoolPref(BoolPref.Names.TOPIC_ALTERNATE_BACKGROUND_MODE_FORUM, currentContext.getString(R.string.settingsTopicAlternateBackgroundColorModeForum), true);
        addBoolPref(BoolPref.Names.TOPIC_ALTERNATE_BACKGROUND_MODE_IRC, currentContext.getString(R.string.settingsTopicAlternateBackgroundColorModeIRC), false);
        addBoolPref(BoolPref.Names.TOPIC_CLEAR_ON_REFRESH_MODE_FORUM, currentContext.getString(R.string.settingsTopicClearOnRefresh), true);
        addBoolPref(BoolPref.Names.TOPIC_SHOW_REFRESH_WHEN_MESSAGE_SHOWED_MODE_IRC, currentContext.getString(R.string.settingsShowRefreshWhenMessagesShowedModeIRC), false);
        addBoolPref(BoolPref.Names.ENABLE_CARD_DESIGN_MODE_FORUM, currentContext.getString(R.string.settingsEnableCardDesignModeForum), true);
        addBoolPref(BoolPref.Names.SEPARATION_BETWEEN_MESSAGES_BLACK_THEM_MODE_FORUM, currentContext.getString(R.string.settingsSeparationBetweenMessagesBlackThemeModeForum), true);
        addBoolPref(BoolPref.Names.HIDE_UGLY_IMAGES, currentContext.getString(R.string.settingsHideUglyImages), false);
        addBoolPref(BoolPref.Names.FORUM_ALTERNATE_BACKGROUND, currentContext.getString(R.string.settingsForumAlternateBackgroundColor), true);
        addBoolPref(BoolPref.Names.ENABLE_SMOOTH_SCROLL, currentContext.getString(R.string.settingsEnableSmoothScroll), true);
        addBoolPref(BoolPref.Names.ENABLE_GO_TO_BOTTOM_ON_LOAD, currentContext.getString(R.string.settingsEnableGoToBottomOnLoad), true);
        addBoolPref(BoolPref.Names.ENABLE_AUTO_SCROLL_MODE_FORUM, currentContext.getString(R.string.settingsEnableAutoScrollModeForum), true);
        addBoolPref(BoolPref.Names.DEFAULT_SHOW_SPOIL_VAL, currentContext.getString(R.string.settingsDefaultShowSpoilVal), false);
        addBoolPref(BoolPref.Names.MARK_AUTHOR_PSEUDO_MODE_FORUM, currentContext.getString(R.string.settingsMarkAuthorPseudoModeForum), false);
        addBoolPref(BoolPref.Names.POST_AS_MODO_WHEN_POSSIBLE, currentContext.getString(R.string.settingsPostAsModoWhenPossible), true);
        addBoolPref(BoolPref.Names.IGNORE_TOPIC_TOO, currentContext.getString(R.string.settingsIgnoreTopicToo), true);
        addBoolPref(BoolPref.Names.HIDE_TOTALLY_MESSAGES_OF_IGNORED_PSEUDOS, currentContext.getString(R.string.settingsHideTotallyMessagesOfIgnoredPseudos), true);
        addBoolPref(BoolPref.Names.ENABLE_FAST_REFRESH_OF_IMAGES, currentContext.getString(R.string.settingsEnableFastRefreshOfImages), false);
        addBoolPref(BoolPref.Names.ENABLE_COLOR_DELETED_MESSAGES, currentContext.getString(R.string.settingsEnableColorDeletedMessages), true);
        addBoolPref(BoolPref.Names.COLOR_PSEUDO_OF_USER_IN_INFO, currentContext.getString(R.string.settingsColorPseudoOfUserInInfo), true);
        addBoolPref(BoolPref.Names.COLOR_PSEUDO_OF_USER_IN_MESSAGE, currentContext.getString(R.string.settingsColorPseudoOfUserInMessage), true);
        addBoolPref(BoolPref.Names.BACK_IS_OPEN_DRAWER, currentContext.getString(R.string.settingsBackIsOpenDrawer), false);
        addBoolPref(BoolPref.Names.SAVE_LAST_ROW_USED_INSERTSTUFF, currentContext.getString(R.string.settingsSaveLastRowUsedInsertstuff), true);

        addStringPref(StringPref.Names.MAX_NUMBER_OF_OVERLY_QUOTE, currentContext.getString(R.string.settingsMaxNumberOfOverlyQuote), "2", 0, 15);
        addStringPref(StringPref.Names.SHOW_AVATAR_MODE_FORUM, currentContext.getString(R.string.settingsShowAvatarModeForum), "1");
        addStringPref(StringPref.Names.SHOW_NOELSHACK_IMAGE, currentContext.getString(R.string.settingsShowNoelshackImage), "1");
        addStringPref(StringPref.Names.REFRESH_TOPIC_TIME, currentContext.getString(R.string.settingsRefreshTopicTime), "10000", 2500, 60000);
        addStringPref(StringPref.Names.MAX_NUMBER_OF_MESSAGES, currentContext.getString(R.string.settingsMaxNumberOfMessages), "60", 1, 120);
        addStringPref(StringPref.Names.INITIAL_NUMBER_OF_MESSAGES, currentContext.getString(R.string.settingsInitialNumberOfMessages), "10", 1, 20);
        addStringPref(StringPref.Names.THEME_USED, currentContext.getString(R.string.settingsThemeUsed), "0");
        addStringPref(StringPref.Names.AVATAR_SIZE, currentContext.getString(R.string.settingsAvatarSize), "45", 40, 60);
        addStringPref(StringPref.Names.STICKER_SIZE, currentContext.getString(R.string.settingsStickerSize), "50", 35, 70);
        addStringPref(StringPref.Names.MINI_NOELSHACK_WIDTH, currentContext.getString(R.string.settingsMiniNoelshackWidth), "68", 68, 136);
    }

    public static boolean getBool(BoolPref.Names prefName) {
        BoolPref prefInfo = listOfBoolPrefs.get(prefName);

        //noinspection SimplifiableIfStatement
        if (prefInfo != null) {
            return currentPrefs.getBoolean(prefInfo.stringName, prefInfo.defaultValue);
        } else {
            return false;
        }
    }

    public static boolean getBool(String prefName) {
        for (int i = 0; i < listOfBoolPrefs.size(); ++i) {
            BoolPref tmpPref = listOfBoolPrefs.valueAt(i);
            if (tmpPref.stringName.equals(prefName)) {
                return currentPrefs.getBoolean(tmpPref.stringName, tmpPref.defaultValue);
            }
        }

        return false;
    }

    public static int getInt(IntPref.Names prefName) {
        IntPref prefInfo = listOfIntPrefs.get(prefName);

        if (prefInfo != null) {
            return currentPrefs.getInt(prefInfo.stringName, prefInfo.defaultValue);
        } else {
            return 0;
        }
    }

    public static String getString(StringPref.Names prefName) {
        StringPref prefInfo = listOfStringPrefs.get(prefName);

        if (prefInfo != null) {
            return currentPrefs.getString(prefInfo.stringName, prefInfo.defaultValue);
        } else {
            return "";
        }
    }

    public static String getString(String prefName) {
        for (int i = 0; i < listOfStringPrefs.size(); ++i) {
            StringPref tmpPref = listOfStringPrefs.valueAt(i);
            if (tmpPref.stringName.equals(prefName)) {
                return currentPrefs.getString(tmpPref.stringName, tmpPref.defaultValue);
            }
        }

        return "";
    }

    public static String getStringWithSufix(StringPref.Names prefName, String sufix) {
        StringPref prefInfo = listOfStringPrefs.get(prefName);

        if (prefInfo != null) {
            return currentPrefs.getString(prefInfo.stringName + sufix, prefInfo.defaultValue);
        } else {
            return "";
        }
    }

    public static StringPref getStringInfos(String prefName) {
        for (int i = 0; i < listOfStringPrefs.size(); ++i) {
            StringPref tmpPref = listOfStringPrefs.valueAt(i);
            if (tmpPref.stringName.equals(prefName)) {
                return tmpPref;
            }
        }

        return new StringPref(prefName, "");
    }

    public static long getLong(LongPref.Names prefName) {
        LongPref prefInfo = listOfLongPrefs.get(prefName);

        if (prefInfo != null) {
            return currentPrefs.getLong(prefInfo.stringName, prefInfo.defaultValue);
        } else {
            return 0;
        }
    }

    public static void putBool(BoolPref.Names prefName, boolean newVal) {
        BoolPref prefInfo = listOfBoolPrefs.get(prefName);

        if (prefInfo != null) {
            currentPrefsEdit.putBoolean(prefInfo.stringName, newVal);
        }
    }

    public static void putInt(IntPref.Names prefName, int newVal) {
        IntPref prefInfo = listOfIntPrefs.get(prefName);

        if (prefInfo != null) {
            currentPrefsEdit.putInt(prefInfo.stringName, newVal);
        }
    }

    public static void putString(StringPref.Names prefName, String newVal) {
        StringPref prefInfo = listOfStringPrefs.get(prefName);

        if (prefInfo != null) {
            currentPrefsEdit.putString(prefInfo.stringName, newVal);
        }
    }

    public static void putStringWithSufix(StringPref.Names prefName, String sufix, String newVal) {
        StringPref prefInfo = listOfStringPrefs.get(prefName);

        if (prefInfo != null) {
            currentPrefsEdit.putString(prefInfo.stringName + sufix, newVal);
        }
    }

    public static void putLong(LongPref.Names prefName, long newVal) {
        LongPref prefInfo = listOfLongPrefs.get(prefName);

        if (prefInfo != null) {
            currentPrefsEdit.putLong(prefInfo.stringName, newVal);
        }
    }

    public static void removeStringWithSufix(StringPref.Names prefName, String sufix) {
        StringPref prefInfo = listOfStringPrefs.get(prefName);

        if (prefInfo != null) {
            currentPrefsEdit.remove(prefInfo.stringName + sufix);
        }
    }

    public static void applyChanges() {
        currentPrefsEdit.apply();
    }

    public static class BoolPref {
        public final String stringName;
        public final boolean defaultValue;

        BoolPref(String newStringName, boolean newDefaultValue) {
            stringName = newStringName;
            defaultValue = newDefaultValue;
        }

        public enum Names {
            IS_FIRST_LAUNCH,
            TRANSFORM_STICKER_TO_SMILEY,
            SHOW_OVERVIEW_ON_IMAGE_CLICK,
            USE_DIRECT_NOELSHACK_LINK,
            SHORTEN_LONG_LINK,
            USE_INTERNAL_NAVIGATOR,
            SHOW_SIGNATURE_MODE_FORUM, SHOW_SIGNATURE_MODE_IRC,
            TOPIC_ALTERNATE_BACKGROUND_MODE_FORUM, TOPIC_ALTERNATE_BACKGROUND_MODE_IRC, FORUM_ALTERNATE_BACKGROUND,
            TOPIC_CLEAR_ON_REFRESH_MODE_FORUM,
            TOPIC_SHOW_REFRESH_WHEN_MESSAGE_SHOWED_MODE_IRC,
            ENABLE_CARD_DESIGN_MODE_FORUM, SEPARATION_BETWEEN_MESSAGES_BLACK_THEM_MODE_FORUM, ENABLE_COLOR_DELETED_MESSAGES,
            HIDE_UGLY_IMAGES,
            ENABLE_SMOOTH_SCROLL, ENABLE_AUTO_SCROLL_MODE_FORUM,
            ENABLE_GO_TO_BOTTOM_ON_LOAD,
            DEFAULT_SHOW_SPOIL_VAL,
            MARK_AUTHOR_PSEUDO_MODE_FORUM,
            USER_IS_MODO, POST_AS_MODO_WHEN_POSSIBLE,
            IGNORE_TOPIC_TOO, HIDE_TOTALLY_MESSAGES_OF_IGNORED_PSEUDOS,
            ENABLE_FAST_REFRESH_OF_IMAGES,
            WEBVIEW_CACHE_NEED_TO_BE_CLEAR,
            COLOR_PSEUDO_OF_USER_IN_INFO, COLOR_PSEUDO_OF_USER_IN_MESSAGE,
            BACK_IS_OPEN_DRAWER,
            SAVE_LAST_ROW_USED_INSERTSTUFF
        }
    }

    public static class IntPref {
        public final String stringName;
        public final int defaultValue;

        IntPref(String newStringName, int newDefaultValue) {
            stringName = newStringName;
            defaultValue = newDefaultValue;
        }

        public enum Names {
            LAST_ACTIVITY_VIEWED,
            CURRENT_TOPIC_MODE,
            FORUM_FAV_ARRAY_SIZE, TOPIC_FAV_ARRAY_SIZE,
            LAST_ROW_SELECTED_INSERTSTUFF
        }
    }

    public static class StringPref {
        public final String stringName;
        public final String defaultValue;
        public final boolean isInt;
        public final int minVal;
        public final int maxVal;

        StringPref(String newStringName, String newDefaultValue) {
            stringName = newStringName;
            defaultValue = newDefaultValue;
            isInt = false;
            minVal = 0;
            maxVal = 0;
        }

        StringPref(String newStringName, String newDefaultValue, int newMinVal, int newMaxVal) {
            stringName = newStringName;
            defaultValue = newDefaultValue;
            isInt = true;
            minVal = newMinVal;
            maxVal = newMaxVal;
        }

        public enum Names {
            PSEUDO_OF_USER, COOKIES_LIST,
            LAST_MESSAGE_SENDED,
            FORUM_FAV_NAME, FORUM_FAV_LINK, TOPIC_FAV_NAME, TOPIC_FAV_LINK,
            TOPIC_URL_TO_FETCH, FORUM_URL_TO_FETCH, PSEUDO_OF_AUTHOR_OF_TOPIC,
            OLD_URL_FOR_TOPIC,
            LAST_TOPIC_TITLE_SENDED, LAST_TOPIC_CONTENT_SENDED, LAST_SURVEY_TITLE_SENDED, LAST_SURVEY_REPLY_SENDED_IN_A_STRING,
            MAX_NUMBER_OF_OVERLY_QUOTE,
            SHOW_AVATAR_MODE_FORUM,
            SHOW_NOELSHACK_IMAGE,
            REFRESH_TOPIC_TIME,
            MAX_NUMBER_OF_MESSAGES, INITIAL_NUMBER_OF_MESSAGES,
            THEME_USED,
            IGNORED_PSEUDOS_IN_LC_LIST,
            AVATAR_SIZE, STICKER_SIZE, MINI_NOELSHACK_WIDTH
        }
    }

    public static class LongPref {
        public final String stringName;
        public final long defaultValue;

        LongPref(String newStringName, long newDefaultValue) {
            stringName = newStringName;
            defaultValue = newDefaultValue;
        }

        public enum Names {
            OLD_LAST_ID_OF_MESSAGE
        }
    }
}
