package com.example.ClinicalDecisionSupportSystemService.controller;


import com.example.ClinicalDecisionSupportSystemService.dto.ProgressDto;
import com.example.ClinicalDecisionSupportSystemService.service.ProgressService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@AllArgsConstructor
public class ProgressController {
    private ProgressService progressService;
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ProgressDto progressDto)
    {
       progressService.createProgress(progressDto);
       return new ResponseEntity<>("Progress Added", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody ProgressDto progressDto,@PathVariable long id)
    {
        progressService.updateProgress(progressDto,id);
        return new ResponseEntity<>("Progress Status Changed", HttpStatus.OK);
    }
    @GetMapping("/getCompletedProgress")
    public ResponseEntity<List<ProgressDto>> getCompletedProgress()
    {
        List<ProgressDto> progressDtos=progressService.completedProgressesByPatientId();
        return new ResponseEntity<>(progressDtos,HttpStatus.OK);
    }
    @GetMapping("/getInCompletedProgress")
    public ResponseEntity<List<ProgressDto>> getInCompletedProgress()
    {
        List<ProgressDto> progressDtos=progressService.incompleteProgressesByPatientId();
        return new ResponseEntity<>(progressDtos,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id)
    {
        progressService.deleteProgress(id);
        return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
    }

}
