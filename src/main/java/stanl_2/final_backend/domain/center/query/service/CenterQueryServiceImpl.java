package stanl_2.final_backend.domain.center.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.query.dto.CenterExcelDownload;
import stanl_2.final_backend.domain.center.query.dto.CenterSearchRequestDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;
import stanl_2.final_backend.domain.center.query.repository.CenterMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service("queryCenterServiceImpl")
public class CenterQueryServiceImpl implements CenterQueryService {

    private final CenterMapper centerMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public CenterQueryServiceImpl(CenterMapper centerMapper, RedisTemplate<String, Object> redisTemplate, ExcelUtilsV1 excelUtilsV1) {
        this.centerMapper = centerMapper;
        this.redisTemplate = redisTemplate;
        this.excelUtilsV1 = excelUtilsV1;
    }

    @Override
    @Transactional
    public CenterSelectIdDTO selectByCenterId(String id) {

        CenterSelectIdDTO centerSelectIdDTO = centerMapper.findCenterById(id);

        return centerSelectIdDTO;
    }

    @Override
    @Transactional
    public Page<CenterSelectAllDTO> selectAll(Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<CenterSelectAllDTO> centerList = centerMapper.findCenterAll(size, offset, sortField, sortOrder);

        int total = centerMapper.findCenterCount();

        return new PageImpl<>(centerList, pageable, total);
    }

    @Override
    @Transactional
    public Page<CenterSelectAllDTO> selectBySearch(CenterSearchRequestDTO centerSearchRequestDTO, Pageable pageable){
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<CenterSelectAllDTO> centerList = centerMapper.findCenterBySearch(size, offset, centerSearchRequestDTO, sortField, sortOrder);
        int total = centerMapper.findCenterBySearchCount(centerSearchRequestDTO);

        return new PageImpl<>(centerList, pageable, total);
    }

    @Override
    @Transactional
    public List<CenterSelectAllDTO> selectCenterListBySearch(CenterSearchRequestDTO centerSearchRequestDTO){

        List<CenterSelectAllDTO> centerList = centerMapper.findCenterListBySearch(centerSearchRequestDTO);

        return centerList;
    }

    @Override
    @Transactional
    public String selectNameById(String id) {

        String centerName = centerMapper.findNameById(id);
        return centerName;
    }

    @Override
    @Transactional
    public void exportCenterToExcel(HttpServletResponse response) {
        List<CenterExcelDownload> centerList = centerMapper.findCentersForExcel();

        excelUtilsV1.download(CenterExcelDownload.class, centerList, "centerExcel", response);
    }
}
