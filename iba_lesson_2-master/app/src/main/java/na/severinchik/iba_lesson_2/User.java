package na.severinchik.iba_lesson_2;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    String userName;
    String phoneNumber;
    int password;

    public User(String userName, String password,String phoneNumber) {
        this.userName = userName;
        this.password = password.hashCode();
        this.phoneNumber = phoneNumber;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password.hashCode();
    }
    protected User(Parcel in) {
        userName = in.readString();
        password = in.readInt();
        phoneNumber = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeInt(password);
        dest.writeString(phoneNumber);
    }
}
