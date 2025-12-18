package com.galleria.bank.service.user;

import com.galleria.bank.dto.user.UserCreateDTO;
import com.galleria.bank.dto.user.UserResponseDTO;
import com.galleria.bank.dto.user.UserUpdateDTO;
import com.galleria.bank.entity.user.UserEntity;
import com.galleria.bank.exception.ApiException;

import com.galleria.bank.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {

        if (repository.existsByLogin(dto.getLogin())) {
            throw new ApiException("Usuario com login: " + dto.getLogin() + " já existe.", HttpStatus.CONFLICT);
        }

        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setLogin(dto.getLogin());
        user.setPassword(encoder.encode(dto.getPassword()));

        repository.save(user);

        return new UserResponseDTO(user.getId(), user.getName(), user.getLogin());
    }

    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO dto) {

        UserEntity user = repository.findById(id)
                .orElseThrow(() -> new ApiException("Usuário com ID: " + id + " não encontrado.", HttpStatus.NOT_FOUND));

        if (!user.getLogin().equals(dto.getLogin())
                && repository.existsByLogin(dto.getLogin())) {
            throw new ApiException("Usuario com login: " + dto.getLogin() + " já existe.", HttpStatus.CONFLICT);
        }

        user.setName(dto.getName());
        user.setLogin(dto.getLogin());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getLogin()
        );
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getLogin()
                ));
    }

    @Transactional
    public void deleteById(Long id) {

        if (!repository.existsById(id)) {
            throw new ApiException("Usuário com ID: " + id + " não encontrado.", HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);
    }
}
