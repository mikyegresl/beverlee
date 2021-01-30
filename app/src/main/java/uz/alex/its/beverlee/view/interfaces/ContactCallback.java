package uz.alex.its.beverlee.view.interfaces;

import uz.alex.its.beverlee.model.Contact;
import uz.alex.its.beverlee.view.adapters.ContactListHorizontalAdapter;
import uz.alex.its.beverlee.view.adapters.ContactListVerticalAdapter;

public interface ContactCallback {
    void onVerticalContactSelected(final Contact contact, final ContactListVerticalAdapter.ContactVerticalViewHolder holder);
    void onHorizontalContactSelected(final Contact contact, final ContactListHorizontalAdapter.ContactHorizontalViewHolder holder);
}
