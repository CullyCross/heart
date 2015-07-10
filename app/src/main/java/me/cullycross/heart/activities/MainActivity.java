package me.cullycross.heart.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;


import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import me.cullycross.heart.R;
import me.cullycross.heart.adapters.PasswordsAdapter;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.rvToDoList)
    protected RecyclerView mRecyclerView;

    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindString(R.string.app_name)
    protected String mHeader;

    //@BindArray(R.array.test_strings)
    protected String [] mStrings;

    private Drawer mDrawer;
    private AccountHeader mAccountHeader;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        initDrawer();
        initRecyclerView();
        initToolbar();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Hello Snackbar", Snackbar.LENGTH_LONG).show();
            }
        });
    }

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

    ////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////

    // Init methods
    private void initDrawer() {

        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.toolbar_background)
                .withHeaderBackgroundScaleType(ImageView.ScaleType.CENTER_CROP)
                .addProfiles(
                        new ProfileDrawerItem().withName("Anton Shkurenko")
                                .withEmail("elaugfein@gmail.com")
                                .withIcon(getResources().getDrawable(android.R.drawable.ic_lock_idle_lock)),
                        new ProfileDrawerItem().withName("Cully Cross")
                                .withEmail("cullycross@gmail.com")
                                .withIcon(getResources().getDrawable(android.R.drawable.ic_delete)),
                        new ProfileDrawerItem().withName("Elaugfein Mizzrym")
                                .withEmail("mizzrym@gmail.com")
                                .withIcon(getResources().getDrawable(android.R.drawable.ic_menu_view)),
                        new ProfileSettingDrawerItem().withName("Add new profile")
                                .withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_add)),
                        new ProfileSettingDrawerItem().withName("Profile Manager")
                                .withIcon(GoogleMaterial.Icon.gmd_settings)
                ).build();


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
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                                        // do something with the clicked item :D

                                        return false;
                                    }
                                })
                                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
    }

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mStrings = getResources().getStringArray(R.array.test_strings);
        mAdapter = new PasswordsAdapter(mStrings);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initToolbar() {
        mCollapsingToolbarLayout.setTitle(mHeader);
    }

    ////////////////////////////////////////////////////////////
    // Public methods
    ////////////////////////////////////////////////////////////

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
