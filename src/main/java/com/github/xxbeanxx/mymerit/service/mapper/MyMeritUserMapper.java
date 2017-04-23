package com.github.xxbeanxx.mymerit.service.mapper;

import com.github.xxbeanxx.mymerit.domain.*;
import com.github.xxbeanxx.mymerit.service.dto.MyMeritUserDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity MyMeritUser and its DTO MyMeritUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface MyMeritUserMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    MyMeritUserDTO myMeritUserToMyMeritUserDTO(MyMeritUser myMeritUser);

    List<MyMeritUserDTO> myMeritUsersToMyMeritUserDTOs(List<MyMeritUser> myMeritUsers);

    @Mapping(source = "userId", target = "user")
    MyMeritUser myMeritUserDTOToMyMeritUser(MyMeritUserDTO myMeritUserDTO);

    List<MyMeritUser> myMeritUserDTOsToMyMeritUsers(List<MyMeritUserDTO> myMeritUserDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default MyMeritUser myMeritUserFromId(Long id) {
        if (id == null) {
            return null;
        }
        MyMeritUser myMeritUser = new MyMeritUser();
        myMeritUser.setId(id);
        return myMeritUser;
    }
    

}
