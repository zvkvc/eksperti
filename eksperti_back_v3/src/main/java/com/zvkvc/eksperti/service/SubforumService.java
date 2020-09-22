package com.zvkvc.eksperti.service;

import com.zvkvc.eksperti.exceptions.SubforumNotFoundException;
import com.zvkvc.eksperti.dao.SubforumRepository;
import com.zvkvc.eksperti.dto.SubforumDto;
import com.zvkvc.eksperti.mappings.SubforumMapper;
import com.zvkvc.eksperti.model.Subforum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubforumService {

    @Autowired
    private SubforumRepository subforumRepository;
    @Autowired
    private SubforumMapper subforumMapper;

    @Transactional(readOnly = true)
    public List<SubforumDto> getAll() {
        return subforumRepository.findAll()
                .stream()
                .map(subforumMapper::mapSubforumToDto)
                .collect(toList());
    }

    @Transactional
    public SubforumDto save(SubforumDto subforumDto) {
        Subforum subforum = subforumRepository.save(subforumMapper.mapDtoToSubforum(subforumDto));
        subforumDto.setId(subforum.getId());
        return subforumDto;
    }

    @Transactional(readOnly = true)
    public SubforumDto getSubforum(Long id) {
        Subforum subforum = subforumRepository.findById(id)
                .orElseThrow(() -> new SubforumNotFoundException("Subforum not found with id -" + id));
        return subforumMapper.mapSubforumToDto(subforum);
    }
    /*
    -------- No need for these mapping methods anymore since we're using MapStruct --------

    private SubforumDto mapToDto(Top subforum) { // map from subforum type to SubforumDto type
        return SubforumDto.builder().name(subforum.getName())
                .id(subforum.getId())
                .postCount(subforum.getPosts().size())
                .build();
    }

    private subforum mapToSubforum(SubforumDto subforumDto) {
        return subforum.builder().name("/r/" + subforumDto.getName())
                .description(subforumDto.getDescription())
                .user(authService.getCurrentUser())
                .createdDate(Instant.now()).build();
    }
    */
}
