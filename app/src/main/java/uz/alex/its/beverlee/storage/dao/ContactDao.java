package uz.alex.its.beverlee.storage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uz.alex.its.beverlee.model.actor.Contact;

@Dao
public interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertContact(final Contact contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertContactList(final List<Contact> contactList);

    @Delete
    void deleteContact(final Contact contact);

    @Delete
    void deleteContactList(final List<Contact> contactList);

    @Query("SELECT * FROM contact ORDER BY id DESC")
    LiveData<List<Contact>> selectAllContacts();

    @Query("SELECT * FROM contact WHERE name LIKE :name")
    LiveData<Contact> selectContactByName(final String name);

    @Query("SELECT * FROM contact WHERE phone LIKE :phone")
    LiveData<Contact> selectContactByPhone(final String phone);
}
