package stanl_2.final_backend.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.s3.common.exception.S3CommonException;
import stanl_2.final_backend.domain.s3.common.exception.S3ErrorCode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

@Component
@Service
public class S3FileServiceImpl implements S3FileService {

    private final AmazonS3Client amazonS3Client;

    @Autowired
    public S3FileServiceImpl(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    // 단일 파일 업로드
    @Override
    public String uploadOneFile(MultipartFile file) {

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try(InputStream inputStream = file.getInputStream()) {
            amazonS3Client.putObject(bucket, fileName, inputStream, metadata);

            return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;
        } catch (IOException e) {
            throw new S3CommonException(S3ErrorCode.FILE_UPLOAD_BAD_REQUEST);
        }
    }

    public String uploadHtml(String htmlContent, String fileName) {

        String fileKey = createFileName(fileName + ".html"); // 파일 이름 생성 (UUID로 고유화 가능)

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/html; charset=UTF-8");
        metadata.setContentLength(htmlContent.getBytes(StandardCharsets.UTF_8).length);

        try (InputStream inputStream = new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8))) {
            amazonS3Client.putObject(bucket, fileKey, inputStream, metadata);

            return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileKey;
        } catch (IOException e) {
            throw new S3CommonException(S3ErrorCode.FILE_UPLOAD_BAD_REQUEST);
        }
    }

    // 파일 삭제
    @Override
    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    // AWS S3에 저장된 파일 이름에 해당하는 URL(경로)를 알아내는 함수
    @Override
    public String getFileUrl(String fileName) {
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 파일 다운로드
    @Override
    public byte[] downloadFile(String fileName) {

        // 파일 유무 확인
        validateFileExists(fileName);

        S3Object s3Object = amazonS3Client.getObject(bucket, fileName);
        S3ObjectInputStream s3ObjectContent = s3Object.getObjectContent();

        try {
            return IOUtils.toByteArray(s3ObjectContent);
        } catch (IOException e) {
            throw new S3CommonException(S3ErrorCode.FILE_NOT_FOUND);
        }
    }

    private void validateFileExists(String fileName) {
        if (!amazonS3Client.doesObjectExist(bucket, fileName)) {
            throw new S3CommonException(S3ErrorCode.FILE_NOT_FOUND);
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {
            throw new S3CommonException(S3ErrorCode.FILE_NOT_FOUND);
        }

        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        fileValidate.add(".html");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if(!fileValidate.contains(idxFileName)) {
            throw new S3CommonException(S3ErrorCode.FILE_NOT_FOUND);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
