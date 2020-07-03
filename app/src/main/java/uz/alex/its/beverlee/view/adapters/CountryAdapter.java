package uz.alex.its.beverlee.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uz.alex.its.beverlee.R;
import uz.alex.its.beverlee.model.Country;

public class CountryAdapter extends ArrayAdapter<Country> {
    private static final String TAG = CountryAdapter.class.toString();

    private final Context context;
    private final int layout_resource;
    private final Map<Country, String> countryMap;
    private final List<Country> countryList;
    private final List<Country> suggestions;
    private final List<Country> tempList;

    private Filter countryFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            final Country country = (Country) resultValue;
            return country.getCountry();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (final Country country : tempList) {
                    if (country.getCountry().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(country);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count  = suggestions.size();
                return filterResults;
            }
            return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Country> tempValues = (ArrayList<Country>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (final Country country : tempValues) {
                    add(country);
                }
                notifyDataSetChanged();
            }
            else {
                clear();
                notifyDataSetChanged();
            }
        }
    };

    public CountryAdapter(@NonNull Context context, int resource, final Map<Country, String> countryMap) {
        super(context, resource);
        this.context = context;
        this.layout_resource = resource;
        this.countryMap = countryMap;
        this.countryList = new ArrayList<>(countryMap.keySet());
        this.suggestions = new ArrayList<>();
        this.tempList = new ArrayList<>(countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = convertView;
        try {
            if (convertView == null) {
                root = LayoutInflater.from(context).inflate(layout_resource, parent, false);
            }
            final Country countryItem = getItem(position);
            final TextView countryNameTextView = (TextView) root.findViewById(R.id.country_name_text_view);
            final ImageView countryFlagImageView = root.findViewById(R.id.country_flag_image_view);
            countryNameTextView.setText(countryItem.getCountry());
            countryFlagImageView.setImageResource(countryItem.getResourceId());
        }
        catch (Exception e) {
            Log.e(TAG, "getView(): ", e);
        }
        return root;
    }

    @Nullable
    @Override
    public Country getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @Override
    public int getCount() {
        return countryList.size();
    }
}
