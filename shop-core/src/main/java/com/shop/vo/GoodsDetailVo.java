package com.shop.vo;

/**
 * Created by TW on 2017/7/17.
 */
public class GoodsDetailVo extends GoodsVo {

//    t.id,
//    t.`name`,
//    t.caption,
//    t.sn,
//    t.price,
//    t.market_price,
//    t.unit,
//    t.type,
//    t.product_images,
//    t.specification_items,
//    t.parameter_values,
//    t.introduction,
//    t.product_category,
//    t.image

    private String parameterValues;
    private String productImages;
    private Integer productCategory;
    private String specificationItems;
    private String sn;
    private String introduction;
    /** 类型 0=普通商品 1=积分兑换 2=赠品*/
    private Integer type;
    private String unit;

    public String getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(String parameterValues) {
        this.parameterValues = parameterValues;
    }

    public String getProductImages() {
        return productImages;
    }

    public void setProductImages(String productImages) {
        this.productImages = productImages;
    }

    public Integer getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Integer productCategory) {
        this.productCategory = productCategory;
    }

    public String getSpecificationItems() {
        return specificationItems;
    }

    public void setSpecificationItems(String specificationItems) {
        this.specificationItems = specificationItems;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
