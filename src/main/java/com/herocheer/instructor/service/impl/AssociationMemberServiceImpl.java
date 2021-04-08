package com.herocheer.instructor.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.herocheer.common.base.Page.Page;
import com.herocheer.common.constants.ResponseCode;
import com.herocheer.common.exception.CommonException;
import com.herocheer.common.utils.StringUtils;
import com.herocheer.instructor.domain.entity.AssociationManage;
import com.herocheer.instructor.domain.entity.AssociationMember;
import com.herocheer.instructor.dao.AssociationMemberDao;
import com.herocheer.instructor.domain.entity.CourierStation;
import com.herocheer.instructor.domain.entity.ServiceHours;
import com.herocheer.instructor.domain.vo.AssociationManageVo;
import com.herocheer.instructor.domain.vo.AssociationMemberQueryVo;
import com.herocheer.instructor.domain.vo.UserGuideProjectVo;
import com.herocheer.instructor.enums.JobTitleEnums;
import com.herocheer.instructor.enums.SexEnums;
import com.herocheer.instructor.service.AssociationManageService;
import com.herocheer.instructor.service.AssociationMemberService;
import com.herocheer.instructor.utils.AesUtil;
import com.herocheer.instructor.utils.ExcelUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.stereotype.Service;
import com.herocheer.mybatis.base.service.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author makejava
 * @desc  协会成员(AssociationMember)表服务实现类
 * @date 2021-04-07 09:31:30
 * @company 厦门熙重电子科技有限公司
 */
@Service
@Transactional
public class AssociationMemberServiceImpl extends BaseServiceImpl<AssociationMemberDao, AssociationMember,Long> implements AssociationMemberService {

    @Resource
    private AssociationManageService associationManageService;


    @Override
    public Page<AssociationMember> queryPage(AssociationMemberQueryVo queryVo) {
        Page page = Page.startPage(queryVo.getPageNo(),queryVo.getPageSize());
        List<AssociationMember> list=this.dao.findList(queryVo);
        page.setDataList(list);
        return page;
    }

    @Override
    public Integer addMember(AssociationMember associationMember) {
        if(associationMember.getAssociationId()!=null && associationMember.getContactNumber()!=null){
            Map<String,Object> map=new HashedMap();
            map.put("associationId",associationMember.getAssociationId());
            map.put("licenseNumber",associationMember.getLicenseNumber());
            Integer count=this.dao.count(map);
            if(count>0){
                throw new CommonException(ResponseCode.SERVER_ERROR, "重复添加,该协会已存在该成员!");
            }
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR, "缺少参数!");
        }
        return this.dao.insert(associationMember);
    }

    @Override
    public Integer updateMember(AssociationMember associationMember) {
        if(associationMember.getAssociationId()!=null && associationMember.getContactNumber()!=null){
            Map<String,Object> map=new HashedMap();
            map.put("associationId",associationMember.getAssociationId());
            map.put("licenseNumber",associationMember.getLicenseNumber());
            List<AssociationMember> list=this.dao.findByLimit(map);
            if(!list.isEmpty()){
                if(list.size()>1){
                    throw new CommonException(ResponseCode.SERVER_ERROR, "重复添加,该协会已存在该成员!");
                }else if(list.size()==1 && !associationMember.getId().equals(list.get(0).getId())){
                    throw new CommonException(ResponseCode.SERVER_ERROR, "重复添加,该协会已存在该成员!");
                }
            }
        }else {
            throw new CommonException(ResponseCode.SERVER_ERROR, "缺少参数!");
        }
        return this.dao.update(associationMember);
    }

    @Override
    public Integer delMember(Long id) {
        return this.dao.delete(id);
    }

    @Override
    public void templateExport(Long associationId, HttpServletResponse response) {
        //准备将数据集合封装成Excel对象
        List<String> row1 = CollUtil.newArrayList("*姓名", "*性别", "*身份证号", "*联系电话","*协会职务");
        List<String> row2 = CollUtil.newArrayList("", "", "", "","");
        List<List<String>> rows = CollUtil.newArrayList(row1,row2);
        ExcelWriter writer = cn.hutool.poi.excel.ExcelUtil.getWriter(true);
        //合并标题单元格
        writer.merge(row1.size() - 1, "协会成员导入模板");
        //设置列宽
        writer.setColumnWidth(0, 20); //第1列20px宽
        writer.setColumnWidth(1, 20); //第2列20px宽
        writer.setColumnWidth(2, 25); //第3列20px宽
        writer.setColumnWidth(3, 20); //第4列20px宽
        writer.setColumnWidth(4, 20); //第5列40px宽
        //设置服务时段下拉选项
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList();
        cellRangeAddressList.addCellRangeAddress(1,1,999,1);
        writer.addSelect(cellRangeAddressList,"男,女");
        CellRangeAddressList cellRangeAddressList2 = new CellRangeAddressList();
        cellRangeAddressList2.addCellRangeAddress(1,4,999,4);
        writer.addSelect(cellRangeAddressList2,"普通成员,秘书长,副会长,会长");
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        try {
            // 获取输出流
            final OutputStream output = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("协会成员导入模板.xlsx", "UTF-8"));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            writer.flush(output, true);
            writer.close();
            IoUtil.close(output);
        } catch (IOException e) {
            throw new CommonException("导出Excel异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void associationMemberImport(Long associationId, MultipartFile multipartFile) {
        AssociationManage associationManage=associationManageService.get(associationId);
        if(associationManage==null){
            throw new CommonException(ResponseCode.SERVER_ERROR, "协会不存在,导入失败!");
        }
        try {
            ExcelReader reader = cn.hutool.poi.excel.ExcelUtil.getReader(multipartFile.getInputStream());
            //第一行是标题，第二行是标
            List<List<Object>> read = reader.read(1,1000);
            List<Object> title = read.get(0);
            for (int i = 1; i < read.size(); i++) {//0是标题，数据行从1开始
                List<Object> data = read.get(i);
                for (int j = 0; j < data.size(); j++) {
                    boolean required = ExcelUtil.isRequired(title.get(j), data.get(j));
                    if(!required){
                        throw new CommonException("第【"+(i+2)+"】行，第【"+(j+1)+"】列："+title.get(j)+"必填选项");
                    }
                }
                AssociationMember associationMember=new AssociationMember();
                associationMember.setAssociationId(associationManage.getId());
                associationMember.setAssociationName(associationManage.getName());
                associationMember.setName(String.valueOf(data.get(0)).trim());
                associationMember.setGender(SexEnums.getType(String.valueOf(data.get(1)).trim()));
                associationMember.setLicenseNumber(AesUtil.encrypt(String.valueOf(data.get(2)).trim()));
                associationMember.setContactNumber(AesUtil.encrypt(String.valueOf(data.get(3)).trim()));
                associationMember.setJobTitle(JobTitleEnums.getType(String.valueOf(data.get(4)).trim()));
                Map<String,Object> map=new HashMap<>();
                map.put("associationId",associationMember.getAssociationId());
                map.put("licenseNumber",associationMember.getLicenseNumber());
                Integer count=this.dao.count(map);
                if(count>0){
                    throw new CommonException("第【"+(i+2)+"】行:数据重复,该成员已经加入"+associationManage.getName());
                }
                this.dao.insert(associationMember);
            }
        } catch (CommonException e){
            throw new CommonException(e.getMessage());
        } catch (Exception e){
            throw new CommonException("模板错误，数据导入失败");
        }
    }
}