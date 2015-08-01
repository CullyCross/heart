package me.cullycross.heart;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.activeandroid.ActiveAndroid;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import fr.tkeunebr.gravatar.Gravatar;

/**
 * Created by cullycross on 7/8/15.
 *
 * base class for the application
 */
public class HeartApp extends Application {

    public static Gravatar sGravatar;

    public HeartApp() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
        sGravatar = new Gravatar.Builder().build();

        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });
    }
}
