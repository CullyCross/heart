package me.cullycross.heart.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import me.cullycross.heart.R;
import me.cullycross.heart.fragments.PasswordsFragment;
import me.cullycross.heart.fragments.UserDialogFragment;
import me.cullycross.heart.users.UserProfile;


public class MainActivity extends AppCompatActivity
        implements PasswordsFragment.OnFragmentInteractionListener,
                    Drawer.OnDrawerItemClickListener,
                    UserDialogFragment.OnFragmentInteractionListener{

    @Bind(R.id.main_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindString(R.string.app_name)
    protected String mHeader;

    private final static String FRAGMENT_PASSWORDS = "fragment_passwords";
    private final static String FRAGMENT_DIALOG_REGISTER = "fragment_dialog_register";
    private final static String TAG = MainActivity.class.getCanonicalName();

    private final static int DRAWER_ADD_NEW_PROFILE = 0;

    private Drawer mDrawer;
    private AccountHeader mAccountHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        initDrawer();
        initFragment();
        initToolbar();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hello Snackbar", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    ////////////////////////////////////////////////////////////
    // Public methods
    ////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    ////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////

    // Init methods
    private void initDrawer() {

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.toolbar_background)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        switch (iProfile.getIdentifier()) {
                            case DRAWER_ADD_NEW_PROFILE:
                                UserDialogFragment dialog = UserDialogFragment.newInstance();
                                dialog.show(getFragmentManager(), FRAGMENT_DIALOG_REGISTER);
                                return true;
                            default:
                                return false;
                        }
                    }
                })
                .build();

        mAccountHeader.addProfiles(UserProfile.getDrawerProfiles());
        mAccountHeader.addProfiles(
                new ProfileSettingDrawerItem().withName("Add new profile")
                .withIdentifier(DRAWER_ADD_NEW_PROFILE),
                new ProfileSettingDrawerItem().withName("Profile Manager")
        );


        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mAccountHeader)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDisplayBelowToolbar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Free to play").withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName("Custom drawer item").withIcon(FontAwesome.Icon.faw_eye),
                        new SectionDrawerItem().withName("Section header"),
                        new SecondaryDrawerItem().withName("Settings").withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName("Help").withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new SecondaryDrawerItem().withName("Open source").withIcon(FontAwesome.Icon.faw_github),
                        new SecondaryDrawerItem().withName("Contact").withIcon(FontAwesome.Icon.faw_bullhorn)

                )
                .withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener(this)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void initFragment() {

        PasswordsFragment fragment = PasswordsFragment.newInstance("password");

        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_frame, fragment, FRAGMENT_PASSWORDS)
                .addToBackStack(FRAGMENT_PASSWORDS)
                .commit();
    }

    private void initToolbar() {

        try {
            // Retrieve the CollapsingTextHelper Field
            final Field ctlf = mCollapsingToolbarLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            ctlf.setAccessible(true);

            // Retrieve an instance of CollapsingTextHelper and its TextPaint
            final Object ctp = ctlf.get(mCollapsingToolbarLayout);
            final Field tpf = ctp.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            // Apply your Typeface to the CollapsingTextHelper TextPaint
            ((TextPaint) tpf.get(ctp)).setTypeface(
                    Typeface.createFromAsset(
                        getAssets(),
                        "fonts/Lobster-Regular.ttf"
                    ));
        } catch (Exception ignored) {
            // Nothing to do
        }

        mCollapsingToolbarLayout.setTitle(mHeader);
    }

    @Override
    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {

        switch (iDrawerItem.getIdentifier()) {

            default:
                return false;
        }
    }

    @Override
    public void onUserRegistered(UserProfile userProfile) {

        ProfileDrawerItem profile = userProfile.toProfileDrawerItem();

        mAccountHeader.addProfile(
                profile,
                mAccountHeader.getProfiles().size() - 2);
        mAccountHeader.setActiveProfile(profile, false);
    }
}
