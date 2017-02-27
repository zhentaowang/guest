package com.zhiweicloud.guest.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CommonUseEntity implements Serializable {
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String MAXID = "maxId";
    public static final String _ID = "_id";
    public static final String COMPANY = "company";
    public static final String COMPANY_CACHE = "company_cache";
    public static final String COMPANY_ID = "company_id";
    public static final String AUTH_HOST="http://airport.zhiweicloud.com";
    public static final String DEV_AUTH_HOST="http://121.43.184.1:8081";
    public static final String DEV_OAUTH_PERMISSION_URL = DEV_AUTH_HOST+"/user/permission";
    public static final String DEV_OAUTH_GET_USER = DEV_AUTH_HOST+"/user/getUser";
    public static final String DEV_OAUTH_SET_USER_STATUS = DEV_AUTH_HOST+"set/user/status";

    public static final String OAUTH_PERMISSION_URL = AUTH_HOST+"/user/permission";
    public static final String OAUTH_GET_USER = AUTH_HOST+"/oauth/oauth/access_token";
    public static final String OAUTH_SET_USER_STATUS = AUTH_HOST+"set/user/status";
    public static final String SHAREHOLDER_TYPE = "shareholder_type";
    public static final String PROVINCE_CITY = "province_city";
    public static final String PROVINCE_ID = "province_id";
    public static final String PROVINCE_NAME = "province_name";
    public static final String PROVINCE = "province";
    public static final String REGISTER_PROVINCE = "register_province";
    public static final String REGISTER_PROVINCE_ID = "register_province_id";
    public static final String LEVEL1 = "level1";
    public static final String LEVEL2 = "level2";
    public static final String LEVEL3 = "level3";
    public static final String LEVEL2_ID = "level2_id";
    public static final String LEVEL3_ID = "level3_id";
    public static final String INDUSTRY = "industry";
    public static final String PARENT_INDUSTRY = "parentIndustry";
    public static final String TYPE = "type";
    public static final String ESTABLISH_PERIOD = "establish_period";
    public static final String ESTABLISH_PERIOD_ID = "establish_period_id";
    public static final String REGISTER_STATUS = "register_status";
    public static final String REGISTER_STATUS_ID = "register_status_id";
    public static final String REGISTER_CAPITAL_RANGE = "register_capital_range";
    public static final String REGISTER_CAPITAL_RANGE_ID = "register_capital_range_id";
    public static final String REGISTER_CITY_ID = "register_city_id";
    public static final String INDUSTRY_LEVEL1_ID = "industry_level1_id";
    public static final String INDUSTRY_LEVEL2_ID = "industry_level2_id";
    public static final String INDUSTRY_LEVEL1_NAME = "industry_level1_name";
    public static final String INDUSTRY_LEVEL2_NAME = "industry_level2_name";
    public static final String CHILDREN = "children";
    public static final String SELECTED = "selected";
    public static final String STATUS = "status";
    public static final String COMPANYLIST = "companyList";
    public static final String COMPANYPORTRAIT = "companyPortrait";
    public static final String VALUE = "value";
    public static final String COUNT = "count";
    public static final String BREAK_COUNT = "break_count";
    public static final String SHAREHOLDER = "shareholder";
    public static final String KEY = "key";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json;charset=utf-8";
    public static final String POST = "POST";
    public static final String COLON_SIGN = ":";
    public static final String EQUAL_SIGN= "=";
    public static final String CONNECTION_SIGN= "&";
    public static final String QUESTION_SIGN= "?";
    public static final String EMPTYJSON = "{}";
    public static final String TYPE_ID = "type_id";
    public static final String DATA_TIME = "datatime";
    public static final String MEASURE = "measure";
    public static final String INDUSTRY_TYPY_VERSION = "gb4754-2011";
    public static final String INDUSTRY_LIST = "industryList";
    public static final String LOCATION_COUNT = "locationCount";
    public static final String SELECT_LIKE = "%";
    public static final String SERIES = "series";
    public static final String RANK = "rank";
    public static final String EVOLUTION = "evolution";
    public static final String HORIZONTAL = "horizontal";
    public static final String VELTICAL = "vertical";
    public static final String DEFAULT_HORIZONTAL = "资产负债率";
    public static final String DEFAULT_VELTICAL = "资产总计";
    public static final String DEFAULT_COUNT = "企业单位数";
    public static final String EMPTYSTRING = "";
    public static final String TAGS_MAP = "tags:map:";
    public static final String SELECTEDTAGS = "selectedTags";
    public static final String SIMILARCOMPANY = "similarCompany";
    public static final String ACCESSTOKEN = "access_token";
    public static final String AIRPORTCODE = "airportCode";
    public static final String PERMISSION = "permission";
    public static final String ERROR = "error";
    public static final String USER_ID = "user_id";
    public static final String EXPIRE_TIME = "expire_time";
    public static final int USER_VERIFY_STATUS = 1;
    public static final int USER_LIMIT_STATUS = 2;
    public static final int NORMALSTATUS = 1;
    public static final int ABNORMALSTATUS = 2;
    public static final int NO_AUTHORITY_STATUS= 3;
    public static final int DELAY_STATUS = 4;
    public static final int LIMITE_DACCESS_STATUS = 5;
    public static final int VERIFYSTATUS = 6;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int MINUSONE = -1;
    public static final int FIVE = 5;
    public static final int TEN = 10;
    public static final int TWENTY  = 20;
    public static final int FIFTY  = 50;
    public static final int HUNDRED  = 100;
    public static final int NUMBER_2000 = 2000;
    public static final String ERROR_CODE = "error_code:";
    public static final String ERROR_STATUS = "error_status:";
    public static final String ERROR_MESSAGE = "error_message:";
    public static final String CONNECTION_ERROR = "Connection error, should retry later";
    public static final String EMPTY_ACCESS_TOKEN = "accessToken is null";
    public static final String LIMITED_ACCESS_ERROR = "access is limited, should retry later";
    public static final String NEED_VERIFY_ERROR = "accessToken needs to verify";
    public static final String UPSTREAM = "upstream";
    public static final String UP_TYPE = "up_type";
    public static final String DOWNSTREAM = "downstream";
    public static final String DOWN_TYPE = "down_type";
    public static final String TAG = "tag";
    public static final String TAGNAME = "tagName";
    public static final String TAGVALUE = "tagValue";
    public static final String TAGID = "tagId";
    public static final String TAG_NAME = "tag_name";
    public static final String TAG_TYPE = "tag_type";
    public static final String SCORE = "score";
    public static final String UP_STREAM = "投入";
    public static final String DOWN_STREAM = "产出";
    public static final String SOUERCE = "source";
    public static final String _SOUERCE = "_source";
    public static final String TARGET = "target";
    public static final String CATE = "cate";
    public static final String CATEGORY = "category";
    public static final String NODES = "nodes";
    public static final String LINKS = "links";
    public static final String COMPANY_NAME = "company_name";
    public static final String LEADER = "leader";
    public static final String INVESTMENT = "investment";
    public static final String BRANCH = "branch";
    public static final String  SHAREHOLDERNAME= "股东";
    public static final String  INVESTMENTNAME= "对外投资";
    public static final String  CUSTOMERNAME= "客户";
    public static final String  SUPPLIERNAME= "供应商";
    public static final String  BRANCHNAME= "分公司";
    public static final String  LEADERNAME= "主要成员";
    public static final String  TYPEHOLDER1= "自然人股东";
    public static final String  TYPEHOLDER2= "自然人";
    public static final String  SHAREHOLDERCOMPANYTYPE1="企业法人";
    public static final String  SHAREHOLDERCOMPANYTYPE2="企业投资";
    public static final String  SHAREHOLDERCOMPANYTYPE3="有限公司";
    public static final String  SUSRELATIONSHIP= "疑似关系";
    public static final String  URL= "URL";
    public static final String  QUERY_PARAMETERS= "queryParameters";
    public static final String  METHOD= "method";
    public static final String  DATA= "data";
    public static final String  USERID= "userid";
    public static final String  HITS= "hits";
    public static final String  OFFSET= "offset";
    public static final String  SIZE= "size";
    public static final String  SELECTTAG= "selectTag";
    public static final String  KEYWORDS= "keywords";
    public static final String  AGGREGATIONS= "aggregations";
    public static final String  PORTRAITCOUNT= "portraitCount";
    public static final String  DOC_COUNT= "doc_count";
    public static final String  BOOL= "bool";
    public static final String  MUST= "must";
    public static final String  MUST_NOT= "must_not";
    public static final String  TERMS= "terms";
    public static final String  TERM= "term";
    public static final String  BUCKETS= "buckets";
    public static final String  TOTAL= "total";
    public static final String  AMOUNT_ID= "amount_id";
    public static final String  BIDDING= "bidding";
    public static final String  BIDDED= "bidded";
    public static final String  BIDS_CACHE= "bids_cache";
    public static final String  BID_AMOUNT= "bid_amount";
    public static final String  AMOUNT= "amount";
    public static final String  BIDDING_COMPANY= "bidding_company";
    public static final String  BID_LOCATION= "bid_location";
    public static final String  BID_TYPE= "bid_type";
    public static final String  BID_DATE= "bid_date";
    public static final String  CITY_ID= "city_id";
    public static final String  CITY_NAME= "city_name";
    public static final String  TYPE_LEVEL2_ID= "type_level2_id";
    public static final String  TYPE_LEVEL1_ID= "type_level1_id";
    public static final String  WEIZHI= "未知";
    public static final String SIMILARBIDDING = "similarBidding";
    public static final String BIDDINGLIST = "biddingList";
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public static final String LABLE = "label";
    public static final String BUSINESS = "business";
    public static final String IS_NEED_REDISCACHE = "is_need_rediscache";
    public static final String REDISKEY = "redisKey";
    public static final String GET = "GET";
    public static final String REDISCACHEPREFIX = "cache:";
    public static final String RISKFREQUENCYPREFIX = "riskFrequency:";
    public static final String RISKFBLACKLISTPREFIX = "riskBlacklist:";
    public static final String ENTITY_STREAM_DATA = "entityStreamData";
    public static final int AUTH_FILTER_PRIORITY = 1000;
    public static final int RISK_FILTER_PRIORITY = 1001;
    public static final int REDISCACHE_FILTER_PRIORITY = 1002;
    public static final String NAME_LENGTH = "name.length";
    public static final String RELATE_BID_MINMATCH = "30%";
    public static final String TEXT_PLAIN_UTF_8 = "text/plain;charset=utf8";
    public static final String NEWS_SOURCE = "news_source";
    public static final String TEXT = "text";
    public static final String LEVEL1_ID = "level1_id";
    public static final String LEVEL2_IDS = "level2_ids";
    public static final String COMPANY_INFO = "company_info";
    public static final String CLASSIFY = "classify";
    public static final String CLASSIFY_ID = "classify_id";
    public static final String DOCUMENTS = "documents";
    public static final String UID = "UID";
    public static final String BID_COUNT = "bid_count";
    public static final String BID_COUNT_ID = "bid_count_id";
    public static final String EXECUTOR_COUNT = "executor_count";
    public static final String INVESTMENT_COUNT = "investment_count";
    public static final String RELATEDCOMPANY = "相关企业";
    public static final String RELATEDCOMPANY1 = "预付关系";
    public static final String RELATEDCOMPANY2 = "关联担保";
    public static final String RELATEDCOMPANY3 = "关联交易";
    public static final String RELATEDCOMPANY4 = "应收关系";
    public static final String  DAY= "day";
    public static final String  BID_DATE_DAY= "bid_date_day";
    public static final String  JOB_TITLE= "job_title";
    public static final String  PEOPLE_INFO= "people_info";
    public static final String  INDEX= "index";
    public static final String  WEIGHT= "weight";
    public static final String  FILTER_KEYWORDS= "filter_keywords";
    public static final String  LEGAL_PERSON= "legal_person";
    public static final String  COMPANY_SHORT_NAME= "company_short_name";
    public static final String  NAME_COMPANY_ID= "name_company_id";
    public static final String  ROOT= "root";
    public static final String  REQUESTNUM= "requestNum";
    public static final String  BREAKPROMISERECORDLIST= "breakPromiseRecordList";
    public static final String  VERDICTRECORDLIST= "VerdictRecordList";
    public static final String  VERDICTRECORD= "VerdictRecord";
    public static final String PHRASE = "phrase";
    public static final String NEWS = "news";
    public static final String BIDS = "bids";
    public static final String USER_TAG = "user_tag";
    public static final String IS_VIP = "is_vip";
    public static final String REQUEST_TIME="request_time";
    public static final String RESPONSE_TIME="response_time";
    public static final String BACKEND_TIME="backend_time";
    public static final String USER_IP="user_ip";
    public static final String IMAGEURL = "imageUrl";
    public static final String USER = "user";
    public static final String COLLECTIONS = "collections";
    public static final String NOT_ONDEMAND = "not ondemand";
    public static final String BREAK = "break";
    public static final String VERDICT = "verdict";

    public static final Map<String, Integer> TIME_TYPE = new HashMap<String, Integer>() {
        {
            put("min", 60);
            put("hour", 3600);
            put("day", 3600*24);
        }
    };
    public static final Map<String, Boolean> NOT_NEED_LIMIT_URL = new HashMap<String, Boolean>() {
        {
           /* put("api/news/relateIndustry/list", true);
            put("api/homepage/news/all_list", true);
            put("api/news/industry/list", true);*/
        }
    };

    public static final Map<String,String> stockMap = new HashMap<String,String>(){
        {
            put("code","hqzqdm");
            put("shortName","hqzqjc");
            put("incresRate","hqzdf");
            put("value","hqzjcj");
            put("incres","hqzd");
            put("currency","xxhbzl");
            put("transferWay","xxzrlx");
            put("tradeAmount","hqcjje");
            put("minValue","hqzdcj");
            put("maxValue","hqzgcj");
            put("ysCloseValue","hqzrsp");
            put("recentValue","hqzjcj");
            put("currentDate","hqjsrq");
            put("currentTime","hqgxsj");
            put("tdOpenValue","hqjrkp");
            put("tradeCount","hqcjsl");
        }
    };

    public static final Map<String,String> sanbanzuoshiMap = new HashMap<String,String>(){
        {
            put("minScore","DRZX");
            put("maxScore","DRZD");
            put("currentScore","SSZS");
            put("tradedCount","CJL");
            put("tradeAmount","CJJE");
            put("tdOpenScore","DRKP");
            put("ysCloseScore","ZRSP");
            put("curentIncreRate","ZDF");
            put("currentIncre","ZD");
            put("currentDate","JSRQ");
            put("currentTime","GXSJ");
        }
    };

    public static final Map<String,String> getSanbanchengzhiMap = new HashMap<String,String>(){
        {
            put("minValue","DRZX");
            put("maxValue","DRZD");
            put("tradedCount","CJL");
            put("tradedAmount","CJJE");
            put("openValue","DRKP");
            put("closeValue","DRSP");
            put("incresRate","ZDF");
            put("incres","ZD");
            put("date","JSRQ");
        }
    };

    public static final Map<Integer, Boolean> NOT_NEED_LIMIT_USER = new HashMap<Integer,Boolean>() {
        {
            put(33, true);
            put(8, true);
            put(37, true);
        }
    };

    public static final String CLASSIFY_NAME = "classify_name";
    public static final String FIELDS = "fields";
    public static final Map<String, Integer> COMPANY_TYPE = new HashMap<String, Integer>() {
        {
            put("上证", 1);
            put("深证", 1);
            put("三板", 3);
            put("四板", 4);
            put("未上市", 5);
        }
    };
    
    public static final String LASTEST_TIME = "latest_time";
    public static final String HIGHLIGHT = "highlight";
    public static final String USER_INTEREST = "user_interest";
    
    public static final String[] newsProperty = {"economic","finance","event","industry","xinsanban","internet","technology","military","automobile"};
    public static final String[] company_name_suffix_array = {"公司", "出版社", "台", "银行", "医院", "厂", "所", "厅", "局", "院", "中心", "大学", "学院", "集团", "社", "（有限合伙）"};
    public static final String[] people_name_blacklist = {"法人股", "个人股", "国家股", "职工股", "锁定股", "集资股", "流通股", "AAA", "管理", "公司", "社会", "公众", "股东", "自然人", "上市", "股份"};
    public static final String PEOPLE = "people";
    public static final String ORDER_BY = "order_by";
    public static final String TIME = "time";
    public static final String TOP_TIME = "top_time";
    public static final String FROM_IDS = "from_ids";
    public static final String DEV_ENV = "dev";
    public static final String PROD_ENV = "production";

    public static final String VIP_LEVEL = "role_id";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
