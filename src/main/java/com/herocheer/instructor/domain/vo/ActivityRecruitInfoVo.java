package com.herocheer.instructor.domain.vo;

import com.herocheer.instructor.domain.entity.ActivityRecruitDetail;
import com.herocheer.instructor.domain.entity.ActivityRecruitInfo;
import lombok.Data;

import java.util.List;

/**
 * @program: herocheer-instructor
 * @description:
 * @author: Linjf
 * @create date: 2021-01-18 14:58
 **/
@Data
public class ActivityRecruitInfoVo extends ActivityRecruitInfo {
    private List<ActivityRecruitDetail> recruitDetails;
}
