package com.mdd.ela.service.impl;

import com.mdd.ela.dto.base.ComboBoxResponse;
import com.mdd.ela.dto.chapter.ChapterRequest;
import com.mdd.ela.dto.chapter.ChapterResponse;
import com.mdd.ela.dto.chapter.ChapterUpdateRequest;
import com.mdd.ela.exception.AppRuntimeException;
import com.mdd.ela.model.base.APIResponse;
import com.mdd.ela.repository.ChapterRepository;
import com.mdd.ela.service.ChapterService;
import com.mdd.ela.util.LoggedInUserUtil;
import com.mdd.ela.util.PagingUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/16/2025 3:21 下午
 * @project e-learning-app
 */
@Service
@Transactional(rollbackFor = AppRuntimeException.class)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChapterServiceImpl implements ChapterService {
    ChapterRepository repository;

    public ChapterServiceImpl(ChapterRepository repository) {
        this.repository = repository;
    }

    @Override
    public APIResponse getAll(long courseId) {
        return APIResponse.success(repository.getAll(courseId));
    }

    @Override
    public APIResponse create(ChapterRequest request) {
        repository.create(request);
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse update(ChapterUpdateRequest request) {
        repository.update(request);
        return APIResponse.success(repository.getDetail(request.getId()));
    }

    @Override
    public APIResponse delete(long id) {
        return APIResponse.success(repository.delete(id));

    }

    @Override
    public APIResponse getAll(Map<String, Object> reqMap) {
        int limit = PagingUtil.getLimit((Integer) reqMap.get("pageSize"));
        int offset = PagingUtil.getOffset((Integer) reqMap.get("pageSize"), (Integer) reqMap.get("pageNum"));
        reqMap.put("limit", limit);
        reqMap.put("offset", offset);
        reqMap.put("accountId",LoggedInUserUtil.getIdOfLoggedInUser());

        List<ChapterResponse> chapterResponseList = repository.getAllComboBox(reqMap);
        Map<String,Object> resultResponse = new HashMap<>();
        int count = repository.getCount(reqMap);
        resultResponse.put("data",chapterResponseList);
        resultResponse.put("metaData",Map.of(
                "pageSize", reqMap.get("pageSize"),
                "pageNum", reqMap.get("pageNum"),
                "totalPage", (int)Math.ceil((double) count/(int)reqMap.get("pageSize")),
                "totalRecords", count
        ));
        return APIResponse.success(resultResponse);
    }

    @Override
    public APIResponse getAllComboBox(long courseId) {
        List<ComboBoxResponse> response = repository.getAllForComboBox(courseId);
        return APIResponse.success(response);
    }
}
