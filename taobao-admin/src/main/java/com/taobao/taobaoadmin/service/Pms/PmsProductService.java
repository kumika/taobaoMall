package com.taobao.taobaoadmin.service.Pms;

import com.taobao.taobaoadmin.dto.Pms.PmsProductParam;
import com.taobao.taobaoadmin.dto.Pms.PmsProductQueryParam;
import com.taobao.taobaoadmin.dto.Pms.PmsProductResult;
import com.taobao.taobaoadmin.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品管理Service
 */
public interface PmsProductService {


    /**
     * 创建商品
     * @param productParam
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED )
    //事务注解：控制事务隔离级别。默认跟数据库的默认隔离级别相同，
    // 控制事务传播。默认是Propagation.REQUIRED
    int create(PmsProductParam productParam);

    /**
     * 分页查询商品
     */
    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

    /**
     *  根据商品编号获取更新信息
     */
    PmsProductResult getUpdateInfo(Long id);

    /**
     * 批量删除商品
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * 更新商品
     */
    int update(Long id, PmsProductParam productParam);

    /**
     * 批量修改商品上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 批量修改商品推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 批量修改新品状态
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 批量修改审核状态
     * @param ids 产品id
     * @param verifyStatus 审核状态
     * @param detail 审核详情
     */
    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    /**
     * 根据商品名称或者货号模糊查询
     */
    List<PmsProduct> list(String keyword);
}
