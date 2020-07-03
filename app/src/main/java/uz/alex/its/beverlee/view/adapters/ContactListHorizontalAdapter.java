package uz.alex.its.beverlee.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Contact;

public class ContactListHorizontalAdapter extends RecyclerView.Adapter<ContactListHorizontalAdapter.ContactHorizontalViewHolder> {
    private static final String TAG = ContactListHorizontalAdapter.class.toString();
    private List<Contact> contactList;
    private Context context;

    public ContactListHorizontalAdapter(@NonNull final Context context) {
        this.context = context;
    }

    public void setContactList(@NonNull final List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactHorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(context).inflate(R.layout.contact_horizontal_view_holder, parent, false);
        return new ContactHorizontalViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHorizontalViewHolder holder, int position) {
        holder.contactNameTextView.setText(contactList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    static class ContactHorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView contactAvatarImageView;
        TextView contactNameTextView;

        public ContactHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            contactAvatarImageView = itemView.findViewById(R.id.contact_avatar_image_view);
            contactNameTextView = itemView.findViewById(R.id.contact_name_text_view);
        }
    }
}
