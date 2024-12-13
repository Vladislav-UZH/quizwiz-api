package com.example.kahoot.controller;

import com.example.kahoot.dto.OptionDto;
import com.example.kahoot.interfaces.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<OptionDto> createOption(@RequestBody OptionDto optionDto) {
        OptionDto createdOption = optionService.createOption(optionDto);
        return ResponseEntity.ok(createdOption);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionDto> getOptionById(@PathVariable Long id) {
        OptionDto option = optionService.getOptionById(id);
        return ResponseEntity.ok(option);
    }

    @GetMapping("/by-question/{questionId}")
    public ResponseEntity<List<OptionDto>> getOptionsByQuestionId(@PathVariable Long questionId) {
        List<OptionDto> options = optionService.getOptionsByQuestionId(questionId);
        return ResponseEntity.ok(options);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OptionDto> updateOption(@PathVariable Long id, @RequestBody OptionDto optionDto) {
        OptionDto updatedOption = optionService.updateOption(id, optionDto);
        return ResponseEntity.ok(updatedOption);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.ok().build();
    }
}
