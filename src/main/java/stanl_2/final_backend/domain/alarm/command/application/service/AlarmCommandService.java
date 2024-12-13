package stanl_2.final_backend.domain.alarm.command.application.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.command.application.dto.AlarmRegistDTO;
import stanl_2.final_backend.domain.alarm.command.domain.aggregate.entity.Alarm;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractAlarmDTO;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeAlarmDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderAlarmDTO;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderAlarmDTO;
import stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity.PurchaseOrder;

import java.security.GeneralSecurityException;

public interface AlarmCommandService {
    SseEmitter subscribe(AlarmRegistDTO alarmRegistDTO, HttpServletResponse response);

    void sendToClient(SseEmitter emitter, String emitterId, Object data);

    void send(String memberId, String adminId, String contentId, String message, String redirectUrl, String tag,
              String type, String createdAt);

    Alarm createAlarm(String memberId, String adminId,String contentId, String message, String redirectUrl, String tag,
                      String type, String createdAt);

    void sendNoticeAlarm(NoticeAlarmDTO noticeAlarmDTO);

    Boolean updateReadStatus(String alarmId);

    void sendContractAlarm(ContractAlarmDTO contractAlarmDTO) throws GeneralSecurityException;

    void sendPurchaseOrderAlarm(PurchaseOrderAlarmDTO purchaseOrderAlarmDTO) throws GeneralSecurityException;

    void sendOrderAlarm(OrderAlarmDTO orderAlarmDTO) throws GeneralSecurityException;
}
