package com.jabhay2012.quiz_service.controller;


import com.jabhay2012.quiz_service.Dto.QuizDto;
import com.jabhay2012.quiz_service.Dto.QuizResponseDto;
import com.jabhay2012.quiz_service.Dto.UserResponseDTO;
import com.jabhay2012.quiz_service.Service.Quizservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    Quizservice quizservice;
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizservice.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuizResponseDto>> getQuizQuestions(@PathVariable Integer id){
        return quizservice.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<UserResponseDTO> responses){
        return quizservice.calculateResult(id, responses);
    }
}
