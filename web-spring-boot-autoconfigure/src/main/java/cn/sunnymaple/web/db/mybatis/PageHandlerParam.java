package cn.sunnymaple.web.db.mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页参数
 * @author wangzb
 * @date 2019/12/11 18:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHandlerParam {
    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 每页的数量
     */
    private Integer pageSize;
}
