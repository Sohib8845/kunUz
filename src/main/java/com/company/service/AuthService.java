package com.company.service;

import com.company.dto.profile.ProfileDTO;
import com.company.dto.RegistrationDTO;
import com.company.dto.auth.AuthorizationDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exeptions.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import com.company.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private EmailService emailService;

    public ProfileDTO authorization(AuthorizationDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPswd());

        Optional<ProfileEntity> op = profileRepository.findByLoginAndPswd(dto.getLogin(), pswd);

        if (!op.isPresent()) {
            throw new RuntimeException("Login or Password incorrect");
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(op.get().getName());
        profileDTO.setSurname(op.get().getSurname());
        profileDTO.setRole(op.get().getRole());
        profileDTO.setJwt(JwtUtil.createJwt(op.get().getId(),profileDTO.getRole()));
        return profileDTO;

    }


    public void registration(RegistrationDTO dto){
        Optional<ProfileEntity> op = profileRepository.findByLogin(dto.getLogin());
        if (op.isPresent()) {
            throw new RuntimeException("Login is already exists");
        }
        op=profileRepository.findByEmail(dto.getEmail());
        if(op.isPresent()){
            throw new RuntimeException("Email is already exists");
        }

        //Algorith md5
        String pswd = DigestUtils.md5Hex(dto.getPswd());

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLogin(dto.getLogin());
        entity.setPswd(pswd);
        entity.setEmail(dto.getEmail());
        entity.setRole(ProfileRole.USER_ROLE);
        entity.setStatus(ProfileStatus.CREATED);
        profileRepository.save(entity);


//        StringBuilder builder = new StringBuilder();
//        builder.append("Salom Jigar Qalaysan ");
//        builder.append("Agar bu sen bo'lsang shu linkga bos ");
        String jwt = JwtUtil.createJwt(entity.getId());
//        builder.append("http://localhost:8080/auth/verification/"+jwt);
        emailService.sendEmail(dto.getEmail(),jwt);

    }


    public void verification(String jwt) {
        Integer id = JwtUtil.decodeJwtId(jwt);
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ItemNotFoundException("User not found");
        }
        if (optional.get().getStatus().equals(ProfileStatus.CREATED)) {
            optional.get().setStatus(ProfileStatus.ACTIVE);
//            emailService.updateEmailHistory();
            profileRepository.save(optional.get());
        } else {
            throw new ItemNotFoundException("User not created");
        }
    }

}
