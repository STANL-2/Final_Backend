package stanl_2.final_backend.domain.s3;

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
import stanl_2.final_backend.global.config.S3Config;

import java.io.IOException;
import java.io.InputStream;
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
            throw new RuntimeException(e);
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
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if(!fileValidate.contains(idxFileName)) {
            throw new S3CommonException(S3ErrorCode.FILE_NOT_FOUND);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
