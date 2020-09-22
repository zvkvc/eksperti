package com.zvkvc.eksperti.controller;

import com.zvkvc.eksperti.dto.SubforumDto;
import com.zvkvc.eksperti.service.SubforumService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subforums/")
@AllArgsConstructor
// @Slf4j // logging facade
public class SubforumController {

    @Autowired
    private SubforumService subforumService;

    @GetMapping
    public List<SubforumDto> getAllSubforums() {
        return subforumService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubforumDto> getSubforum(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(subforumService.getSubforum(id));
    }

    @PostMapping
    public SubforumDto create(@RequestBody @Valid SubforumDto subforumDto) {
        return subforumService.save(subforumDto);
    }


}
