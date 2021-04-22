package com.achao.pojo.constant;

/**
 * 返回状态码
 *
 * @author dz
 * @Date 2020-12-16
 */
public class HttpStatus {
    /**
     * 操作成功
     */
    public static final String SUCCESS = "200";

    /**
     * 对象创建成功
     */
    public static final String CREATED = "201";

    /**
     * 请求已经被接受
     */
    public static final String ACCEPTED = "202";

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final String NO_CONTENT = "204";

    /**
     * 资源已被移除
     */
    public static final String MOVED_PERM = "301";

    /**
     * 重定向
     */
    public static final String SEE_OTHER = "303";

    /**
     * 资源没有被修改
     */
    public static final String NOT_MODIFIED = "304";

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final String BAD_REQUEST = "400";

    /**
     * 未授权
     */
    public static final String UNAUTHORIZED = "401";

    /**
     * 访问受限，授权过期
     */
    public static final String FORBIDDEN = "403";

    /**
     * 资源，服务未找到
     */
    public static final String NOT_FOUND = "404";

    /**
     * 不允许的http方法
     */
    public static final String BAD_METHOD = "405";

    /**
     * 	服务器无法根据客户端请求的内容特性完成请求
     */
    public static final String Not_Acceptable = "406";

    /**
     * 请求要求代理的身份认证，与401类似，但请求者应当使用代理进行授权
     */
    public static final String Proxy_Authentication_Required = "407";

    /**
     * 服务器等待客户端发送的请求时间过长，超时
     */
    public static final String Request_TimeOut = "408";

    /**
     * 	服务器完成客户端的 PUT 请求时可能返回此代码，服务器处理请求时发生了冲突
     */
    public static final String CONFLICT = "409";

    /**
     * 客户端请求的资源已经不存在。410不同于404，如果资源以前有现在被永久删除了可使用410代码，网站设计人员可通过301代码指定资源的新位置
     */
    public static final String Gone = "410";

    /**
     * 服务器无法处理客户端发送的不带Content-Length的请求信息
     */
    public static final String Length_Required = "411";

    /**
     * 客户端请求信息的先决条件错误
     */
    public static final String Precondition_Failed = "412";

    /**
     * 由于请求的实体过大，服务器无法处理，因此拒绝请求。为防止客户端的连续请求，服务器可能会关闭连接。如果只是服务器暂时无法处理，则会包含一个Retry-After的响应信息
     */
    public static final String Request_Entity_Too_Large = "413";

    /**
     * 请求的URI过长（URI通常为网址），服务器无法处理
     */
    public static final String Request_URI_Too_Large = "414";

    /**
     * 服务器无法处理请求附带的媒体格式
     */
    public static final String UNSUPPORTED_TYPE = "415";
    /**
     * 客户端请求的范围无效
     */
    public static final String Requested_range_not_satisfiable = "416";

    /**
     * 服务器无法满足Expect的请求头信息
     */
    public static final String Expectation_Failed = "417";

    /**
     * 系统内部错误
     */
    public static final String ERROR = "500";

    /**
     * 接口未实现
     */
    public static final String NOT_IMPLEMENTED = "501";

    /**
     * 作为网关或者代理工作的服务器尝试执行请求时，从远程服务器接收到了一个无效的响应
     */
    public static final String Bad_Gateway = "502";

    /**
     * 由于超载或系统维护，服务器暂时的无法处理客户端的请求。延时的长度可包含在服务器的Retry-After头信息中
     */
    public static final String Service_Unavailable = "503";

    /**
     * 充当网关或代理的服务器，未及时从远端服务器获取请求
     */
    public static final String Gateway_Time_out = "504";

    /**
     * 服务器不支持请求的HTTP协议的版本，无法完成处理
     */
    public static final String HTTP_Version_not_supported = "505";

}
