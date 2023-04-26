package com.example.onjeong.question.service;

import com.example.onjeong.error.ErrorCode;
import com.example.onjeong.question.domain.Answer;
import com.example.onjeong.question.domain.Question;
import com.example.onjeong.question.dto.AnswerDto;
import com.example.onjeong.question.dto.AnswerModifyRequestDto;
import com.example.onjeong.question.dto.QuestionDto;
import com.example.onjeong.question.exception.AnswerDuplicateException;
import com.example.onjeong.question.exception.AnswerNotExistException;
import com.example.onjeong.question.exception.NullQuestionException;
import com.example.onjeong.question.repository.AnswerRepository;
import com.example.onjeong.question.repository.PureQuestionRepository;
import com.example.onjeong.question.repository.QuestionRepository;
import com.example.onjeong.user.domain.User;
import com.example.onjeong.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final AuthUtil authUtil;

    public QuestionDto showWeeklyQuestion(){
        User user = authUtil.getUserByAuthentication();
        Question question = questionRepository.findWeeklyQuestion(user.getFamily().getFamilyId());
        if(question == null){ // 서버에 준비된 이주의 질문 내용이 없는 경우
            throw new NullQuestionException("weekly question not exist", ErrorCode.QUESTION_NOTEXIST);
        }
        QuestionDto questionDto = QuestionDto.builder()
                .questonId(question.getQuestionId())
                .questionContent(question.getQuestionContent())
                .build();
        return questionDto;
    }

    public List<AnswerDto> showWeeklyAnswer(){
        User user = authUtil.getUserByAuthentication();
        Question question = questionRepository.findWeeklyQuestion(user.getFamily().getFamilyId());
        if(question == null){ // 서버에 준비된 이주의 질문 내용이 없는 경우
            throw new NullQuestionException("weekly question not exist", ErrorCode.QUESTION_NOTEXIST);
        }
        List<Answer> answers = answerRepository.findByQuestion(question);
        List<AnswerDto> answerDtos = new ArrayList<>();
        for(Answer a : answers){
            AnswerDto answer = AnswerDto.builder()
                    .answerId(a.getAnswerId())
                    .userName(a.getUser().getUserName())
                    .answerContent(a.getAnswerContent())
                    .answerTime(a.getAnswerTime())
                    .build();
            answerDtos.add(answer);
        }
        return answerDtos;
    }


    public List<QuestionDto> showQuestion(Pageable pageable){
        User user = authUtil.getUserByAuthentication();

        List<Question> questionList = questionRepository.findAllByFamily(pageable, user.getFamily()).toList();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for(Question q : questionList){
            QuestionDto questionDto = QuestionDto.builder()
                    .questonId(q.getQuestionId())
                    .questionContent(q.getQuestionContent())
                    .build();
            questionDtoList.add(questionDto);
        }

        return questionDtoList;
    }

    public List<AnswerDto> showAllAnswer(Long questionId){
        Question question = questionRepository.findById(questionId)
                .orElseThrow(()-> new NullQuestionException("question not exist", ErrorCode.QUESTION_NOTEXIST));
        List<Answer> answers = answerRepository.findByQuestion(question);
        List<AnswerDto> answerDtos = new ArrayList<>();

        for(Answer a : answers){
            AnswerDto answer = AnswerDto.builder()
                    .answerId(a.getAnswerId())
                    .userName(a.getUser().getUserName())
                    .answerContent(a.getAnswerContent())
                    .answerTime(a.getAnswerTime())
                    .build();
            answerDtos.add(answer);
        }

        return answerDtos;
    }

    public List<String> showAllAnswerFamily(){
        User user = authUtil.getUserByAuthentication();
        Question question = questionRepository.findWeeklyQuestion(user.getFamily().getFamilyId());
        if(question == null){ // 서버에 준비된 이주의 질문 내용이 없는 경우
            throw new NullQuestionException("weekly question not exist", ErrorCode.QUESTION_NOTEXIST);
        }

        List<String> answeredFamily = new ArrayList<>();
        List<Answer> answers = answerRepository.findByQuestion(question);

        for(Answer a : answers){
            answeredFamily.add(a.getUser().getUserStatus());
        }

        return answeredFamily;
    }

    public Boolean answerFamilyCheck(){
        User user = authUtil.getUserByAuthentication();
        Question question = questionRepository.findWeeklyQuestion(user.getFamily().getFamilyId());
        if(question == null){ // 서버에 준비된 이주의 질문 내용이 없는 경우
            throw new NullQuestionException("weekly question not exist", ErrorCode.QUESTION_NOTEXIST);
        }

        List<Answer> answers = answerRepository.findByQuestion(question);
        List<User> answeredFamily = new ArrayList<>();
        for(Answer a : answers){
            if(!answeredFamily.contains(a.getUser())) answeredFamily.add(a.getUser());
        }

        if(answeredFamily.equals(question.getFamily().getUsers())) return true;
        else return false;

    }

    @Transactional
    public Answer registerAnswer(String answerContent){
        User user = authUtil.getUserByAuthentication();
        Question question = questionRepository.findWeeklyQuestion(user.getFamily().getFamilyId());
        if(question == null){ // 서버에 준비된 이주의 질문 내용이 없는 경우
            throw new NullQuestionException("weekly question not exist", ErrorCode.QUESTION_NOTEXIST);
        }

        System.out.println("hi!");
        List<Answer> answerList = answerRepository.findByQuestionAndUser(question, user);
        System.out.println(answerList.size());
        if(answerList.size() != 0){
            throw new AnswerDuplicateException("User already created an answer", ErrorCode.ANSWER_DUPLICATE);
        }

        Answer answer = Answer.builder()
                .question(question)
                .user(user)
                .answerContent(answerContent)
                .answerTime(LocalDateTime.now())
                .build();

        answerRepository.save(answer);

        return answer;
    }

    @Transactional
    public void modifyAnswer(AnswerModifyRequestDto answerModifyRequestDto) {
        Answer answer = answerRepository.findById(answerModifyRequestDto.getAnswerId())
                .orElseThrow(() -> new AnswerNotExistException("answer not exist", ErrorCode.ANSWER_NOTEXIST));

        if (answerModifyRequestDto.getAnswerContent() != null) {
            answer.updateContent(answerModifyRequestDto.getAnswerContent());
        }
    }

    @Transactional
    public void deleteAnswer(Long answerId){
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotExistException("answer not exist", ErrorCode.ANSWER_NOTEXIST));

        answerRepository.delete(answer);
    }

}
