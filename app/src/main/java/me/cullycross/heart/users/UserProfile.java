package me.cullycross.heart.users;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cullycross on 7/21/15.
 *
 * User profile, to store data here (stores in <s>json</s> in internal dir)
 *
 * todo(CullyCross): refactor it with ActiveAndroid
 */

@Table(name = "Users")
@Parcel(value = Parcel.Serialization.BEAN, analyze = UserProfile.class)
public class UserProfile extends Model {

    private static final String FILENAME = "user_profiles";

    @Column(name = "Name",
            unique = true)
    @ParcelProperty("name")
    @SerializedName("name")
    private String mName;

    @Column(name = "Password")
    @ParcelProperty("password")
    @SerializedName("password")
    private String mPassword;

    public UserProfile() {
        super();
    }

    @ParcelConstructor
    public UserProfile(
            @ParcelProperty("password") String password,
            @ParcelProperty("name") String name) {
        this();
        mPassword = password;
        mName = name;
        this.save();
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("Name: ")
                .append(mName)
                .append(", Password: ")
                .append(mPassword)
                .append(", Passwords: ");

        List<Password> passwords = getPasswords();

        for(Password p : passwords) {
            builder.append(p.toString());
        }
        return builder.toString();
    }

    public List<Password> getPasswords() {
        return getMany(Password.class, "User");
    }

    public Password addPassword(String name, String password) {

        return new Password(name, password, this);
    }

    @Table(name = "Passwords")
    @Parcel(value = Parcel.Serialization.BEAN, analyze = Password.class)
    public static class Password extends Model {

        @Column(name = "Name")
        @ParcelProperty("name")
        @SerializedName("name")
        private String mName;

        @Column(name = "Password")
        @ParcelProperty("password")
        @SerializedName("password")
        private String mPassword;

        @Column(name = "User")
        @ParcelProperty("user")
        @SerializedName("user")
        private UserProfile mUserProfile;

        public Password() {
            super();
        }

        @ParcelConstructor
        public Password(
                @ParcelProperty("name") String name,
                @ParcelProperty("password") String password,
                @ParcelProperty("user") UserProfile userProfile) {
            this();
            mName = name;
            mPassword = password;
            mUserProfile = userProfile;
            this.save();
        }

        @Override
        public String toString() {
            return "\nPassName: " + mName + ", PassPass: " + mPassword;
        }
    }
}
