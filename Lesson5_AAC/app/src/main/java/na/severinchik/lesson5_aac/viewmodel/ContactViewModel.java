package na.severinchik.lesson5_aac.viewmodel;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import na.severinchik.lesson5_aac.entity.Contact;

public class ContactViewModel extends AndroidViewModel {
    ContentResolver contentResolver;
    Context context;

    public List<Contact> contactList;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contentResolver = application.getContentResolver();
        context = application.getApplicationContext();
        contactList = getContactList();
    }

    private List<Contact> getContactList() {
        List<Contact> list = new ArrayList<>();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver,
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));
                    while (cursorInfo.moveToNext()) {
                        Contact info = new Contact();
                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(info);
                    }
                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }

    public ArrayAdapter<String> getAdapter() {
        List<String> stringList = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            stringList = contactList.stream()
                    .map(elt -> elt.toString())
                    .collect(Collectors.toList());
        }
        return new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, stringList);
    }


}
