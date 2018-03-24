package com.franckrj.respawnirc.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.text.emoji.EmojiCompat;
import android.text.Spannable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.franckrj.respawnirc.MainActivity;
import com.franckrj.respawnirc.R;
import com.franckrj.respawnirc.WebBrowserActivity;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String colorToString(@ColorInt int colorValue) {
        return String.format("#%06X", 0xFFFFFF & colorValue);
    }

    public static String colorToStringWithAlpha(@ColorInt int colorValue) {
        return String.format("#%08X", colorValue);
    }

    public static int roundToInt(double valToRound) {
        return (int) (valToRound + 0.5);
    }

    public static boolean stringsAreEquals(String str1, String str2) {
        return (str1 == null ? str2 == null : str1.equals(str2));
    }

    public static String truncateString(String baseString, int maxSize, String endingPartIfCuted) {
        if (baseString.length() > maxSize) {
            baseString = baseString.substring(0, maxSize - endingPartIfCuted.length()) + endingPartIfCuted;
        }

        return baseString;
    }

    public static boolean stringIsEmptyOrNull(String thisString) {
        //noinspection SimplifiableIfStatement
        if (thisString == null) {
            return true;
        }
        return thisString.isEmpty();
    }

    public static void showSoftKeyboard(Activity forThisActivity) {
        InputMethodManager inputManager = (InputMethodManager) forThisActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideSoftKeyboard(Activity fromThisActivity) {
        InputMethodManager inputManager = (InputMethodManager) fromThisActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = fromThisActivity.getCurrentFocus();
        if (inputManager != null && focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void replaceSpanByAnotherSpan(Spannable inThisSpan, Object oldSpan, Object newSpan) {
        int start = inThisSpan.getSpanStart(oldSpan);
        int end = inThisSpan.getSpanEnd(oldSpan);
        int flags = inThisSpan.getSpanFlags(oldSpan);
        inThisSpan.setSpan(newSpan, start, end, flags);
        inThisSpan.removeSpan(oldSpan);
    }

    public static String encodeStringToUrlString(String baseString) {
        try {
            baseString = URLEncoder.encode(baseString, "UTF-8");
        } catch (Exception e) {
            baseString = "";
        }
        return baseString;
    }

    public static String decodeUrlStringToString(String baseString) {
        try {
            baseString = URLDecoder.decode(baseString, "UTF-8");
        } catch (Exception e) {
            baseString = "";
        }
        return baseString;
    }

    public static ArrayList<String> mapStringArrayList(List<String> baseList, StringModifier mapFunction) {
        ArrayList<String> newList = new ArrayList<>();

        for (String currentString : baseList) {
            newList.add(mapFunction.changeString(currentString));
        }

        return newList;
    }

    public static String imageLinkToFileName(String link) {
        if (link.startsWith("http://image.noelshack.com/minis/")) {
            return "img_nlsk_mini_" + link.substring(("http://image.noelshack.com/minis/").length()).replace("/", "_");
        } else if (link.startsWith("http://image.noelshack.com/fichiers/")) {
            return "img_nlsk_big_" + link.substring(("http://image.noelshack.com/fichiers/").length()).replace("/", "_");
        } else if (link.startsWith("http://image.jeuxvideo.com/avatar")) {
            return "img_vtr_" + link.substring(("http://image.jeuxvideo.com/avatar").length()).replace("/", "_");
        } else {
            return "";
        }
    }

    public static void openCorrespondingBrowser(PrefsManager.LinkType linkTypeToOpenInternalBrowser, String link, Activity parentActivity) {
        boolean itsAJVCLink = link.matches("(?i)^http(s)?://((www|m)\\.)?jeuxvideo\\.com$") ||
                              link.matches("(?i)^http(s)?://((www|m)\\.)?jeuxvideo\\.com/.*");

        if (linkTypeToOpenInternalBrowser.type == PrefsManager.LinkType.ALL_LINKS ||
                (linkTypeToOpenInternalBrowser.type == PrefsManager.LinkType.JVC_LINKS_ONLY && itsAJVCLink)) {
            openLinkInInternalBrowser(link, parentActivity);
        } else {
            openLinkInExternalBrowser(link, parentActivity);
        }
    }

    public static void openLinkInExternalBrowser(String link, Activity parentActivity) {
        try {
            Intent browserIntent;

            if (JVCParser.checkIfItsOpennableFormatedLink(JVCParser.formatThisUrlToClassicJvcUrl(link))) {
                browserIntent = Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(link)), parentActivity.getString(R.string.chooseBrowser));
            } else {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            }

            parentActivity.startActivity(browserIntent);
        } catch (Exception e) {
            //rien
        }
    }

    public static void openLinkInInternalBrowser(String link, Activity parentActivity) {
        Intent newBrowserIntent = new Intent(parentActivity, WebBrowserActivity.class);
        newBrowserIntent.putExtra(WebBrowserActivity.EXTRA_URL_LOAD, link);
        parentActivity.startActivity(newBrowserIntent);
    }

    public static void putStringInClipboard(String textToCopy, Activity fromThisActivity) {
        ClipboardManager clipboard = (ClipboardManager) fromThisActivity.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText(textToCopy, textToCopy);
            clipboard.setPrimaryClip(clip);
        }
    }

    public static void insertStringInEditText(EditText currentEditText, String stringToInsert, int posOfCenterFromEnd) {
        int currentCursorPos = currentEditText.getSelectionStart();
        int currentEndOfSelec = currentEditText.getSelectionEnd();
        if (currentCursorPos == -1) {
            currentCursorPos = 0;
        }
        if (currentEndOfSelec > currentCursorPos && posOfCenterFromEnd > 0) {
            String firstStringToAdd = stringToInsert.substring(0, stringToInsert.length() - posOfCenterFromEnd);
            String secondStringToAdd = stringToInsert.substring(stringToInsert.length() - posOfCenterFromEnd);
            currentEditText.getText().insert(currentEndOfSelec, secondStringToAdd);
            currentEditText.getText().insert(currentCursorPos, firstStringToAdd);
            currentEditText.setSelection(currentEndOfSelec + stringToInsert.length() - posOfCenterFromEnd);
        } else {
            currentEditText.getText().insert(currentCursorPos, stringToInsert);
            currentEditText.setSelection(currentCursorPos + stringToInsert.length() - posOfCenterFromEnd);
        }
    }

    public static CharSequence applyEmojiCompatIfPossible(CharSequence baseMessage) {
        if (EmojiCompat.get().getLoadState() == EmojiCompat.LOAD_STATE_SUCCEEDED) {
            return EmojiCompat.get().process(baseMessage);
        } else {
            return baseMessage;
        }
    }

    @TargetApi(25)
    public static void updateShortcuts(Activity parentActivity, ShortcutManager shortcutManager, int sizeOfForumFavArray) {
        ArrayList<ShortcutInfo> listOfShortcuts = new ArrayList<>();
        int sizeOfShortcutArray = (sizeOfForumFavArray > 4 ? 4 : sizeOfForumFavArray);

        for (int i = 0; i < sizeOfShortcutArray; ++i) {
            String currentShortcutLink = PrefsManager.getStringWithSufix(PrefsManager.StringPref.Names.FORUM_FAV_LINK, String.valueOf(i));
            String currentShortcutName = PrefsManager.getStringWithSufix(PrefsManager.StringPref.Names.FORUM_FAV_NAME, String.valueOf(i));
            ShortcutInfo newShortcut = new ShortcutInfo.Builder(parentActivity, String.valueOf(i) + "_" + currentShortcutLink)
                    .setShortLabel(currentShortcutName)
                    .setLongLabel(currentShortcutName)
                    .setIcon(Icon.createWithResource(parentActivity, R.mipmap.ic_shortcut_forum))
                    .setIntent(new Intent(MainActivity.ACTION_OPEN_SHORTCUT, Uri.parse(currentShortcutLink), parentActivity, MainActivity.class)).build();

            listOfShortcuts.add(newShortcut);
        }

        try {
            shortcutManager.setDynamicShortcuts(listOfShortcuts);
        } catch (Exception e) {
            /* À ce qu'il parait ça peut crash "when the user is locked", je sais pas ce que ça
             * veut dire donc dans le doute je mets ça là. */
        }
    }

    public interface StringModifier {
        String changeString(String baseString);
    }
}
