package com.jabhay2012.quiz_service.Feign;

import com.jabhay2012.quiz_service.Dto.QuizResponseDto;
import com.jabhay2012.quiz_service.Dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
    // generate questions for quiz
    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category, @RequestParam Integer numQues);
    // get questions from ID
    @PostMapping("questions/getquestions")
    public ResponseEntity<List<QuizResponseDto>> getQuestionsFromID(@RequestBody List<Integer> questionIds);
    //get score
    @PostMapping("questions/getscore")
    public ResponseEntity<Integer> getScore(@RequestBody List<UserResponseDTO> responses);
}
