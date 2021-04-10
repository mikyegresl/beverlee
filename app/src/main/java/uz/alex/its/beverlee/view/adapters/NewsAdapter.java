package uz.alex.its.beverlee.view.adapters;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.News;
import uz.alex.its.beverlee.utils.DateFormatter;
import uz.alex.its.beverlee.view.UiUtils;
import uz.alex.its.beverlee.view.interfaces.NewsCallback;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final Context context;
    private final NewsCallback callback;

    private List<News> newsList;

    public NewsAdapter(final Context context, final NewsCallback callback) {
        this(context, callback, null);
    }

    public NewsAdapter(final Context context, final NewsCallback callback, final List<News> newsList) {
        this.context = context;
        this.callback = callback;
        this.newsList = newsList;
    }

    public void setNewsList(final List<News> newsList) {
        this.newsList = newsList;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_news_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.titleTextView.setText(Html.fromHtml(newsList.get(position).getTitle(), Html.FROM_HTML_MODE_COMPACT));
            holder.descriptionTextView.setText(Html.fromHtml(newsList.get(position).getDescription(), Html.FROM_HTML_MODE_COMPACT));
        }
        else {
            holder.titleTextView.setText(Html.fromHtml(newsList.get(position).getTitle()));
            holder.descriptionTextView.setText(Html.fromHtml(newsList.get(position).getDescription()));
        }
        holder.dateTextView.setText(DateFormatter.timestampToStringDate(newsList.get(position).getCreatedAt()));

        holder.bindItem(callback, position, newsList.get(position));
        holder.bindMoreTextView(callback, position);

        Picasso.get()
                .load(newsList.get(position).getPhotoUrl())
                .centerCrop()
                .fit()
                .into(holder.bannerImageView);

        if (position == 0) {
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.leftMargin = 80;
            holder.itemView.setLayoutParams(params);
            return;
        }
        if (position == newsList.size() - 1) {
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.rightMargin = 80;
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView bannerImageView;
        TextView titleTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        TextView moreTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerImageView = itemView.findViewById(R.id.news_header);
            titleTextView = itemView.findViewById(R.id.news_title_text_view);
            dateTextView = itemView.findViewById(R.id.news_date_text_view);
            descriptionTextView = itemView.findViewById(R.id.news_description_text_view);
            moreTextView = itemView.findViewById(R.id.more_text_view);
        }

        void bindMoreTextView(final NewsCallback callback, final int position) {
            moreTextView.setOnClickListener(v -> {
                callback.expandNewsItem(position);
            });
        }

        void bindItem(final NewsCallback callback, final int position, final News news) {
            itemView.setOnClickListener(v -> {
                callback.onNewsSelected(position, news);
            });
        }
    }

    private static final String TAG = NewsAdapter.class.toString();
}
