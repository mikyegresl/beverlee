package uz.alex.its.beverlee.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.actor.Contact;
import uz.alex.its.beverlee.storage.LocalDatabase;
import uz.alex.its.beverlee.storage.dao.ContactDao;

public class ContactsRepository {
    private final Context context;
    private static final int CONTACT_ID_INDEX = 0;
    private static final int CONTACT_KEY_INDEX = 1;
    private static final String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?";

    private final ContactDao contactDao;

    private static final String[] FROM_COLUMNS = {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };
    private static final int[] TO_IDS = {
            R.id.contact_name
    };
    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    };

    private String searchString;
    private String[] selectionArgs = { searchString };

    public ContactsRepository(final Context context) {
        this.context = context;
        final LocalDatabase database = LocalDatabase.getInstance(context);
        this.contactDao = database.contactDao();
    }

    public void loadContacts() {
        final Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        Log.i(TAG, "fetchContacts(): cursor.getCount() is " + cursor.getCount());

        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor.moveToNext()) {
                final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                final String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                insertContact(new Contact(0, name, phone));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    //todo: fetch Contacts from book to Room

    /* room queries */
    void insertContact(final Contact contact) {
        new Thread(() -> {
            final long id = contactDao.insertContact(contact);
        }).start();
    }

    void insertContactList(final List<Contact> contactList) {
        new Thread(() -> { contactDao.insertContactList(contactList); }).start();
    }

    void deleteContact(final Contact contact) {
        new Thread(() -> { contactDao.deleteContact(contact); }).start();
    }

    void deleteContactList(final List<Contact> contactList) {
        new Thread(() -> { contactDao.deleteContactList(contactList); }).start();
    }

    public LiveData<List<Contact>> selectAllContacts() {
        return contactDao.selectAllContacts();
    }

    public LiveData<Contact> selectContactByName(final String name) {
        return contactDao.selectContactByName(name);
    }

    public LiveData<Contact> selectContactByPhone(final String phone) {
        return contactDao.selectContactByPhone(phone);
    }

    private static final String TAG = ContactsRepository.class.toString();
}
