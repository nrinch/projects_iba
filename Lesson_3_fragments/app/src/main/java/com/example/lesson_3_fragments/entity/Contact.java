package com.example.lesson_3_fragments.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
    public int id=0;
    public String name;
    public String phoneNumber;
    public String about;

    private static int incrementInt=0;

    public Contact(String name, String phoneNumber, String about) {

        this.id =incrementInt++;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.about = about;
    }

    protected Contact(Parcel in) {
        id= in.readInt();
        name = in.readString();
        phoneNumber = in.readString();
        about = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(phoneNumber);
        parcel.writeString(about);
    }
}
