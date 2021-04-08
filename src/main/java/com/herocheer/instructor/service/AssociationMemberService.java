package com.herocheer.instructor.service;

import com.herocheer.common.base.Page.Page;
import com.herocheer.instructor.domain.entity.AssociationMember;
import com.herocheer.common.base.service.BaseService;
import com.herocheer.instructor.domain.vo.AssociationMemberQueryVo;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author makejava
 * @desc  协会成员(AssociationMember)表服务接口
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
public interface AssociationMemberService extends BaseService<AssociationMember,Long> {

    Page<AssociationMember> queryPage(AssociationMemberQueryVo queryVo);

    Integer addMember(AssociationMember associationMember);

    Integer updateMember(AssociationMember associationMember);

    Integer delMember(Long id);

    void templateExport(Long associationId,HttpServletResponse response);

    void associationMemberImport(Long associationId, MultipartFile multipartFile);
}