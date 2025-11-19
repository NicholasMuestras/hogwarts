package org.skypro.hogwarts.service;

import org.skypro.hogwarts.model.Avatar;
import org.skypro.hogwarts.repository.AvatarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Page<Avatar> getAvatars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return this.avatarRepository.findAll(pageable);
    }
}
