package com.herocheer.instructor.service.impl;

import cn.hutool.core.util.IdUtil;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.dao.SysOperationDao;
import com.herocheer.instructor.domain.entity.SysOperation;
import com.herocheer.instructor.domain.vo.SysOperationVO;
import com.herocheer.instructor.service.SysOperationService;
import com.herocheer.instructor.utils.PinYinUtil;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaorh
 * @desc 功能操作表(SysOperation)表服务实现类
 * @date 2021-01-07 17:46:31
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Slf4j
public class SysOperationServiceImpl extends BaseServiceImpl<SysOperationDao, SysOperation, Long> implements SysOperationService {

    /**
     * 添加操作
     *
     * @param sysOperationVO VO
     * @return {@link SysOperation}
     */
    @Override
    public SysOperation addOperation(SysOperationVO sysOperationVO) {
        SysOperation sysOperation = SysOperation.builder().build();
        BeanCopier.create(sysOperationVO.getClass(),sysOperation.getClass(),false).copy(sysOperationVO,sysOperation,null);
        sysOperation.setId(IdUtil.getSnowflake(1, 1).nextId());


        // 处理编码重复
        Map<String, Object> codeMap = new HashMap();
        // 编码取用操作名的拼首字母
        String  oldCode  = PinYinUtil.toFirstChar(sysOperation.getOperationName()).toLowerCase();
        codeMap.put("code",oldCode);
        int sum = 1;
        String newCode = null;
        if(this.dao.selectSysOperateOne(codeMap) >= 1){

            do{
                newCode = oldCode +"0" + sum++;
                codeMap.put("code",newCode);
            }while (this.dao.selectSysOperateOne(codeMap) >= 1);

            sysOperation.setCode(newCode);
        }else {
            sysOperation.setCode(oldCode);
        }

        this.insert(sysOperation);
        return sysOperation;
    }

    /**
     * 通过id移除操作
     *
     * @param id id
     */
    @Override
    public void removeOperationById(Long id) {
        this.delete(id);
    }

    /**
     * 通过id查找操作
     *
     * @param id id
     * @return {@link SysOperation}
     */
    @Override
    public SysOperation findOperationById(Long id) {
        return this.get(id);
    }

    /**
     * 修改操作
     *
     * @param sysOperationVO VO
     * @return {@link SysOperation}
     */
    @Override
    public SysOperation modifyOperation(SysOperationVO sysOperationVO) {
        if(sysOperationVO.getId() == null || StringUtils.isBlank(sysOperationVO.getId().toString())){
            throw new CommonException("编辑ID不能为空");
        }
        SysOperation sysOperation = SysOperation.builder().build();
        BeanCopier.create(sysOperationVO.getClass(),sysOperation.getClass(),false).copy(sysOperationVO,sysOperation,null);
        this.update(sysOperation);
        return sysOperation;
    }
}