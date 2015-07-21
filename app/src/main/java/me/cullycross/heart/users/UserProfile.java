package me.cullycross.heart.users;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cullycross on 7/21/15.
 *
 * User profile, to store data here (stores in json in internal dir)
 */

@Parcel
public class UserProfile {

    @ParcelProperty("name")
    private String mName;

    @ParcelProperty("password")
    private String mPassword;

    @ParcelProperty("map")
    private final Map<String, String> mPasswords;

    public UserProfile(String name, String password) {
        mPasswords = new HashMap<String, String>();
        mName = name;
        mPassword = password;
    }

    @ParcelConstructor
    public UserProfile(
            @ParcelProperty("map") Map<String, String> passwords,
            @ParcelProperty("password") String password,
            @ParcelProperty("name") String name) {
        mPasswords = passwords;
        mPassword = password;
        mName = name;
    }

    public String putPassword(String name, String password) {
        return mPasswords.put(name, password);
    }


    public Map<String, String> getPasswords() {
        return mPasswords;
    }
}
