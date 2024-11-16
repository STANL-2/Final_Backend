package stanl_2.final_backend.domain.alarm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.final_backend.domain.alarm.repository.AlarmRepository;

import java.util.Map;

@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmServiceImpl(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @Override
    public SseEmitter subscribe(AlarmDTO alarmDTO) {

        return null;
    }
}
