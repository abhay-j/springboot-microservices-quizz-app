package com.jabhay2012.question_service.Service;

import com.jabhay2012.question_service.Dto.QuizResponseDto;
import com.jabhay2012.question_service.Dto.UserResponseDTO;
import com.jabhay2012.question_service.Entity.Question;
import com.jabhay2012.question_service.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<List<Question>>  getAllQuestions(){
        try {
            return  new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return  new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionRepository.save(question);
        try{
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Question> updateQuestion(Integer id, Question question) {
        Optional<Question> prevQuestion = questionRepository.findById(id);
        if(prevQuestion.isPresent()){
            try {
                return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> deleteQuestion(Integer id){
        Optional<Question> question2delete = questionRepository.findById(id);
        if(question2delete.isPresent()){
            questionRepository.deleteById(id);
            return new  ResponseEntity<>("Deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        }

    }


    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQues) {
        List<Integer> questions  = questionRepository.findRandomQuestionsByCategory(category, numQues);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuizResponseDto>> getQuestionsFromID(List<Integer> questionIds) {
        List<QuizResponseDto> dtos = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer qId: questionIds){
            questions.add(questionRepository.findById(qId).get());
        }
        for(Question question: questions){
            QuizResponseDto responseDto = new QuizResponseDto();
            responseDto.setId(question.getId());
            responseDto.setQuestionTitle(question.getQuestionTitle());
            responseDto.setOption1(question.getOption1());
            responseDto.setOption2(question.getOption2());
            responseDto.setOption3(question.getOption3());
            responseDto.setOption4(question.getOption4());
            dtos.add(responseDto);

        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    public ResponseEntity<Integer> getScore(List<UserResponseDTO> responses) {
        int right = 0;
        for(UserResponseDTO response : responses){
            Optional<Question> question = questionRepository.findById(response.getId());
            if(response.getResponse().equals(question.get().getRightAnswer())){
                right++;
            }

        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
