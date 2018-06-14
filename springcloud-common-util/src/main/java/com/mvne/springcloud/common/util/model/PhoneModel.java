package com.mvne.springcloud.common.util.model;

import lombok.Data;

@Data
public class PhoneModel {

	/** 省份名称 */  
    private String provinceName;  
  
    /** 城市名称 */  
    private String cityName;  
  
    /** 运营商：移动/电信/联通 */  
    private String carrier;  
}
