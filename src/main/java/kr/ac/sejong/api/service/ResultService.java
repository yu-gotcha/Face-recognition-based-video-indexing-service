package kr.ac.sejong.api.service;

import kr.ac.sejong.api.domain.ResultSection;
import kr.ac.sejong.api.domain.Upload;
import kr.ac.sejong.api.repository.ResultSectionRepository;
import kr.ac.sejong.api.repository.UploadRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResultService {
    private final ResultSectionRepository resultSectionRepository;
    int count=0;

    public ResultService(ResultSectionRepository resultSectionRepository) {
        this.resultSectionRepository = resultSectionRepository;
    }

    public List<Map<String, Object>> getResultByUploadId(String uploadId){
        long id = Long.parseLong(uploadId);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<ResultSection> result = resultSectionRepository.findAllByUpload(id);

        count=result.size();

        for(ResultSection i: result){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("start", i.getStart());
            map.put("end", i.getEnd());
        }

        return resultList;
    }

    public Map<String, Object> getFileByUploadId(String uploadId){
        long id = Long.parseLong(uploadId);
        Map<String, Object> map = new HashMap<String, Object>();

        Upload upload = resultSectionRepository.findByUpload(id);
        map.put("imgName", upload.getUploadImg().getUpImgName());
        map.put("imgSavedName", upload.getUploadImg().getUpImgSavedName());
        map.put("vidName", upload.getUploadVid().getUpVidName());
        map.put("vidSavedName", upload.getUploadVid().getUpVidSavedName());
        map.put("resultCount", count);

        return map;
    }
}
