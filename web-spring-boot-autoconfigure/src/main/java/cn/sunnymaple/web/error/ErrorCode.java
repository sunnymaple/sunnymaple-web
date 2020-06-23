package cn.sunnymaple.web.error;

/**
 * @author wangzb
 * @date 2020/6/11 16:08
 */
public class ErrorCode {
    /**
     * 一切 ok
     * 正确执行后的返回
     */
    public static final String OK = "00000";
    /**
     * 用户端错误
     * 一级宏观错误码
     */
    public static final String A0001 = "A0001";
    /**
     * 用户注册错误
     * 二级宏观错误码
     */
    public static final String A0100 = "A0100";
    /**
     * 用户未同意隐私协议
     */
    public static final String A0101 = "A0101";
    /**
     * 注册国家或地区受限
     */
    public static final String A0102 = "A0102";
    /**
     * 用户名校验失败
     */
    public static final String A0110 = "A0110";
    /**
     * 用户名已存在
     */
    public static final String A0111 = "A0111";
    /**
     * 用户名包含敏感词
     */
    public static final String A0112 = "A0112";
    /**
     * 用户名包含特殊字符
     */
    public static final String A0113 = "A0113";
    /**
     * 密码校验失败
     */
    public static final String A0120 = "A0120";
    /**
     *密码长度不够
     */
    public static final String A0121 = "A0121";
    /**
     * 密码强度不够
     */
    public static final String A0122 = "A0122";
    /**
     *校验码输入错误
     */
    public static final String A0130 = "A0130";
    /**
     *短信校验码输入错误
     */
    public static final String A0131 = "A0131";
    /**
     *邮件校验码输入错误
     */
    public static final String A0132 = "A0132";
    /**
     *语音校验码输入错误
     */
    public static final String A0133 = "A0133";
    /**
     *用户证件异常
     */
    public static final String A0140 = "A0140";
    /**
     *用户证件类型未选择
     */
    public static final String A0141 = "A0141";
    /**
     *大陆身份证编号校验非法
     */
    public static final String A0142 = "A0142";
    /**
     *护照编号校验非法
     */
    public static final String A0143 = "A0143";
    /**
     *军官证编号校验非法
     */
    public static final String A0144 = "A0144";
    /**
     *用户基本信息校验失败
     */
    public static final String A0150 = "A0150";
    /**
     *手机格式校验失败
     */
    public static final String A0151 = "A0151";
    /**
     *地址格式校验失败
     */
    public static final String A0152 = "A0152";
    /**
     *邮箱格式校验失败
     */
    public static final String A0153 = "A0153";
    /**
     *用户登陆异常 二级宏观错误码
     */
    public static final String A0200 = "A0200";
    /**
     *用户账户不存在
     */
    public static final String A0201 = "A0201";
    /**
     *用户账户被冻结
     */
    public static final String A0202 = "A0202";
    /**
     * 用户账户已作废
     */
    public static final String A0203 = "A0203";
    /**
     *用户密码错误
     */
    public static final String A0210 = "A0210";
    /**
     *用户输入密码次数超限
     */
    public static final String A0211 = "A0211";
    /**
     *用户身份校验失败
     */
    public static final String A0220 = "A0220";
    /**
     * 用户指纹识别失败
     */
    public static final String A0221 = "A0221";
    /**
     *用户面容识别失败
     */
    public static final String A0222 = "A0222";
    /**
     *用户未获得第三方登陆授权
     */
    public static final String A0223 = "A0223";
    /**
     *用户登陆已过期
     */
    public static final String A0230 = "A0230";
    /**
     * 用户验证码错误
     */
    public static final String A0240 = "A0240";
    /**
     *用户验证码尝试次数超限
     */
    public static final String A0241 = "A0241";
    /**
     *访问权限异常 二级宏观错误码
     */
    public static final String A0300 = "A0300";
    /**
     * 访问未授权
     */
    public static final String A0301 = "A0301";
    /**
     *正在授权中
     */
    public static final String A0302 = "A0302";
    /**
     *用户授权申请被拒绝
     */
    public static final String A0303 = "A0303";
    /**
     *因访问对象隐私设置被拦截
     */
    public static final String A0310 = "A0310";
    /**
     *授权已过期
     */
    public static final String A0311 = "A0311";
    /**
     *无权限使用 API
     */
    public static final String A0312 = "A0312";
    /**
     *用户访问被拦截
     */
    public static final String A0320 = "A0320";
    /**
     *黑名单用户
     */
    public static final String A0321 = "A0321";
    /**
     * 账号被冻结
     */
    public static final String A0322 = "A0322";
    /**
     *非法 IP 地址
     */
    public static final String A0323 = "A0323";
    /**
     *网关访问受限
     */
    public static final String A0324 = "A0324";
    /**
     *地域黑名单
     */
    public static final String A0325 = "A0325";
    /**
     *服务已欠费
     */
    public static final String A0330 = "A0330";
    /**
     *用户签名异常
     */
    public static final String A0340 = "A0340";
    /**
     *RSA 签名错误
     */
    public static final String A0341 = "A0341";
    /**
     *用户请求参数错误 二级宏观错误码
     */
    public static final String A0400 = "A0400";
    /**
     *包含非法恶意跳转链接
     */
    public static final String A0401 = "A0401";
    /**
     *无效的用户输入
     */
    public static final String A0402 = "A0402";
    /**
     * 用户资源不存在
     */
    public static final String A0404 = "A0404";
    /**
     *请求必填参数为空
     */
    public static final String A0410 = "A0410";
    /**
     *用户订单号为空
     */
    public static final String A0411 = "A0411";
    /**
     * 订购数量为空
     */
    public static final String A0412 = "A0412";
    /**
     *缺少时间戳参数
     */
    public static final String A0413 = "A0413";
    /**
     *非法的时间戳参数
     */
    public static final String A0414 = "A0414";
    /**
     * 请求参数值超出允许的范围
     */
    public static final String A0420 = "A0420";
    /**
     *参数格式不匹配
     */
    public static final String A0421 = "A0421";
    /**
     *地址不在服务范围
     */
    public static final String A0422 = "A0422";
    /**
     * 时间不在服务范围
     */
    public static final String A0423 = "A0423";
    /**
     *金额超出限制
     */
    public static final String A0424 = "A0424";
    /**
     * 数量超出限制
     */
    public static final String A0425 = "A0425";
    /**
     *请求批量处理总个数超出限制
     */
    public static final String A0426 = "A0426";
    /**
     *请求 JSON 解析失败
     */
    public static final String A0427 = "A0427";
    /**
     * 用户输入内容非法
     */
    public static final String A0430 = "A0430";
    /**
     *包含违禁敏感词
     */
    public static final String A0431 = "A0431";
    /**
     *图片包含违禁信息
     */
    public static final String A0432 = "A0432";
    /**
     *文件侵犯版权
     */
    public static final String A0433 = "A0433";
    /**
     *用户操作异常
     */
    public static final String A0440 = "A0440";
    /**
     *用户支付超时
     */
    public static final String A0441 = "A0441";
    /**
     * 确认订单超时
     */
    public static final String A0442 = "A0442";
    /**
     *订单已关闭
     */
    public static final String A0443 = "A0443";
    /**
     *用户请求服务异常 二级宏观错误码
     */
    public static final String A0500 = "A0500";
    /**
     *请求次数超出限制
     */
    public static final String A0501 = "A0501";
    /**
     *请求并发数超出限制
     */
    public static final String A0502 = "A0502";
    /**
     * 用户操作请等待
     */
    public static final String A0503 = "A0503";
    /**
     *WebSocket 连接异常
     */
    public static final String A0504 = "A0504";
    /**
     * WebSocket 连接断开
     */
    public static final String A0505 = "A0505";
    /**
     *用户重复请求
     */
    public static final String A0506 = "A0506";
    /**
     *用户资源异常 二级宏观错误码
     */
    public static final String A0600 = "A0600";
    /**
     * 账户余额不足
     */
    public static final String A0601 = "A0601";
    /**
     *用户磁盘空间不足
     */
    public static final String A0602 = "A0602";
    /**
     *用户内存空间不足
     */
    public static final String A0603 = "A0603";
    /**
     *用户 OSS 容量不足
     */
    public static final String A0604 = "A0604";
    /**
     *用户配额已用光 蚂蚁森林浇水数或每天抽奖数
     */
    public static final String A0605 = "A0605";
    /**
     * 用户上传文件异常 二级宏观错误码
     */
    public static final String A0700 = "A0700";
    /**
     *用户上传文件类型不匹配
     */
    public static final String A0701 = "A0701";
    /**
     *用户上传文件太大
     */
    public static final String A0702 = "A0702";
    /**
     *用户上传图片太大
     */
    public static final String A0703 = "A0703";
    /**
     *用户上传视频太大
     */
    public static final String A0704 = "A0704";
    /**
     *用户上传压缩文件太大
     */
    public static final String A0705 = "A0705";
    /**
     *用户当前版本异常 二级宏观错误码
     */
    public static final String A0800 = "A0800";
    /**
     * 用户安装版本与系统不匹配
     */
    public static final String A0801 = "A0801";
    /**
     *用户安装版本过低
     */
    public static final String A0802 = "A0802";
    /**
     *用户安装版本过高
     */
    public static final String A0803 = "A0803";
    /**
     *用户安装版本已过期
     */
    public static final String A0804 = "A0804";
    /**
     * 用户 API 请求版本不匹配
     */
    public static final String A0805 = "A0805";
    /**
     *用户 API 请求版本过高
     */
    public static final String A0806 = "A0806";
    /**
     *用户 API 请求版本过低
     */
    public static final String A0807 = "A0807";
    /**
     *用户隐私未授权 二级宏观错误码
     */
    public static final String A0900 = "A0900";
    /**
     *用户隐私未签署
     */
    public static final String A0901 = "A0901";
    /**
     *用户摄像头未授权
     */
    public static final String A0902 = "A0902";
    /**
     *用户相机未授权
     */
    public static final String A0903 = "A0903";
    /**
     *用户图片库未授权
     */
    public static final String A0904 = "A0904";
    /**
     * 用户文件未授权
     */
    public static final String A0905 = "A0905";
    /**
     * 用户位置信息未授权
     */
    public static final String A0906 = "A0906";
    /**
     *用户通讯录未授权
     */
    public static final String A0907 = "A0907";
    /**
     *用户设备异常 二级宏观错误码
     */
    public static final String A1000 = "A1000";
    /**
     *用户相机异常
     */
    public static final String A1001 = "A1001";
    /**
     *用户麦克风异常
     */
    public static final String A1002 = "A1002";
    /**
     * 用户听筒异常
     */
    public static final String A1003 = "A1003";
    /**
     * 用户扬声器异常
     */
    public static final String A1004 = "A1004";
    /**
     *用户 GPS 定位异常
     */
    public static final String A1005 = "A1005";
    /**
     * 系统执行出错 一级宏观错误码
     */
    public static final String B0001 = "B0001";
    /**
     *系统执行超时 二级宏观错误码
     */
    public static final String B0100 = "B0100";
    /**
     * 系统订单处理超时
     */
    public static final String B0101 = "B0101";
    /**
     *系统容灾功能被触发 二级宏观错误码
     */
    public static final String B0200 = "B0200";
    /**
     *系统限流
     */
    public static final String B0210 = "B0210";
    /**
     *系统功能降级
     */
    public static final String B0220 = "B0220";
    /**
     *系统资源异常 二级宏观错误码
     */
    public static final String B0300 = "B0300";
    /**
     *系统资源耗尽
     */
    public static final String B0310 = "B0310";
    /**
     *系统磁盘空间耗尽
     */
    public static final String B0311 = "B0311";
    /**
     *系统内存耗尽
     */
    public static final String B0312 = "B0312";
    /**
     *文件句柄耗尽
     */
    public static final String B0313 = "B0313";
    /**
     *系统连接池耗尽
     */
    public static final String B0314 = "B0314";
    /**
     *系统线程池耗尽
     */
    public static final String B0315 = "B0315";
    /**
     *系统资源访问异常
     */
    public static final String B0320 = "B0320";
    /**
     *系统读取磁盘文件失败
     */
    public static final String B0321 = "B0321";
    /**
     *调用第三方服务出错 一级宏观错误码
     */
    public static final String C0001 = "C0001";
    /**
     *中间件服务出错 二级宏观错误码
     */
    public static final String C0100 = "C0100";
    /**
     *RPC 服务出错
     */
    public static final String C0110 = "C0110";
    /**
     * RPC 服务未找到
     */
    public static final String C0111 = "C0111";
    /**
     * RPC 服务未注册
     */
    public static final String C0112 = "C0112";
    /**
     * 接口不存在
     */
    public static final String C0113 = "C0113";
    /**
     * 消息服务出错
     */
    public static final String C0120 = "C0120";
    /**
     * 消息投递出错
     */
    public static final String C0121 = "C0121";
    /**
     *消息消费出错
     */
    public static final String C0122 = "C0122";
    /**
     *消息订阅出错
     */
    public static final String C0123 = "C0123";
    /**
     *消息分组未查到
     */
    public static final String C0124 = "C0124";
    /**
     *缓存服务出错
     */
    public static final String C0130 = "C0130";
    /**
     *key 长度超过限制
     */
    public static final String C0131 = "C0131";
    /**
     *value 长度超过限制
     */
    public static final String C0132 = "C0132";
    /**
     * 存储容量已满
     */
    public static final String C0133 = "C0133";
    /**
     * 不支持的数据格式
     */
    public static final String C0134 = "C0134";
    /**
     *配置服务出错
     */
    public static final String C0140 = "C0140";
    /**
     *网络资源服务出错
     */
    public static final String C0150 = "C0150";
    /**
     *VPN 服务出错
     */
    public static final String C0151 = "C0151";
    /**
     * CDN 服务出错
     */
    public static final String C0152 = "C0152";
    /**
     *域名解析服务出错
     */
    public static final String C0153 = "C0153";
    /**
     *网关服务出错
     */
    public static final String C0154 = "C0154";
    /**
     *第三方系统执行超时 二级宏观错误码
     */
    public static final String C0200 = "C0200";
    /**
     * RPC 执行超时
     */
    public static final String C0210 = "C0210";
    /**
     * 消息投递超时
     */
    public static final String C0220 = "C0220";
    /**
     *缓存服务超时
     */
    public static final String C0230 = "C0230";
    /**
     *配置服务超时
     */
    public static final String C0240 = "C0240";
    /**
     *数据库服务超时
     */
    public static final String C0250 = "C0250";
    /**
     *数据库服务出错 二级宏观错误码
     */
    public static final String C0300 = "C0300";
    /**
     * 表不存在
     */
    public static final String C0311 = "C0311";
    /**
     *列不存在
     */
    public static final String C0312 = "C0312";
    /**
     * 多表关联中存在多个相同名称的列
     */
    public static final String C0321 = "C0321";
    /**
     *数据库死锁
     */
    public static final String C0331 = "C0331";
    /**
     * 主键冲突
     */
    public static final String C0341 = "C0341";
    /**
     *第三方容灾系统被触发 二级宏观错误码
     */
    public static final String C0400 = "C0400";
    /**
     *第三方系统限流
     */
    public static final String C0401 = "C0401";
    /**
     * 第三方功能降级
     */
    public static final String C0402 = "C0402";
    /**
     *通知服务出错 二级宏观错误码
     */
    public static final String C0500 = "C0500";
    /**
     *短信提醒服务失败
     */
    public static final String C0501 = "C0501";
    /**
     *语音提醒服务失败
     */
    public static final String C0502 = "C0502";
    /**
     *邮件提醒服务失败
     */
    public static final String C0503 = "C0503";




}
