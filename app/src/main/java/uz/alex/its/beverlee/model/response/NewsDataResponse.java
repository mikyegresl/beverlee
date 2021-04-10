package uz.alex.its.beverlee.model.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import uz.alex.its.beverlee.model.News;
import uz.alex.its.beverlee.model.NewsData;

public class NewsDataResponse {
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
    private final List<NewsData> newsData;

    public NewsDataResponse(final long draw, final int recordsTotal, final int recordsFiltered, final List<NewsData> newsData) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.newsData = newsData;
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

    public List<NewsData> getNewsData() {
        return newsData;
    }

    @NonNull
    @Override
    public String toString() {
        return "NewsResponse{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", newsData=" + newsData +
                '}';
    }
}
