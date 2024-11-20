package stanl_2.final_backend.domain.alarm.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDetailDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface AlarmMapper {
    AlarmSelectTypeDTO findNumberOfAlarmsByType(String memberId);

    List<AlarmSelectDetailDTO> findReadAlarmsByType(@Param("offset") Integer offset,
                                                    @Param("pageSize") Integer pageSize,
                                                    @Param("memberId") String memberId,
                                                    @Param("type") String type);

    List<AlarmSelectDetailDTO> findNotReadAlarmsByType(@Param("offset") Integer offset,
                                                       @Param("pageSize") Integer pageSize,
                                                       @Param("memberId") String memberId,
                                                       @Param("type") String type);
}
