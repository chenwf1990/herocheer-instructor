package com.herocheer.instructor.dao;

import com.herocheer.instructor.domain.entity.Reservation;
import com.herocheer.instructor.domain.vo.ReservationListVO;
import com.herocheer.instructor.domain.vo.ReservationQueryVo;
import com.herocheer.instructor.domain.vo.SignInfoVO;
import com.herocheer.mybatis.base.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author makejava
 * @desc  预约记录(Reservation)表数据库访问层
 * @date 2021-02-24 16:30:04
 * @company 厦门熙重电子科技有限公司
 */
public interface ReservationDao extends BaseDao<Reservation,Long>{
    /**
     * 查询预约记录
     * @param queryVo
     * @return
     */
    List<ReservationListVO> findList(ReservationQueryVo queryVo);


    Integer updateReservationStatus(@Param("status")Integer status,@Param("relevanceId")Long relevanceId,
                                    @Param("type")Integer type);

    List<String> findReservationOpenid(@Param("relevanceId")Long relevanceId,
                                       @Param("type") Integer type);

    /**
     * 签到信息列表
     *
     * @param signInfoVO VO
     * @return {@link List<Reservation>}
     */
    List<ReservationListVO> selectSignInfoByPage(SignInfoVO signInfoVO);

    /**
     * 根据当前用户id获取最新预约信息
     *
     * @param queryVo 查询签证官
     * @return {@link ReservationListVO}
     */
    ReservationListVO selectByCurUserId(ReservationQueryVo queryVo);
}