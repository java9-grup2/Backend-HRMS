package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.CommentSaveRequestDto;
import org.hrms.dto.response.CommentSaveResponseDto;
import org.hrms.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.hrms.constant.EndPoints.COMMENT;
import static org.hrms.constant.EndPoints.SAVE;

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

}
