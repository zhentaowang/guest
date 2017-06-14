package com.zhiweicloud.guest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by tc on 2017/6/14.
 */
@Configuration()
@Import({SpringConfiguration.class})
public class BaseAttributeConfig {

    public static String customFlightUrl;

    public static String ibeHost;

    public static String ibeHashCode;

    public static String juheKey;

    public static String juheHost;

    /**
     * 蒙奇·D·风数
     */
    public static String webhookTokenMonteDFengshu;

    public String getCustomFlightUrl() {
        return customFlightUrl;
    }

    @Value("${flight.custom.url}")
    public void setCustomFlightUrl(String customFlightUrl) {
        this.customFlightUrl = customFlightUrl;
    }

    public String getIbeHost() {
        return ibeHost;
    }

    @Value("${ibe.host}")
    public void setIbeHost(String ibeHost) {
        this.ibeHost = ibeHost;
    }

    public String getIbeHashCode() {
        return ibeHashCode;
    }

    @Value("${ibe.hashcode}")
    public void setIbeHashCode(String ibeHashCode) {
        this.ibeHashCode = ibeHashCode;
    }

    public String getJuheKey() {
        return juheKey;
    }

    @Value("${juhe.key}")
    public void setJuheKey(String juheKey) {
        BaseAttributeConfig.juheKey = juheKey;
    }

    public String getJuheHost() {
        return juheHost;
    }

    @Value("${juhe.host}")
    public void setJuheHost(String juheHost) {
        BaseAttributeConfig.juheHost = juheHost;
    }

    public String getWebhookTokenMonteDFengshu() {
        return webhookTokenMonteDFengshu;
    }

    @Value("${webhook.token.monte.d.fengshu}")
    public void setWebhookTokenMonteDFengshu(String webhookTokenMonteDFengshu) {
        BaseAttributeConfig.webhookTokenMonteDFengshu = webhookTokenMonteDFengshu;
    }

}
