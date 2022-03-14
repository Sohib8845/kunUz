package com.company.service;

import com.company.dto.RegionDTO;
import com.company.entity.RegionEntity;
import com.company.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto){
        RegionEntity entity = new RegionEntity();
        entity.setName(dto.getName());

        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public String update(RegionDTO dto, Integer id){
        RegionEntity entity = new RegionEntity();
        entity.setName(dto.getName());
        entity.setId(id);
        regionRepository.save(entity);
        dto.setId(entity.getId());
        return "Updated";
    }

    public String delete(Integer id){
        regionRepository.deleteById(id);
        return "Deleted";
    }

    public RegionDTO getById(Integer id){
        RegionEntity entity = regionRepository.getById(id);
        return toDto(entity);
    }

    public List<RegionDTO> list(){
        List<RegionEntity> entityList =
                regionRepository.findAll();
        List<RegionDTO> dtoList = new LinkedList<>();
        for(RegionEntity entity:entityList){
            dtoList.add(toDto(entity));
        }
        return dtoList;
    }


    public RegionDTO toDto(RegionEntity entity){
        RegionDTO dto = new RegionDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

}
