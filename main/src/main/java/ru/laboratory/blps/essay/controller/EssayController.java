package ru.laboratory.blps.essay.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.laboratory.blps.essay.dto.CommentCreateDTO;
import ru.laboratory.blps.essay.dto.EssayDTO;
import ru.laboratory.blps.essay.dto.EssayUpdateDTO;
import ru.laboratory.blps.essay.dto.EssayUploadDTO;
import ru.laboratory.blps.essay.service.EssayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.laboratory.blps.auth.exceptions.UserNotFound;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/essay")
public class EssayController {

    private final EssayService essayService;

    @GetMapping("/watch/{id}")
    public ResponseEntity<EssayDTO> getEssay(@PathVariable long id){
        return ResponseEntity.ok(EssayDTO.create(essayService.getEssay(id)));
    }

    @PreAuthorize("hasAuthority(\"admin\")")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEssay(@PathVariable long id){
        log.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        essayService.deleteEssay(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority(\"user\", \"admin\")")
    @PostMapping
    public  ResponseEntity<Void> uploadEssay(@RequestBody EssayUploadDTO uploadDTO){
        log.info(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        essayService.createEssay(uploadDTO);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority(\"admin\")")
    @PutMapping
    public ResponseEntity<EssayDTO> updateEssay(@RequestBody EssayUpdateDTO updateDTO){
        return ResponseEntity.ok(EssayDTO.create(essayService.updateEssay(updateDTO)));
    }

    @PreAuthorize("hasAnyAuthority(\"user\", \"admin\")")
    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> commentEssay(@PathVariable long id, @RequestBody CommentCreateDTO commentCreateDTO,
                                             HttpServletRequest request) throws UserNotFound {
        String userId = (String) request.getAttribute("UserId");
        essayService.commentEssay(id, commentCreateDTO, Long.parseLong(userId));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority(\"user\", \"admin\")")
    @PostMapping("/{id}/mark")
    public ResponseEntity<Void> markEssay(@PathVariable long id, @RequestParam int mark){
        essayService.markEssay(id, mark);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category")
    public ResponseEntity<List<EssayDTO>> getByCategory(@RequestParam("category") String category){
        return ResponseEntity.ok(essayService.getByCategory(category)
                .stream().map(EssayDTO::create)
                .collect(Collectors.toList()));
    }

    @GetMapping()
    public ResponseEntity<List<EssayDTO>> getAll(){
        return ResponseEntity.ok(essayService.getAll()
                .stream().map(EssayDTO::create)
                .collect(Collectors.toList()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<EssayDTO>> searchByPhrase(@RequestParam("phrase") String phrase){
        return ResponseEntity.ok(essayService.searchByPhrase(phrase)
                .stream().map(EssayDTO::create)
                .collect(Collectors.toList()));
    }
}
