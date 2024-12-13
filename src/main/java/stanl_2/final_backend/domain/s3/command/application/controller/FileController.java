package stanl_2.final_backend.domain.s3.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.s3.command.application.dto.FileDTO;
import stanl_2.final_backend.domain.s3.command.domain.service.S3FileServiceImpl;
import stanl_2.final_backend.domain.s3.common.response.S3ResponseMessage;

@RestController("commandFileController")
@RequestMapping("/api/v1/file")
public class FileController {
    private final S3FileServiceImpl s3FileService;

    @Autowired
    public FileController(S3FileServiceImpl s3FileService) {
        this.s3FileService = s3FileService;
    }

    @Operation(summary = "파일 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = S3ResponseMessage.class))})
    })
    @PostMapping(value = "")
    public ResponseEntity<S3ResponseMessage> postFile(FileDTO fileDTO,@RequestPart("file") MultipartFile file) {
        fileDTO.setFileUrl(s3FileService.uploadOneFile(file));
        String url=s3FileService.uploadOneFile(file);
        s3FileService.registerFile(fileDTO);
        return ResponseEntity.ok(S3ResponseMessage.builder()
                .httpStatus(200)
                .msg(url)
                .result(null)
                .build());

    }
}