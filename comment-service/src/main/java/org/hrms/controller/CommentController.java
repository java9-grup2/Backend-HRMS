package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CommentSaveRequestDto;
import org.hrms.dto.response.CommentSaveResponseDto;
import org.hrms.repository.entity.Comment;
import org.hrms.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hrms.constant.EndPoints.*;

@RestController
@RequestMapping(COMMENT)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;


    @PostMapping(SAVE)
    public ResponseEntity<CommentSaveResponseDto> saveEmployeeComment(@RequestBody CommentSaveRequestDto dto) {
        return ResponseEntity.ok(service.saveEmployeeComment(dto));
    }

    @GetMapping("/gatewaytest")
    public ResponseEntity<String> testinggateway() {
        return ResponseEntity.ok("Calisiyor");
    }

    @GetMapping(GETALLPENDINGCOMMENTS)
    public ResponseEntity<List<Comment>> getAllPendingComments() {
        return ResponseEntity.ok(service.getAllPendingComments());
    }

    @PutMapping(APPROVECOMMENT)
    public ResponseEntity<Boolean> approveComment(@RequestParam Long id) {
        return ResponseEntity.ok(service.approveComment(id));
    }

    @DeleteMapping(DELETECOMMENT)
    public ResponseEntity<Boolean> deleteCommentById(@RequestParam Long id) {
        return ResponseEntity.ok(service.deleteCommentById(id));
    }
}
