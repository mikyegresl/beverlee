package uz.alex.its.beverlee.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uz.alex.its.beverlee.model.actor.Contact;
import uz.alex.its.beverlee.repository.ContactsRepository;

public class ContactsViewModel extends ViewModel {
    private final ContactsRepository contactsRepository;

    public ContactsViewModel(final Context context) {
        contactsRepository = new ContactsRepository(context);
    }

    public void loadContactList() {
        contactsRepository.loadContacts();
    }

    public LiveData<List<Contact>> getContactList() {
        return contactsRepository.selectAllContacts();
    }

    public LiveData<List<Contact>> getFavContactList() {
        return null;
    }
}
