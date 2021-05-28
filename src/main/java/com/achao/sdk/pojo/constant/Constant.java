package com.achao.sdk.pojo.constant;

/**常量类
 * @author achao
 */

public interface Constant {
    int ORDER_FINISH = 3;
    int ORDER_DELIVERING = 2;
    int ORDER_CREATE = 1;

    String STORE_QUERY_RESULT = "store:query:result";
    String LOCATION_STORE = "location:store";

    Integer YES = 1;
    Integer NO = 0;

    /**
     * 默认的redis键过期时间
     */
    long REDIS_DEFOULT_EXPIRE_TIME = 60 * 60;

    /**
     * 热度餐品名称redis键
     */
    String HOST_DISH_NAME = "host:dish:";

    /**
     * 热度商店名称redis键
     */
    String HOST_STORE_NAME = "host:store:";

    /**
     * 消息订阅内容键
     */
    String MESSAGE = "message";

    /**
     * 消息订阅者key
     */
    String MESSAGE_ACCEPTER = "message_accepter";
}
