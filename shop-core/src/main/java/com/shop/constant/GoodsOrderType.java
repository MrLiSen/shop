package com.shop.constant;

/**
 * Created by TW on 2017/7/16.
 */
public enum GoodsOrderType {

    is_top("is_top.desc", "置顶降序"),
    priceasc("price.asc", "价格升序"),
    pricedesc("price.desc", "价格降序"),
    sales("sales.desc", "销量降序"),
    score("score.desc", "评分降序"),
    create_date("create_date.desc", "日期降序");

    private GoodsOrderType(String sort, String sortValue) {
        this.sort = sort;
        this.sortValue = sortValue;
    }

    /**
     * 根据sort获取枚举对象
     * @param sort
     * @return
     */
    public static GoodsOrderType findBySort(String sort) {
        for(GoodsOrderType type: GoodsOrderType.values()) {
            if (type.getSort().equals(sort)) {
                return type;
            }
        }
        return null;
    }

    private String sort;
    private String sortValue;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }
}
