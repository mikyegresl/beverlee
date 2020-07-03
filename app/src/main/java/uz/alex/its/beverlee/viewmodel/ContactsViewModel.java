package uz.alex.its.beverlee.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uz.alex.its.beverlee.model.Contact;

public class ContactsViewModel extends ViewModel {
    private MutableLiveData<List<Contact>> contactList;
    private MutableLiveData<List<Contact>> favContactList;
    private final MutableLiveData<Contact> selectedContact = new MutableLiveData<>();

    public MutableLiveData<List<Contact>> getContactList() {
        if (contactList == null) {
            contactList = new MutableLiveData<>();
            loadContactList();
        }
        return contactList;
    }

    public MutableLiveData<List<Contact>> getFavContactList() {
        if (favContactList == null) {
            favContactList = new MutableLiveData<>();
            loadFavContactList();
        }
        return favContactList;
    }

    public void selectContact(Contact contact) {
        selectedContact.setValue(contact);
    }

    public LiveData<Contact> getSelectedContact() {
        return selectedContact;
    }

    private void loadContactList() {
//        contactList.add(new Contact("Аброр Турсунов"));
//        contactList.add(new Contact("Нигора Икромова"));
//        contactList.add(new Contact("Сидни Кросби"));
//        contactList.add(new Contact("Роджер Федерер"));
//        contactList.add(new Contact("Винстон Черчиль"));
//        contactList.add(new Contact("Мао Цзедун"));
//        contactList.add(new Contact("Авраам Линкольн"));
//        contactList.add(new Contact("Галь Гадот"));
//        contactList.add(new Contact("Моника Беллучи"));
//        contactList.add(new Contact("Сергей Ким"));
    }

    private void loadFavContactList() {
//        favContactList.add(new Contact("Галь Гадот"));
//        favContactList.add(new Contact("Моника Беллучи"));
//        favContactList.add(new Contact("Нигора Икромова"));
    }
}
