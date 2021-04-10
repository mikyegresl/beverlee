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
import uz.alex.its.beverlee.model.actor.Contact;
import uz.alex.its.beverlee.view.interfaces.ContactCallback;

public class ContactListHorizontalAdapter extends RecyclerView.Adapter<ContactListHorizontalAdapter.ContactHorizontalViewHolder> {
    private List<Contact> contactList;
    private final Context context;
    private final ContactCallback contactCallback;

    public ContactListHorizontalAdapter(@NonNull final Context context, @NonNull final ContactCallback contactCallback) {
        this.context = context;
        this.contactCallback = contactCallback;
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
        holder.checkImageView.setVisibility(View.INVISIBLE);
        holder.bind(contactList.get(position), contactCallback);
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    public static class ContactHorizontalViewHolder extends RecyclerView.ViewHolder {
        public ImageView contactAvatarImageView;
        public TextView contactNameTextView;
        public ImageView checkImageView;

        public ContactHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            contactAvatarImageView = itemView.findViewById(R.id.contact_avatar_image_view);
            contactNameTextView = itemView.findViewById(R.id.contact_name_text_view);
            checkImageView = itemView.findViewById(R.id.check_image_view);
        }

        void bind(final Contact contact, final ContactCallback contactCallback) {
            itemView.setOnClickListener(v -> {
                contactCallback.onHorizontalContactSelected(contact, this);
            });
        }
    }

    private static final String TAG = ContactListHorizontalAdapter.class.toString();
}
