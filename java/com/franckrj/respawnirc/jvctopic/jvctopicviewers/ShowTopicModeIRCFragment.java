package com.franckrj.respawnirc.jvctopic.jvctopicviewers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.franckrj.respawnirc.R;
import com.franckrj.respawnirc.jvctopic.jvctopicgetters.JVCTopicModeIRCGetter;
import com.franckrj.respawnirc.utils.JVCParser;
import com.franckrj.respawnirc.utils.Utils;

import java.util.ArrayList;

public class ShowTopicModeIRCFragment extends AbsShowTopicFragment {
    private static final String SAVE_OLD_URL_FOR_TOPIC = "saveOldUrlForTopic";
    private static final String SAVE_OLD_LAST_ID_OF_MESSAGE = "saveOldLastIdOfMessage";

    private int maxNumberOfMessagesShowed = 40;
    private int initialNumberOfMessagesShowed = 10;
    private JVCTopicModeIRCGetter getterForTopic = null;
    private String oldUrlForTopic = "";
    private long oldLastIdOfMessage = 0;

    private final JVCTopicModeIRCGetter.NewMessagesListener listenerForNewMessages = new JVCTopicModeIRCGetter.NewMessagesListener() {
        @Override
        public void getNewMessages(ArrayList<JVCParser.MessageInfos> listOfNewMessages) {
            if (!listOfNewMessages.isEmpty()) {
                boolean scrolledAtTheEnd = true;
                boolean firstTimeGetMessages = adapterForTopic.getAllItems().isEmpty();
                isInErrorMode = false;

                if (jvcMsgList.getChildCount() > (adapterForTopic.getShowSurvey() ? 1 : 0)) {
                    scrolledAtTheEnd = (jvcMsgList.getLastVisiblePosition() == jvcMsgList.getCount() - 1) &&
                            (jvcMsgList.getChildAt(jvcMsgList.getChildCount() - 1).getBottom() <= jvcMsgList.getHeight());
                }

                for (JVCParser.MessageInfos thisMessageInfo : listOfNewMessages) {
                    if (!thisMessageInfo.isAnEdit) {
                        adapterForTopic.addItem(thisMessageInfo);
                    } else {
                        adapterForTopic.updateThisItem(thisMessageInfo);
                    }
                }

                if (firstTimeGetMessages) {
                    while (adapterForTopic.getCount() > initialNumberOfMessagesShowed) {
                        adapterForTopic.removeFirstItem();
                    }
                }

                while (adapterForTopic.getCount() > maxNumberOfMessagesShowed) {
                    adapterForTopic.removeFirstItem();
                }

                adapterForTopic.updateAllItems();

                if (scrolledAtTheEnd && jvcMsgList.getCount() > 0) {
                    jvcMsgList.setSelection(jvcMsgList.getCount() - 1);
                }
            } else {
                if (!isInErrorMode) {
                    getterForTopic.reloadTopic();
                    isInErrorMode = true;
                } else if (adapterForTopic.getAllItems().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.errorDownloadFailed, Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public static boolean getShowNavigationButtons() {
        return false;
    }

    private void saveOldTopicInfos() {
        if (!getterForTopic.getUrlForTopic().isEmpty()) {
            SharedPreferences.Editor sharedPrefEdit = sharedPref.edit();
            sharedPrefEdit.putString(getString(R.string.prefOldUrlForTopic), getterForTopic.getUrlForTopic());
            sharedPrefEdit.putLong(getString(R.string.prefOldLastIdOfMessage), getterForTopic.getLastIdOfMessage());
            sharedPrefEdit.apply();
        }
    }

    private void loadFromOldTopicInfos() {
        isInErrorMode = false;
        getterForTopic.stopAllCurrentTask();
        getterForTopic.resetDirectlyShowedInfos();
        adapterForTopic.disableSurvey();
        adapterForTopic.removeAllItems();
        adapterForTopic.updateAllItems();
        getterForTopic.setOldTopic(oldUrlForTopic, oldLastIdOfMessage);
        getterForTopic.reloadTopic();
    }

    @Override
    public void setPageLink(String newTopicLink) {
        isInErrorMode = false;

        getterForTopic.stopAllCurrentTask();
        getterForTopic.resetDirectlyShowedInfos();
        adapterForTopic.disableSurvey();
        adapterForTopic.removeAllItems();
        adapterForTopic.updateAllItems();
        getterForTopic.setNewTopic(newTopicLink);
        getterForTopic.reloadTopic();
    }

    @Override
    public void clearContent() {
        saveOldTopicInfos();
        super.clearContent();
    }

    @Override
    protected void initializeGetterForMessages() {
        getterForTopic = new JVCTopicModeIRCGetter(getActivity());
        absGetterForTopic = getterForTopic;
    }

    @Override
    protected void initializeSettings() {
        showRefreshWhenMessagesShowed = false;
        currentSettings.firstLineFormat = "[<%DATE_COLOR_START%><%DATE_TIME%><%DATE_COLOR_END%>] &lt;<%PSEUDO_COLOR_START%><%PSEUDO_PSEUDO%><%PSEUDO_COLOR_END%>&gt;";
        currentSettings.colorPseudoUser = Utils.resColorToString(R.color.colorPseudoUser, getActivity());
        currentSettings.colorPseudoOther = "#000025";
        currentSettings.colorPseudoModo = Utils.resColorToString(R.color.colorPseudoModo, getActivity());
        currentSettings.colorPseudoAdmin = Utils.resColorToString(R.color.colorPseudoAdmin, getActivity());
        currentSettings.secondLineFormat = "<%MESSAGE_MESSAGE%>";
        currentSettings.addBeforeEdit = "";
        currentSettings.addAfterEdit = "";
    }

    @Override
    protected void initializeAdapter() {
        adapterForTopic.setIdOfLayoutToUse(R.layout.jvcmessages_rowirc);
        adapterForTopic.setAlternateBackgroundColor(false);
    }

    @Override
    protected void reloadSettings() {
        super.reloadSettings();
        maxNumberOfMessagesShowed = Integer.parseInt(sharedPref.getString(getString(R.string.settingsMaxNumberOfMessages), getString(R.string.maxNumberOfMessagesDefault)));
        initialNumberOfMessagesShowed = Integer.parseInt(sharedPref.getString(getString(R.string.settingsInitialNumberOfMessages), getString(R.string.initialNumberOfMessagesDefault)));
        getterForTopic.setTimeBetweenRefreshTopic(Integer.parseInt(sharedPref.getString(getString(R.string.settingsRefreshTopicTime), getString(R.string.refreshTopicTimeDefault))));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mainView = inflater.inflate(R.layout.fragment_showtopicirc, container, false);

        jvcMsgList = (ListView) mainView.findViewById(R.id.jvcmessage_view_showtopicirc);
        swipeRefresh = (SwipeRefreshLayout) mainView.findViewById(R.id.swiperefresh_showtopicirc);

        return mainView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getterForTopic.setListenerForNewMessages(listenerForNewMessages);
        swipeRefresh.setEnabled(false);

        if (savedInstanceState != null) {
            oldUrlForTopic = savedInstanceState.getString(SAVE_OLD_URL_FOR_TOPIC, "");
            oldLastIdOfMessage = savedInstanceState.getLong(SAVE_OLD_LAST_ID_OF_MESSAGE, 0);
        } else {
            oldUrlForTopic = sharedPref.getString(getString(R.string.prefOldUrlForTopic), "");
            oldLastIdOfMessage = sharedPref.getLong(getString(R.string.prefOldLastIdOfMessage), 0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isInErrorMode = false;
        getterForTopic.reloadTopic();
    }

    @Override
    public void onPause() {
        saveOldTopicInfos();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_OLD_URL_FOR_TOPIC, oldUrlForTopic);
        outState.putLong(SAVE_OLD_LAST_ID_OF_MESSAGE, oldLastIdOfMessage);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_showtopicirc, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_load_from_old_topic_info_showtopicirc).setEnabled(JVCParser.checkIfTopicAreSame(getterForTopic.getUrlForTopic(), oldUrlForTopic));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_load_from_old_topic_info_showtopicirc:
                loadFromOldTopicInfos();
                return true;
            case R.id.action_switch_to_forum_mode_showtopicirc:
                if (listenerForNewModeNeeded != null) {
                    listenerForNewModeNeeded.newModeRequested(MODE_FORUM);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}