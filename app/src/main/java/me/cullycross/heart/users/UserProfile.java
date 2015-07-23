package me.cullycross.heart.users;

import android.content.Context;

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

@Parcel
public class UserProfile {

    private static final String FILENAME = "user_profiles";

    @ParcelProperty("name")
    @SerializedName("name")
    private String mName;

    @ParcelProperty("password")
    @SerializedName("password")
    private String mPassword;

    @ParcelProperty("map")
    @SerializedName("map")
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

    public void saveUserToJson(Context ctx) {
        List<UserProfile> list = readFromJson(ctx);
        list.add(this);

        writeToJson(ctx, list);
    }

    public static void writeToJson(Context ctx, List<UserProfile> userProfiles) {
        Gson gson = new Gson();

        Type type = new TypeToken<List<UserProfile>>(){}.getType();
        String accountsJson = gson.toJson(userProfiles, type);

        try {
            FileOutputStream outputStream =
                    ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            outputStream.write(accountsJson.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<UserProfile> readFromJson(Context ctx) {
        Gson gson = new Gson();
        StringBuilder fileContent = new StringBuilder("");
        try {
            FileInputStream inputStream = ctx.openFileInput(FILENAME);
            byte[] buffer = new byte[1024];
            int n;

            while ((n = inputStream.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(fileContent.toString().equals("")) {
            throw new IllegalArgumentException("File content is empty");
        }

        Type type = new TypeToken<List<UserProfile>>(){}.getType();
        return gson.fromJson(fileContent.toString(), type);
    }


}
