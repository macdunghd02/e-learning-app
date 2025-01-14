package com.mdd.ela.util;


import com.mdd.ela.util.constants.Constants;

public class PagingUtil {

    private PagingUtil() {
    }

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUM = 1;

    public static int getLimit(Integer pageSize) {
        if (pageSize == null) {
            return 0;
        }
        if (pageSize <= 0) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;

    }

    public static Integer getOffset(Integer pageSize, Integer pageNum) {
        if(pageSize == null){
            return null;
        }
        if (pageSize <= 0) {
            pageSize = Constants.DEFAULT_PAGE_SIZE;
        }
        if (pageNum < 1) {
            return 0;
        }
        return ((pageNum - 1) * pageSize);
    }

    public static Integer getAvailablePageNum(Integer pageNum, Integer pageSize, int total) {
        if(pageNum == null || pageSize == null){
            return null;
        }
        if (pageSize < 1 || pageNum < 1) {
            return 1;
        }
        if (pageSize * (pageNum - 1) >= total) {
            return 1;
        }
        return pageNum;
    }
}
