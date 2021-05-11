package kr.ac.sejong.api.service;

import kr.ac.sejong.api.domain.Upload;
import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UploadImgRepository;
import kr.ac.sejong.api.repository.UploadRepository;
import kr.ac.sejong.api.repository.UploadVidRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {
    private final UploadRepository uploadRepository;
    private final UploadImgRepository uploadImgRepository;
    private final UploadVidRepository uploadVidRepository;

    public FileService(UploadRepository uploadRepository, UploadImgRepository uploadImgRepository, UploadVidRepository uploadVidRepository) {
        this.uploadRepository = uploadRepository;
        this.uploadImgRepository = uploadImgRepository;
        this.uploadVidRepository = uploadVidRepository;
    }


    @Transactional
    public List<Map<String, Object>> getFileListByUser(User user){
        List<Upload> uploadList = uploadRepository.findByUser(user);

        System.out.println(uploadList.size());

        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();

        String uploading, processing;

        for(Upload i : uploadList){
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("no", i.getUpId());
            //이름 가져올 수 있어야함
            map.put("imageFileName", uploadImgRepository.findByUpImgId(i.getUpId()).getUpImgName());
            map.put("videoFileName", uploadVidRepository.findByUpVidId(i.getUpId()).getUpVidName());

            if(i.getUploading()==1) uploading = "upload completed";
            else uploading = "uploading...";
            map.put("upload", uploading);

            if(i.getProcessing()==1) processing = "process completed";
            else processing = "processing...";
            map.put("process", processing);

            map.put("imgSavedName", uploadImgRepository.findByUpImgId(i.getUpId()).getUpImgSavedName());
            map.put("vidSavedName", uploadVidRepository.findByUpVidId(i.getUpId()).getUpVidSavedName());

            fileList.add(map);
        }

        System.out.println(fileList);

        return fileList;
    }

}
