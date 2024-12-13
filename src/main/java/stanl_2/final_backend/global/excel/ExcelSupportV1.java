package stanl_2.final_backend.global.excel;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ExcelSupportV1 {
    // 메타데이터 타입, 메타 데이터, 파일이름, 응답
    void download(Class<?> clazz, List<?> data, String fileName, HttpServletResponse response);
}
