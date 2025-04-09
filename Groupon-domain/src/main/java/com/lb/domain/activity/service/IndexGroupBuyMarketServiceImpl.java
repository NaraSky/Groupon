package com.lb.domain.activity.service;

import com.lb.domain.activity.adapter.repository.IActivityRepository;
import com.lb.domain.activity.model.entity.MarketProductEntity;
import com.lb.domain.activity.model.entity.TrialBalanceEntity;
import com.lb.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import com.lb.domain.activity.model.valobj.TeamStatisticVO;
import com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.lb.types.design.framework.tree.StrategyHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory.*;

/**
 * 首页营销服务实现类，负责处理营销试算核心流程
 */
@Service
public class IndexGroupBuyMarketServiceImpl implements IIndexGroupBuyMarketService {

    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;
    @Resource
    private IActivityRepository repository;

    /**
     * 执行首页营销试算流程
     *
     * @param marketProductEntity 商品营销参数实体
     * @return 试算结果对象
     */
    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler = defaultActivityStrategyFactory.strategyHandler();
        // 方法会调用 DefaultActivityStrategyFactory 创建的 RootNode 策略处理器。
        TrialBalanceEntity trialBalanceEntity = strategyHandler.apply(marketProductEntity, new DynamicContext());
        return trialBalanceEntity;
    }

    @Override
    public List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailList(Long activityId, String userId, Integer ownerCount, Integer randomCount) {
        List<UserGroupBuyOrderDetailEntity> unionAllList = new ArrayList<>();

        // 查询个人拼团数据
        if (0 != ownerCount) {
            List<UserGroupBuyOrderDetailEntity> ownerList = repository.queryInProgressUserGroupBuyOrderDetailListByOwner(activityId, userId, ownerCount);
            if (null != ownerList && !ownerList.isEmpty()) {
                unionAllList.addAll(ownerList);
            }
        }

        // 查询其他非个人拼团
        if (0 != randomCount) {
            List<UserGroupBuyOrderDetailEntity> randomList = repository.queryInProgressUserGroupBuyOrderDetailListByRandom(activityId, userId, randomCount);
            if (null != randomList && !randomList.isEmpty()) {
                unionAllList.addAll(randomList);
            }
        }

        return unionAllList;
    }

    @Override
    public TeamStatisticVO queryTeamStatisticByActivityId(Long activityId) {
        return repository.queryTeamStatisticByActivityId(activityId);
    }
}
