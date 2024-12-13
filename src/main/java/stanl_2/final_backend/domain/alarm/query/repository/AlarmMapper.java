package stanl_2.final_backend.domain.alarm.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectUnreadDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectReadDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;

import java.util.List;

@Mapper
public interface AlarmMapper {
    AlarmSelectTypeDTO findNumberOfAlarmsByType(String memberId);

    List<AlarmSelectReadDTO> findReadAlarmsByType(@Param("offset") Integer offset,
                                                  @Param("pageSize") Integer pageSize,
                                                  @Param("memberId") String memberId,
                                                  @Param("type") String type);

    List<AlarmSelectUnreadDTO> findUnReadAlarmsByType(@Param("offset") Integer offset,
                                                       @Param("pageSize") Integer pageSize,
                                                       @Param("memberId") String memberId,
                                                       @Param("type") String type);

    List<AlarmSelectDTO> findAllAlarmsByType(@Param("offset") Integer offset,
                                             @Param("pageSize") Integer pageSize,
                                             @Param("memberId") String memberId,
                                             @Param("type") String type);

    Integer findReadAlarmsCountByMemberId(String memberId);

    Integer findUnreadAlarmsCountByMemberId(String memberId);
}
