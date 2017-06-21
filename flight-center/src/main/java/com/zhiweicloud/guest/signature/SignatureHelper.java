package com.zhiweicloud.guest.signature;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tc on 2017/6/21.
 */
public class SignatureHelper {

    public static void main(String[] args) throws Exception {
//        RSACoder.initKeyFile("C://tc");
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMWwKU5bgiygdZrK7Y6e3O+t8A2JZq1fIeAsf29RAQW6XnTwGyrWfj8NLOv07lTZuLy5srIsETedlUElrwZvT1boDY+y3ar7AlcwXbAx2czveSkkmFEe5CEaxTKG0C58WNicqiDA8HpLPKHvWU+vE0Xs+MObtq1SbIxUM18gUkYlAgMBAAECgYEAr8YS9DkpZcTGzPvifg2DJRnjeCXTxhPwawMlzDSaVB2CHzfe8BgH6MguSR9fStLMEAKNgR7tsCXNsD9xgKc4QtqnBq7sh7yfy8WCZJ8ZurqxECdyFhy7PyJsWKh+iOP+MeKVRodj+cQvoRquVmkw8JrwE6fdveKlX97WO8BhI4ECQQD+1Sw39tcp4L/ZH1/hNqplgMWiZuFpchOh1HZEdtWfJyx1stKFRPwNTX5EeNxu5DIz5Kjy6UrYf/0ZKuoJeiS7AkEAxpf6gmigLt3LMTlZTkEtIis7VcJjIcIP31/RHUYmzkLeMi5frg69L9WrtKZQkGyJ5nvKJCgs6awpbj+W3sQCnwJACWbwFLiEw8TJD0e7D+Imc0xG/nDxTCSrWMMwAeKoixC08TrBzaz3572+NOSIrYa523RKT3wQCODgA7ezntnk1QJAPoLWOrheO4JbFEp9/vDrGQdMrQj8FUM1pdVOSI2tZ12K/Xa1bV98U5NJchqr31oa6i7rCQiQYplrjSV9Au/l9wJBAJbIDysW6iEL+/aibOEryZtpB1IX58HqH3S/fSdp6gtYHk4mYMRZm2RT/MxvBcydvbIGXCO5o5kW0psPTE6XC/M=";
        Map<String, Object> params = new HashMap<>();
        params.put("sysCode", "shuttle_bus");
        params.put("flightNo", "MU2474");
        params.put("depDate", "2017-06-22");
        String sign = RSACoder.signToStr(params, privateKey);
        System.out.println(sign);
    }

}
