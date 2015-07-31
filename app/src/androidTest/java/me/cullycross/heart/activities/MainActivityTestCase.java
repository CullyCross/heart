package me.cullycross.heart.activities;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import me.cullycross.heart.users.UserProfile;

/**
 * Created by: cullycross
 * Date: 7/31/15
 * For my shining stars!
 */

public class MainActivityTestCase extends ActivityInstrumentationTestCase2<MainActivity> {

    private final static String TAG = MainActivityTestCase.class.getCanonicalName();

    private MainActivity mMainActivity;

    public MainActivityTestCase(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mMainActivity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mMainActivity is null", mMainActivity);
    }

    public void testUserSaveInOrm() {

        UserProfile userProfile = new UserProfile("Anton", "LittlePassword");

        userProfile.addPassword("Vk", "VkPass");
        userProfile.addPassword("Fb", "FbPass");
        userProfile.addPassword("Tw", "TwPass");

        //////////////////////

        UserProfile loadedUser = UserProfile.load(UserProfile.class, userProfile.getId());

        Log.v(TAG, loadedUser.toString());

    }
}
