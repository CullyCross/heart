package me.cullycross.heart.users;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import java.util.List;

/**
 * Created by: cullycross
 * Date: 8/6/15
 * For my shining stars!
 */

@Table(name = "FakeUsers")
@Parcel(value = Parcel.Serialization.BEAN, analyze = {FakeUserProfile.class, UserProfile.class})
public class FakeUserProfile extends UserProfile {

    @Column(name = "LinkedUser")
    @ParcelProperty("user")
    @SerializedName("user")
    protected UserProfile mLinkedUser;

    public FakeUserProfile() {
        super();
    }

    @ParcelConstructor
    public FakeUserProfile(@ParcelProperty("password") String password,
                           @ParcelProperty("name") String name,
                           @ParcelProperty("user") UserProfile linkedUser) {
        super(password, name);
        mLinkedUser = linkedUser;
    }

    public static FakeUserProfile createUser(String password, String name, UserProfile profile) {
        FakeUserProfile fakeUserProfile = new FakeUserProfile(password, name, profile);
        fakeUserProfile.save();
        return fakeUserProfile;
    }

    @Override
    public Password addPassword(String name, String password) {

        List<FakeUserProfile> fakes = mLinkedUser.getFakes();

        for(FakeUserProfile fake : fakes) {
            fake.addPassword(name);
        }
        return new Password(name, password, this);
    }

    // todo(CullyCross): change password to a random value
    public Password addPassword(String name) {
        return new Password(name, "password", this);
    }
}
