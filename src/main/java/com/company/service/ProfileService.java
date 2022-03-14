package com.company.service;

import com.company.enums.ProfileStatus;
import com.company.repository.ProfileRepository;
import com.company.dto.profile.ProfileDTO;
import com.company.entity.ProfileEntity;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        String pswd = DigestUtils.md5Hex(dto.getPswd());
        entity.setPswd(pswd);
        entity.setEmail(dto.getEmail());
        entity.setRole(dto.getRole());
        entity.setStatus(ProfileStatus.CREATED);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<ProfileDTO> getList() {
        List<ProfileDTO> dtoList=new LinkedList<>();
        List<ProfileEntity> entityList = profileRepository.findAll();
        entityList.forEach(e->dtoList.add(toDto(e)));
        return dtoList;
    }

    public boolean update(ProfileDTO p, Integer pid) {
        if (profileRepository.existsById(pid)) {
            profileRepository.update(p.getName(), p.getSurname(), p.getEmail(), p.getLogin(), p.getPswd(), pid);
            return true;
        }
        return false;
    }

    public ProfileDTO deleteByAdmin(Integer pid) {
        if (profileRepository.existsById(pid)) {
            ProfileDTO dto = profileRepository.findById(pid).map(this::toDto).get();
            profileRepository.deleteById(pid);
            return dto;
        }
        return null;
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile Not Found"));
    }

    public ProfileDTO getByID(Integer id) {
        Optional<ProfileEntity> entity = profileRepository.findById(id);
        return entity.map(this::toDto).orElseThrow(()->new RuntimeException("Not Found"));
    }

    public ProfileDTO toDto(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLogin(entity.getLogin());
        dto.setPswd(entity.getPswd());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        return dto;
    }

}
