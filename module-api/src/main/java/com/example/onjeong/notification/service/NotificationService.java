package com.example.onjeong.notification.service;


import com.example.onjeong.board.domain.Board;
import com.example.onjeong.family.domain.Family;
import com.example.onjeong.mail.domain.Mail;
import com.example.onjeong.notification.domain.Notifications;
import com.example.onjeong.notification.dto.DeviceTokenRequest;
import com.example.onjeong.notification.dto.NotificationDto;
import com.example.onjeong.notification.repository.NotificationRepository;
import com.example.onjeong.profile.domain.Profile;
import com.example.onjeong.question.domain.Answer;
import com.example.onjeong.question.domain.Question;
import com.example.onjeong.user.domain.User;
import com.example.onjeong.util.AuthUtil;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final AuthUtil authUtil;

    @Transactional
    public void registerToken(String token) throws FirebaseMessagingException {
        User user = authUtil.getUserByAuthentication();
        if(user.getCheckNotification()) user.updateDeviceToken(token);
    }

    @Transactional
    public void deleteToken() throws FirebaseMessagingException {
        User user = authUtil.getUserByAuthentication();
        user.updateDeviceToken("");
    }

    public Boolean checkNotification() {
        User user = authUtil.getUserByAuthentication();
        return user.getCheckNotification();
    }

    @Transactional
    public void updateNotification(DeviceTokenRequest deviceTokenRequest) {
        User user = authUtil.getUserByAuthentication();
        user.updateCheckNotification(deviceTokenRequest.getCheck());
        if(deviceTokenRequest.getCheck()){
            user.updateDeviceToken(deviceTokenRequest.getToken());
        }else{
            user.updateDeviceToken("");
        }
    }

    public List<NotificationDto> getNotifications(){
        User user = authUtil.getUserByAuthentication();
        List<Notifications> notificationsList = notificationRepository.findAllByUserId(user.getUserId());
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for(Notifications n : notificationsList){

            Long days = ChronoUnit.DAYS.between(n.getNotificationTime(), LocalDateTime.now());
            Long hours = ChronoUnit.HOURS.between(n.getNotificationTime(), LocalDateTime.now());
            Long minutes = ChronoUnit.SECONDS.between(n.getNotificationTime(), LocalDateTime.now())/60;

            String notificationTime = days != 0 ? days.toString() + "일전" : hours != 0  ? hours.toString() + "시간전" : minutes.toString() + "분전";

            notificationDtoList.add(NotificationDto.builder()
                    .notificationContent(n.getNotificationContent())
                    .notificationTime(notificationTime)
                    .build());
        }
        return notificationDtoList;
    }


    public void sendMail(Mail mail) throws FirebaseMessagingException {
        // 메일 전송 시 받는 사람에게 알림 전송
        String token = mail.getReceiveUser().getDeviceToken();
        String content = mail.getSendUser().getUserStatus() + "(으)로부터 메일이 도착했습니다.";

        saveNotifications(content, mail.getReceiveUser());
        sendPersonalAlarm(content, token);
    }

    public void sendFamilyCheck(Answer answer) throws FirebaseMessagingException {
        // 한 명만 안썼으면 그 한명에게 알림 전송
        Question question = answer.getQuestion();
        List<Answer> answers = question.getAnswerList();
        Family family = answer.getUser().getFamily();

        List<User> answeredFamily = new ArrayList<>();
        List<User> notAnsweredFamily = new ArrayList<>();
        for(Answer a : answers){
            if(!answeredFamily.contains(a.getUser())) answeredFamily.add(a.getUser());
        }
        for(User u : family.getUsers()){
            if(!answeredFamily.contains(u)) notAnsweredFamily.add(u);
        }

        if(notAnsweredFamily.size() == 1){
            // 메일 수신 알림
            String token = notAnsweredFamily.get(0).getDeviceToken();
            String content = notAnsweredFamily.get(0).getUserStatus() + "님만 답변하면 가족 전체의 답변이 완성됩니다";

            saveNotifications(content, notAnsweredFamily.get(0));
            sendPersonalAlarm(content, token);
        }
    }

    public void sendBoard(Board board) throws FirebaseMessagingException {
        // 오늘의 기록 작성했을 때 알림
        String topic = board.getFamily().getFamilyId().toString();
        String content = board.getUser().getUserStatus() + "님이 오늘의 기록을 작성했습니다.";

        //알림 전송
        for(User u : board.getFamily().getUsers()){
            if(!u.equals(board.getUser())) saveNotifications(content, u);
        }
        sendFamilyAlarm(content, topic);
    }

    public void sendAnswer(Answer answer) throws FirebaseMessagingException {
        // 이주의 문답 답변 작성했을 때 알림
        String topic = answer.getUser().getFamily().getFamilyId().toString();
        String content = answer.getUser().getUserStatus() + "님이 이주의 문답에 대한 답변을 작성했습니다.";

        for(User u : answer.getUser().getFamily().getUsers()){
            if(!u.equals(answer.getUser())) saveNotifications(content, u);
        }
        sendFamilyAlarm(content, topic);
    }

    public void sendProfileModify(Profile profile) throws FirebaseMessagingException {
        // 프로필이 수정될 때 알림
        String topic = profile.getFamily().getFamilyId().toString();
        String content = profile.getUser().getUserStatus() + "님의 프로필이 수정되었습니다.";

        for(User u : profile.getFamily().getUsers()){
            if(!u.equals(profile.getUser())) saveNotifications(content, u);
        }
        sendFamilyAlarm(content, topic);
    }


    public void sendPersonalAlarm(String content, String token) throws FirebaseMessagingException {
        try{
            Message message = Message.builder()
                    .putData("time", LocalDateTime.now().toString())
                    .setNotification(new Notification("On:Jeong", content))
                    .setToken(token)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("메시지 전송 알림 완료 : " + response);
        } catch (Exception e){} // 에러가 발생해도 무시하고 다음 코드 진행
    }

    public void sendFamilyAlarm(String content, String topic) throws FirebaseMessagingException {
        try{
            Message message = Message.builder()
                    .putData("time", LocalDateTime.now().toString())
                    .setNotification(new Notification("OnJeong", content))
                    .setTopic(topic)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("메시지 전송 알림 완료 : " + response);
        } catch (Exception e){} // 에러가 발생해도 무시하고 다음 코드 진행
    }

    @Transactional
    public void saveNotifications(String content, User user){
        Notifications notification = Notifications.builder()
                .notificationContent(content)
                .notificationTime(LocalDateTime.now())
                .user(user)
                .build();
        notificationRepository.save(notification);
    }
}
