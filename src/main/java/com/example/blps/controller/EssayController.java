package com.example.blps.controller;

import com.example.blps.model.referat.Essay;
import com.example.blps.model.referat.dto.CommentCreateDTO;
import com.example.blps.model.referat.dto.EssayUpdateDTO;
import com.example.blps.model.referat.dto.EssayUploadDTO;
import com.example.blps.service.essay.EssayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/essay")
public class EssayController {

    private final EssayService essayService;

    @GetMapping("/watch/{id}")
    public ResponseEntity<Essay> getEssay(@PathVariable long id){
        return ResponseEntity.ok(essayService.getEssay(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEssay(@PathVariable long id){
        essayService.deleteEssay(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public  ResponseEntity<Void> uploadEssay(@RequestBody EssayUploadDTO uploadDTO){
        essayService.createEssay(uploadDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Essay> updateEssay(@RequestBody EssayUpdateDTO updateDTO){
        return ResponseEntity.ok(essayService.updateEssay(updateDTO));
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> commentEssay(@PathVariable long id, @RequestBody CommentCreateDTO commentCreateDTO){
        essayService.commentEssay(id, commentCreateDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/mark")
    public ResponseEntity<Void> markEssay(@PathVariable long id, @RequestParam int mark){
        essayService.markEssay(id, mark);
        return ResponseEntity.ok().build();
    }
}
