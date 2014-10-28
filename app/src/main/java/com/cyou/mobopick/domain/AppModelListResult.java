package com.cyou.mobopick.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chengfei on 14-10-20.
 */
public class AppModelListResult {
    @SerializedName(value = "totalrow")
    public int totalrow;
    @SerializedName(value = "pagecurrent")
    public int pageCurrent;
    @SerializedName(value = "pagesize")
    public int pageSize = PAGE_SIZE;
    @SerializedName(value = "listdata")
    public List<AppModel> appModels;

    public static final int PAGE_SIZE = 30;


    public boolean hasMore() {
        boolean hasMore = true;
        hasMore &= (totalrow / pageSize) + (totalrow % pageSize) > pageCurrent;
        return hasMore;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppModelListResult that = (AppModelListResult) o;

        if (pageCurrent != that.pageCurrent) return false;
        if (pageSize != that.pageSize) return false;
        if (totalrow != that.totalrow) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = totalrow;
        result = 31 * result + pageCurrent;
        result = 31 * result + pageSize;
        return result;
    }
}
