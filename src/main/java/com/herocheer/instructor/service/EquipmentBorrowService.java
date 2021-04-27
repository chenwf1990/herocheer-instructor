package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.common.base.entity.UserEntity;
import com.herocheer.instructor.domain.entity.EquipmentBorrow;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.entity.EquipmentBorrowDetails;
import com.herocheer.instructor.domain.entity.EquipmentDamage;
import com.herocheer.instructor.domain.entity.EquipmentRemand;
import com.herocheer.instructor.domain.vo.EquipmentBorrowQueryVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowSaveVo;
import com.herocheer.instructor.domain.vo.EquipmentBorrowVo;
import com.herocheer.instructor.domain.vo.EquipmentDamageVo;
import com.herocheer.instructor.domain.vo.EquipmentRemandVo;

import java.util.List;

/**
 * @author makejava
 * @desc  器材借用 (EquipmentBorrow)表服务接口
 * @date 2021-04-20 15:22:50
 * @company 厦门熙重电子科技有限公司
 */
public interface EquipmentBorrowService extends BaseService<EquipmentBorrow,Long> {

    /**
     * 分页查询器材借用列表
     * @param queryVo
     * @return
     */
    Page<EquipmentBorrow> queryPage(EquipmentBorrowQueryVo queryVo,Long userId);

    /**
     * 用户申请借用器材
     * @param vo
     * @return
     */
    Integer addBorrow(EquipmentBorrowSaveVo vo,Long userId);

    /**
     * 值班人员确认
     * @param details
     * @param userId
     * @return
     */
    Integer confirmBorrow(List<EquipmentBorrowDetails> details,Long userId);

    /**
     * 用户签收
     * @param id
     * @return
     */
    Integer userReceipt(Long id);

    /**
     * 驳回
     * @param id
     * @return
     */
    Integer overrule(Long id);

    /**
     * 器材申请归还 必要参数:关联借用器材id,借用单据id
     * @param  remand
     * @return
     */
    Integer applyRemand(List<EquipmentRemand> remand);

    /**
     * 器材归还确认
     * @param remand
     * @return
     */
    Integer confirmRemand(List<EquipmentRemand> remand,Long userId);

    /**
     * 申请归还列表
     * @param id
     * @return
     */
    List<EquipmentRemandVo> remandList(Long id);

    /**
     *归还提示
     * @param id
     * @return
     */
    String getRemandMsg(Long id);

    /**
     * 用户确认归还
     * @param id
     * @return
     */
    Integer userConfirmRemand(Long id);

    /**
     * 自动签收
     * @return
     */
    Integer confirmRemandTack();

    /**
     *
     * @param id
     * @return
     */
    EquipmentBorrowVo getEquipmentBorrow(Long id);

    /**
     *
     * @param id
     * @return
     */
    Integer getCountByUserId(Long id);

    /**
     * 新增器材报废
     * @param vo
     * @return
     */
    Integer andDamage(EquipmentDamageVo vo);

    /**
     * 获取器材报废详情
     * @param borrowId
     * @return
     */
    List<EquipmentDamageVo> getDamage(Long borrowId);
}