package uz.alex.its.beverlee.model.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import uz.alex.its.beverlee.model.News;

public class NewsResponse {
    @Expose
    @SerializedName("draw")
    private final long draw;

    @Expose
    @SerializedName("recordsTotal")
    private final int recordsTotal;

    @Expose
    @SerializedName("recordsFiltered")
    private final int recordsFiltered;

    @Expose
    @SerializedName("data")
    private final List<News> newsList;

    public NewsResponse(final long draw, final int recordsTotal, final int recordsFiltered, final List<News> newsList) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.newsList = newsList;
    }

    public long getDraw() {
        return draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    @NonNull
    @Override
    public String toString() {
        return "NewsResponse{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", newsList=" + newsList +
                '}';
    }
}
