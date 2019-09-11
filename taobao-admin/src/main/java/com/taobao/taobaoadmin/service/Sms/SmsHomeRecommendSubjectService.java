package com.taobao.taobaoadmin.service.Sms;

import com.taobao.taobaoadmin.model.SmsHomeRecommendSubject;

import java.util.List;

public interface SmsHomeRecommendSubjectService {
    int create(List<SmsHomeRecommendSubject> homeRecommendSubjects);

    List<SmsHomeRecommendSubject> list(String subjectName, Integer recommendStatus, Integer pageNum, Integer pageSize);

    int updateSort(Long id, Integer sort);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int delete(List<Long> ids);
}
