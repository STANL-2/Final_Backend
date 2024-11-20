package stanl_2.final_backend.domain.alarm.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import stanl_2.final_backend.domain.alarm.command.domain.aggregate.entity.Alarm;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmQueryDTO;

import java.util.List;

@Mapper
public interface AlarmMapper {
    List<AlarmQueryDTO> findAlarmsByMemberId(@Param("memberId") String memberId,
                                             @Param("size") Integer size);

    List<AlarmQueryDTO> findAlarmsByMemberIdAndCursorId(@Param("memberId") String memberId,
                                                @Param("cursorId") Long cursorId,
                                                @Param("size") Integer size);

}
