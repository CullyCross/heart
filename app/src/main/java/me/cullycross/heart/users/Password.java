package me.cullycross.heart.users;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

/**
 * Created by: cullycross
 * Date: 8/6/15
 * For my shining stars!
 */

@Table(name = "Passwords")
@Parcel(value = Parcel.Serialization.BEAN, analyze = Password.class)
public class Password extends Model {
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
