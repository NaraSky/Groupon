package com.lb.infrastructure.adapter.repository;

import com.lb.domain.activity.adapter.repository.IActivityRepository;
import com.lb.domain.activity.model.valobj.DiscountTypeEnum;
import com.lb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.lb.domain.activity.model.valobj.SCSkuActivityVO;
import com.lb.domain.activity.model.valobj.SkuVO;
import com.lb.infrastructure.dao.IGroupBuyActivityDao;
import com.lb.infrastructure.dao.IGroupBuyDiscountDao;
import com.lb.infrastructure.dao.ISCSkuActivityDao;
import com.lb.infrastructure.dao.ISkuDao;
import com.lb.infrastructure.dao.po.GroupBuyActivity;
import com.lb.infrastructure.dao.po.GroupBuyDiscount;
import com.lb.infrastructure.dao.po.SCSkuActivity;
import com.lb.infrastructure.dao.po.Sku;
import com.lb.infrastructure.dcc.DCCService;
import com.lb.infrastructure.redis.IRedisService;
import org.redisson.api.RBitSet;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ActivityRepository implements IActivityRepository {

    @Resource
    private IGroupBuyActivityDao groupBuyActivityDao;
    @Resource
    private IGroupBuyDiscountDao groupBuyDiscountDao;
    @Resource
    private ISCSkuActivityDao skuActivityDao;
    @Resource
    private ISkuDao skuDao;
    @Resource
    private IRedisService redisService;
    @Resource
    private DCCService dccService;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId) {
        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryValidGroupBuyActivityId(activityId);
        if (null == groupBuyActivityRes) return null;

        String discountId = groupBuyActivityRes.getDiscountId();

        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyActivityDiscountByDiscountId(discountId);
        if (null == groupBuyDiscountRes) return null;

        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
                .discountName(groupBuyDiscountRes.getDiscountName())
                .discountDesc(groupBuyDiscountRes.getDiscountDesc())
                .discountType(DiscountTypeEnum.getByCode(groupBuyDiscountRes.getDiscountType()))
                .marketPlan(groupBuyDiscountRes.getMarketPlan())
                .marketExpr(groupBuyDiscountRes.getMarketExpr())
                .tagId(groupBuyDiscountRes.getTagId())
                .build();

        return GroupBuyActivityDiscountVO.builder()
                .activityId(groupBuyActivityRes.getActivityId())
                .activityName(groupBuyActivityRes.getActivityName())
                .groupBuyDiscount(groupBuyDiscount)
                .groupType(groupBuyActivityRes.getGroupType())
                .takeLimitCount(groupBuyActivityRes.getTakeLimitCount())
                .target(groupBuyActivityRes.getTarget())
                .validTime(groupBuyActivityRes.getValidTime())
                .status(groupBuyActivityRes.getStatus())
                .startTime(groupBuyActivityRes.getStartTime())
                .endTime(groupBuyActivityRes.getEndTime())
                .tagId(groupBuyActivityRes.getTagId())
                .tagScope(groupBuyActivityRes.getTagScope())
                .build();
    }

    @Override
    public SkuVO querySkuByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuByGoodsId(goodsId);
        if (sku == null) return null;
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();
    }

    @Override
    public SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setGoodsId(goodsId);

        SCSkuActivity scSkuActivity = skuActivityDao.querySCSkuActivityBySCGoodsId(scSkuActivityReq);
        if (null == scSkuActivity) return null;

        return SCSkuActivityVO.builder()
                .source(scSkuActivity.getSource())
                .chanel(scSkuActivity.getChannel())
                .activityId(scSkuActivity.getActivityId())
                .goodsId(scSkuActivity.getGoodsId())
                .build();
    }

    @Override
    public boolean isTagCrowdRange(String tagId, String userId) {
        RBitSet bitSet = redisService.getBitSet(tagId);
        if (!bitSet.isExists()) {
            // 修改为默认拒绝，需根据业务需求调整
            return false;
        }
        return bitSet.get(redisService.getIndexFromUserId(userId));
    }

    /**
     * 获取降级开关状态（来自DCC配置）
     * @return true表示开启降级
     */
    @Override
    public boolean downgradeSwitch() {
        return dccService.isDowngradeSwitch();
    }

    /**
     * 判断用户是否在切量范围内
     * @param userId 用户ID
     * @return true表示在切量范围内
     */
    @Override
    public boolean cutRange(String userId) {
        return dccService.isCutRange(userId);
    }

}