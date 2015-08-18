package me.cullycross.heart.users;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import java.util.List;

import fr.tkeunebr.gravatar.Gravatar;
import me.cullycross.heart.HeartApp;

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

    protected static final String FILENAME = "user_profiles";

    @Column(name = "Name",
            unique = true)
    @ParcelProperty("name")
    @SerializedName("name")
    protected String mName;

    @Column(name = "Password")
    @ParcelProperty("password")
    @SerializedName("password")
    protected String mPassword;

    public static List<UserProfile> getRealProfiles() {
        return new Select().from(UserProfile.class).orderBy("id ASC").execute();
    }

    public static ProfileDrawerItem [] getRealDrawerProfiles() {

        List<UserProfile> profiles = getRealProfiles();
        int size = profiles.size();

        ProfileDrawerItem [] drawerProfiles =
                new ProfileDrawerItem[size];

        for(int i = 0; i < size; i++) {
            drawerProfiles[i] = profiles.get(i).toProfileDrawerItem();
        }

        return drawerProfiles;
    }

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

    public static UserProfile createUser(String password,
                                  String name) {
        UserProfile user = new UserProfile(password, name);
        user.save();
        return user;
    }

    public List<Password> getPasswords() {
        return getMany(Password.class, "User");
    }

    public List<FakeUserProfile> getFakes() {
        return getMany(FakeUserProfile.class, "LinkedUser");
    }

    public Password addPassword(String name, String password) {

        List<FakeUserProfile> fakes = getFakes();

        for(FakeUserProfile fake : fakes) {
            fake.addPassword(name);
        }

        return new Password(name, password, this);
    }

    public ProfileDrawerItem toProfileDrawerItem() {
        return new ProfileDrawerItem()
                .withEmail(mName)
                .withIcon(HeartApp.sGravatar.with(mName)
                        .defaultImage(Gravatar.DefaultImage.IDENTICON)
                        .build());

    }
}
