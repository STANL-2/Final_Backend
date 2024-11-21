package stanl_2.final_backend.domain.s3;

import org.springframework.web.multipart.MultipartFile;

public interface S3FileService {

    // 파일 업로드(단일)
    String uploadOneFile(MultipartFile file);

    // 파일 업로드(다중)


    // 파일 삭제
    void deleteFile(String fileName);

    // 파일 URL 조회
    String getFileUrl(String fileName);

    // 파일 다운로드
    byte[] downloadFile(String fileName);

    // 폴더 조회
//    String getFileFolder(FileForder fileFolder);


}
