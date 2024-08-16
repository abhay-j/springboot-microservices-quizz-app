package com.jabhay2012.quiz_service.Service;

import com.jabhay2012.quiz_service.Dto.QuizResponseDto;
import com.jabhay2012.quiz_service.Dto.UserResponseDTO;
import com.jabhay2012.quiz_service.Entity.Quiz;
import com.jabhay2012.quiz_service.Feign.QuizInterface;
import com.jabhay2012.quiz_service.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Quizservice {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

//    @Autowired
//    QuestionRepository questionRepository;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        //we need to get random questions by category from the database

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    public ResponseEntity<List<QuizResponseDto>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Integer> questionIds = quiz.get().getQuestionIds();

        ResponseEntity<List<QuizResponseDto>> questions =  quizInterface.getQuestionsFromID(questionIds);

        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<UserResponseDTO> responses) {
           ResponseEntity<Integer> score = quizInterface.getScore(responses);
           return score;
    }
}
