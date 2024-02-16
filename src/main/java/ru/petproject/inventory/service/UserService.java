package ru.petproject.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petproject.inventory.common.Utility;
import ru.petproject.inventory.dto.UserDto;
import ru.petproject.inventory.dto.UserNewDto;
import ru.petproject.inventory.dto.UserUpdateDto;
import ru.petproject.inventory.mapper.UserMapper;
import ru.petproject.inventory.model.Role;
import ru.petproject.inventory.model.User;
import ru.petproject.inventory.service.base.BaseItemService;
import ru.petproject.inventory.service.base.BaseUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BaseUserService baseUserService;
    private final BaseItemService baseItemService;

    @Transactional
    public UserDto postUser(Long userId, UserNewDto userNewDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        baseUserService.checkExistsUser(userNewDto.getEmail());
        baseUserService.checkExistsUser(user.getOrganization(), userNewDto.getName());
        User newUser = User.builder()
                .name(userNewDto.getName())
                .reporting(userNewDto.getReporting())
                .position(userNewDto.getPosition())
                .email(userNewDto.getEmail())
                .password(baseUserService.generatePassword())
                .role(userNewDto.getRole())
                .organization(user.getOrganization())
                .build();
        newUser = baseUserService.saveUser(newUser);
        return UserMapper.toDto(newUser);
    }

    @Transactional
    public UserDto patchUser(Long userId, Long id, UserUpdateDto userUpdateDto) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        User patchUser = baseUserService.getUser(user.getOrganization(), id);
        if (userUpdateDto.getName() != null) {
            Utility.checkBlank("name", userUpdateDto.getName());
            baseUserService.checkExistsUser(user.getOrganization(), userUpdateDto.getName(), id);
            patchUser.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getReporting() != null) {
            if (!userUpdateDto.getReporting() && patchUser.isReporting()) {
                baseItemService.checkExistsItemByOwner(patchUser);
            }
            patchUser.setReporting(userUpdateDto.getReporting());
        }
        if (userUpdateDto.getPosition() != null) {
            Utility.checkBlank("position", userUpdateDto.getPosition());
            patchUser.setPosition(userUpdateDto.getPosition());
        }
        if (userUpdateDto.getEmail() != null) {
            Utility.checkBlank("email", userUpdateDto.getEmail());
            baseUserService.checkExistsUser(userUpdateDto.getEmail(), id);
            patchUser.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getRole() != null) {
            if (userUpdateDto.getRole() == Role.CLIENT && patchUser.getRole() == Role.ADMIN) {
                baseUserService.checkExistsAdmin(patchUser.getOrganization(), patchUser);
            }
            patchUser.setRole(userUpdateDto.getRole());
        }
        patchUser = baseUserService.saveUser(patchUser);
        return UserMapper.toDto(patchUser);
    }

    @Transactional
    public void deleteUser(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        baseUserService.checkUserByAdmin(user);
        User deleteUser = baseUserService.getUser(user.getOrganization(), id);
        baseItemService.checkExistsItemByOwnerOrClient(deleteUser);
        baseUserService.deleteUser(deleteUser);
    }

    @Transactional
    public List<UserDto> getUsers(Long userId) {
        User user = baseUserService.getUser(userId);
        return baseUserService.getUsers(user.getOrganization()).stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto getUser(Long userId, Long id) {
        User user = baseUserService.getUser(userId);
        User foundUser = baseUserService.getUser(user.getOrganization(), id);
        return UserMapper.toDto(foundUser);
    }
}
