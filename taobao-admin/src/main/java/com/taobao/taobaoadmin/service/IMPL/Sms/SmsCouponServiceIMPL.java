package com.taobao.taobaoadmin.service.IMPL.Sms;

import com.github.pagehelper.PageHelper;
import com.taobao.taobaoadmin.dao.Sms.SmsCouponDao;
import com.taobao.taobaoadmin.dao.Sms.SmsCouponProductCategoryRelationDao;
import com.taobao.taobaoadmin.dao.Sms.SmsCouponProductRelationDao;
import com.taobao.taobaoadmin.dto.Sms.SmsCouponParam;
import com.taobao.taobaoadmin.mapper.SmsCouponMapper;
import com.taobao.taobaoadmin.mapper.SmsCouponProductCategoryRelationMapper;
import com.taobao.taobaoadmin.mapper.SmsCouponProductRelationMapper;
import com.taobao.taobaoadmin.model.*;
import com.taobao.taobaoadmin.service.Sms.SmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SmsCouponServiceIMPL implements SmsCouponService {

    @Autowired
    private SmsCouponMapper couponMapper;

    @Autowired
    private SmsCouponProductRelationDao productRelationDao;

    @Autowired
    private SmsCouponProductRelationMapper productRelationMapper;


    @Autowired
    private SmsCouponProductCategoryRelationDao productCategoryRelationDao;

    @Autowired
    private SmsCouponProductCategoryRelationMapper productCategoryRelationMapper;

    @Autowired
    private SmsCouponDao couponDao;


    @Override
    public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);

        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (type != null) {
            criteria.andTypeEqualTo(type);
        }

        return couponMapper.selectByExample(example);
    }

    @Override
    public int update(Long id, SmsCouponParam couponParam) {
        //赋值id，然后更新到数据库中，
        // 优惠券和商品关系还有优惠券和商品分类关系表的更新步骤是需要先删除，在插入
        couponParam.setId(id);
        int count = couponMapper.updateByPrimaryKey(couponParam);

        //删除后插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            //遍历优惠券和商品关系表列表，把商品关系对象与优惠劵关联起来（id赋值）
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
            }
            //根据id删除优惠券和商品关系表中的对象
            deleteProductRelation(id);
            //因为上面遍历优惠券和商品关系对象列表之后，优惠劵与商品建立新的关系对象，可以进行插入数据
            productRelationDao.insertList(couponParam.getProductRelationList());
        }
        //删除后插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            //遍历优惠券和商品分类关系表列表，把商品分类关系对象与优惠劵关联起来（id赋值）
            for (SmsCouponProductCategoryRelation productCategoryRelation : couponParam.getProductCategoryRelationList()) {
                productCategoryRelation.setCouponId(couponParam.getId());
            }
            //根据id删除优惠券和商品分类关系表中的对象
            deleteProductCategoryRelation(id);
            //因为上面遍历优惠券和商品分类关系对象列表之后，优惠劵与商品分类建立新的关系对象，可以进行插入数据
            productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }


    private void deleteProductCategoryRelation(Long id) {
        SmsCouponProductCategoryRelationExample example = new SmsCouponProductCategoryRelationExample();
        example.createCriteria().andCouponIdEqualTo(id);
        productCategoryRelationMapper.deleteByExample(example);
    }

    private void deleteProductRelation(Long id) {
        SmsCouponProductRelationExample example = new SmsCouponProductRelationExample();
        example.createCriteria().andCouponIdEqualTo(id);
        productRelationMapper.deleteByExample(example);
    }

    @Override
    public SmsCouponParam getItem(Long id) {
        return couponDao.getItem(id);
    }

    @Override
    public int create(SmsCouponParam couponParam) {
        couponParam.setCount(couponParam.getPublishCount());
        couponParam.setUseCount(0);
        couponParam.setCount(0);
        //插入优惠劵表
        int count = couponMapper.insert(couponParam);

        //插入优惠券和商品关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation productRelation : couponParam.getProductRelationList()) {
                productRelation.setCouponId(couponParam.getId());
            }
            productRelationDao.insertList(couponParam.getProductRelationList());
        }
        //插入优惠券和商品分类关系表
        if (couponParam.getUseType().equals(1)) {
            for (SmsCouponProductCategoryRelation productCategoryRelation : couponParam.getProductCategoryRelationList()) {
                productCategoryRelation.setCouponId(couponParam.getId());
            }
            productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        //删除优惠券
        int count = couponMapper.deleteByPrimaryKey(id);
        //删除商品关联
        deleteProductRelation(id);
        //删除商品分类关联
        deleteProductCategoryRelation(id);
        return count;
    }

}
