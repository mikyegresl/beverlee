package uz.alex.its.beverlee.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Contact;
import uz.alex.its.beverlee.view.interfaces.ContactCallback;

public class ContactListVerticalAdapter extends RecyclerView.Adapter<ContactListVerticalAdapter.ContactVerticalViewHolder> {
    private static final String TAG = ContactListVerticalAdapter.class.toString();
    private List<Contact> contactList;
    private Context context;
    private ContactCallback contactCallback;

    public ContactListVerticalAdapter(@NonNull final Context context, @NonNull final ContactCallback contactCallback) {
        this.context = context;
        this.contactCallback = contactCallback;
    }

    public void setContactList(@NonNull final List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactVerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(context).inflate(R.layout.contact_vertical_view_holder, parent, false);
        return new ContactVerticalViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVerticalViewHolder holder, int position) {
        holder.contactNameTextView.setText(contactList.get(position).getName());
        holder.checkImageView.setVisibility(View.INVISIBLE);

        if (contactList.size() <= 3) {
            holder.starImgView.setVisibility(View.VISIBLE);
        }
        else {
            holder.starImgView.setVisibility(View.INVISIBLE);
        }
        holder.bind(contactList.get(position), contactCallback);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ContactVerticalViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    public static class ContactVerticalViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout viewHolderLayout;
        public TextView contactNameTextView;
        public ImageView avatarImageView;
        public ImageView checkImageView;
        public ImageView starImgView;

        ContactVerticalViewHolder(@NonNull View itemView) {
            super(itemView);
            viewHolderLayout = itemView.findViewById(R.id.view_holder_layout);
            contactNameTextView = itemView.findViewById(R.id.contact_name);
            avatarImageView = itemView.findViewById(R.id.contact_avatar);
            checkImageView = itemView.findViewById(R.id.check_image_view);
            starImgView = itemView.findViewById(R.id.star_img_view);
        }

        void bind(final Contact contact, final ContactCallback contactCallback) {
            itemView.setOnClickListener(v -> {
                contactCallback.onContactSelect(contact, this);
            });
        }
    }
}
