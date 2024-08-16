package com.jabhay2012.question_service.controller;

import com.jabhay2012.question_service.Dto.QuizResponseDto;
import com.jabhay2012.question_service.Dto.UserResponseDTO;
import com.jabhay2012.question_service.Entity.Question;
import com.jabhay2012.question_service.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/allquestions")
    public ResponseEntity<List<Question>> getAllQuestions(){

        return questionService.getAllQuestions();
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>>  getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String>  addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Integer id, @RequestBody Question question){
        return  questionService.updateQuestion(id, question);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
    }
   // generate questions for quiz
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category, @RequestParam Integer numQues){
        return questionService.getQuestionsForQuiz(category, numQues);
    }
    // get questions from ID
    @PostMapping("/getquestions")
    public ResponseEntity<List<QuizResponseDto>> getQuestionsFromID(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromID(questionIds);
    }
     //get score
    @PostMapping("/getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<UserResponseDTO> responses){
        return questionService.getScore(responses);
    }
}
