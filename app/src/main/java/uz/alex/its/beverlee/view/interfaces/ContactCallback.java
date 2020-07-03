package uz.alex.its.beverlee.view.interfaces;

import uz.alex.its.beverlee.model.Contact;
import uz.alex.its.beverlee.view.adapters.ContactListVerticalAdapter;

public interface ContactCallback {
    void onContactSelect(final Contact contact, final ContactListVerticalAdapter.ContactVerticalViewHolder holder);
}
