package com.sungness.core.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 列表页分页类
 * Created by wanghongwei on 4/4/16.
 */
public class Pagination {
    private Logger logger = LoggerFactory.getLogger(Pagination.class);

    /** 默认每页条数 */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /** 每页最大条数 */
    private static final int MAX_PAGE_SIZE = 100;

    /** 显示页数（快捷翻页模式） */
    private static final int SHOW_PAGE_COUNT = 10;

    /** 请求的URI地址 */
    private String requestURI;

    /** 每页显示条数 */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /** 前一次请求的每页条数 */
    private int oldPageSize = DEFAULT_PAGE_SIZE;

    /** 当前页号 */
    private int pageNumber = 1;

    /** 总条数 */
    private int totalCount;

    /** 总页数 */
    private int totalPage;

    /** 过滤参数字符串 */
    private String filterString;

    public Pagination() {
    }


    /**
     * 获取起始位置
     * @return int 查询数据库用的起始位置
     */
    public int getOffset() {
        return getThisPageFirstElementNumber() - 1;
    }

    /**
     * 获取当前页URL地址
     * @return String 当前页URL地址
     */
    public String getCurrentPageURL() {
        return getPageURL(getPageNumber());
    }

    public String getEncodedCurrentPageURL() {
        try {
            return URLEncoder.encode(getCurrentPageURL(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCurrentPageURL();
    }

    /**
     * 获取第一页URL地址
     */
    public String getFirstPageURL() {
        return getPageURL(1);
    }

    /**
     * 上一页
     */
    public String getPrevPageURL() {
        return getPageURL(getPageNumber() - 1);
    }

    /**
     * 下一页
     */
    public String getNextPageURL() {
        return getPageURL(getPageNumber() + 1);
    }

    /**
     * 最后一页
     */
    public String getLastPageURL() {
        return getPageURL(getTotalPage());
    }

    private String getPageURL(int pageNumber) {
        String url = String.format("%s?pagination.pageNumber=%s&pagination.pageSize=%s",
                this.requestURI, pageNumber, getPageSize());
        if (StringUtils.isNotBlank(this.filterString)) {
            url += "&" + this.filterString;
        }
        return url;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0 && pageSize <= MAX_PAGE_SIZE) {
            this.pageSize = pageSize;
        }
    }

    /**
     * 解析request请求中的翻页参数
     *
     * @param requestURI String
     */
    public void parse(String requestURI) {
        this.requestURI = requestURI;
        logger.debug("parse this.requestURI=" + this.requestURI);
        if (pageSize != oldPageSize) {
            int first = pageNumber > 0 ? (pageNumber - 1) * oldPageSize : 0;
            pageNumber = first / this.pageSize + 1;
            oldPageSize = pageSize;
        }
    }

    /**
     * 是否是首页
     *
     * @return boolean 是返回 true，否返回false
     */
    public boolean isFirstPage() {
        return getPageNumber() <= 1;
    }

    /**
     * 是否是尾页
     *
     * @return boolean 是返回 true，否返回false
     */
    public boolean isLastPage() {
        return getPageNumber() == getLastPage();
    }

    /**
     * 判断是否存在下一页
     *
     * @return boolean 是返回 true，否返回false
     */
    public boolean hasNextPage() {
        return getLastPage() > getPageNumber();
    }

    /**
     * 判断是否存在上一页
     *
     * @return boolean 是返回 true，否返回false
     */
    public boolean hasPreviousPage() {
        return getPageNumber() > 1;
    }

    /**
     * 获取总条数
     *
     * @return int 总条数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总条数，需要重新计算总页数
     * @param count int 总条数
     */
    public void setTotalCount(int count) {
        this.totalCount = count;
        int page = this.totalCount / this.pageSize;
        int tmp = this.totalCount % this.pageSize;
        this.totalPage = page + (tmp == 0 ? 0 : 1);
        if (this.pageNumber > this.totalPage) {
            this.pageNumber = this.totalPage;
        }
    }

    /**
     * 根据设置的显示页数，计算出页码列表。
     * @return List<Integer> 页码列表，从小到大
     */
    public List<Integer> getPageList() {
        List<Integer> pageList = new ArrayList<>();
        if (pageNumber > 0 && totalPage >= pageNumber) {
            pageList.add(pageNumber);
            int remainedPage = totalPage - pageNumber;
            int preCount = SHOW_PAGE_COUNT - 1;
            if (remainedPage >= SHOW_PAGE_COUNT / 2) {
                preCount -= SHOW_PAGE_COUNT / 2;
            } else {
                preCount -= remainedPage;
            }
            for (int i = pageNumber - 1; i >= 1 && preCount > 0; i--, preCount--) {
                pageList.add(0, i);
            }
            int afterCount = SHOW_PAGE_COUNT - pageList.size();
            for (int j = pageNumber + 1; j <= totalPage && afterCount > 0; j++, afterCount--) {
                pageList.add(j);
            }
        }
        return pageList;
    }

    /**
     * 获取总页数数
     *
     * @return int 总页数
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 获取下一页页码
     *
     * @return int 下一页页码
     */
    public int getNextPage() {
        return getPageNumber() + 1;
    }

    /**
     * 获取上一页页码
     *
     * @return int 上一页页码
     */
    public int getPreviousPage() {
        return getPageNumber() - 1;
    }

    /**
     * 获取最后一页页码
     *
     * @return int 最后一页页码，同getTotalPage()
     */
    public int getLastPage() {
        return getTotalPage();
    }

    /**
     * 获取当前请求的URI
     *
     * @return String 当前URI字符串
     */
    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    /**
     * 获取翻页参数字符串
     * 格式：param1=value1&param2=value2
     *
     * @return String 参数字符串
     */
    public String getPagingParams() {
        return String.format("pagination.pageNumber=%s&pagination.pageSize=%s",
                getPageNumber(), getPageSize());
    }

    public int getThisPageFirstElementNumber() {
        return (getPageNumber() - 1) * getPageSize() + 1;
    }

    public int getThisPageLastElementNumber() {
        int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
        return getTotalCount() < fullPage ? getTotalCount() : fullPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public boolean isCurrentPage(String pageNumber) {
        return pageNumber != null && pageNumber.equals("" + this.pageNumber);
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber > 0 ? pageNumber : 1;
    }

    public String getFilterString() {
        return filterString;
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public int getOldPageSize() {
        return oldPageSize;
    }

    public void setOldPageSize(int oldPageSize) {
        if (oldPageSize > 0 && oldPageSize <= MAX_PAGE_SIZE) {
            this.oldPageSize = oldPageSize;
        }
    }
}
