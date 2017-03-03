/**
 * AirportInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-02 11:52:12 Created By zhangpengfei
*/
package com.zhiweicloud.guest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * AirportInfo.java
 * Copyright(C) 2016 杭州量子金融信息服务有限公司
 * https://www.zhiweicloud.com
 * 2017-03-02 11:52:12 Created By zhangpengfei
*/
@ApiModel(value="AirportInfo",description="airport_info")
public class AirportInfo {
    @ApiModelProperty(value="名称",name="name")
    private String name;

    @ApiModelProperty(value="编码",name="code")
    private String code;

    @ApiModelProperty(value="洲",name="continent")
    private String continent;

    @ApiModelProperty(value="国家",name="country")
    private String country;

    @ApiModelProperty(value="城市",name="city")
    private String city;

    @ApiModelProperty(value="纬度",name="latitude")
    private String latitude;

    @ApiModelProperty(value="经度",name="longitude")
    private String longitude;

    @ApiModelProperty(value="洲id",name="continentId")
    private String continentId;

    @ApiModelProperty(value="国家id",name="countryId")
    private String countryId;

    @ApiModelProperty(value="城市id",name="cityId")
    private String cityId;

    @ApiModelProperty(value="高铁，机场",name="type")
    private String type;

    @ApiModelProperty(value="境内外",name="inOrOut")
    private String inOrOut;

    @ApiModelProperty(value="排序",name="sort")
    private String sort;

    @ApiModelProperty(value="三字码",name="airportCode")
    private String airportCode;

    @ApiModelProperty(value="",name="fdPinyin")
    private String fdPinyin;

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 编码
     * @return code 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 编码
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 洲
     * @return continent 洲
     */
    public String getContinent() {
        return continent;
    }

    /**
     * 洲
     * @param continent 洲
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * 国家
     * @return country 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 国家
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 城市
     * @return city 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 城市
     * @param city 城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 纬度
     * @return latitude 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 纬度
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 经度
     * @return longitude 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 经度
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 洲id
     * @return continent_id 洲id
     */
    public String getContinentId() {
        return continentId;
    }

    /**
     * 洲id
     * @param continentId 洲id
     */
    public void setContinentId(String continentId) {
        this.continentId = continentId;
    }

    /**
     * 国家id
     * @return country_id 国家id
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * 国家id
     * @param countryId 国家id
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     * 城市id
     * @return city_id 城市id
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 城市id
     * @param cityId 城市id
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     * 高铁，机场
     * @return type 高铁，机场
     */
    public String getType() {
        return type;
    }

    /**
     * 高铁，机场
     * @param type 高铁，机场
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 境内外
     * @return in_or_out 境内外
     */
    public String getInOrOut() {
        return inOrOut;
    }

    /**
     * 境内外
     * @param inOrOut 境内外
     */
    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }

    /**
     * 排序
     * @return sort 排序
     */
    public String getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * 三字码
     * @return airport_code 三字码
     */
    public String getAirportCode() {
        return airportCode;
    }

    /**
     * 三字码
     * @param airportCode 三字码
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * 
     * @return FD_PINYIN 
     */
    public String getFdPinyin() {
        return fdPinyin;
    }

    /**
     * 
     * @param fdPinyin 
     */
    public void setFdPinyin(String fdPinyin) {
        this.fdPinyin = fdPinyin;
    }
}