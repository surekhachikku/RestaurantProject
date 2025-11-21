package com.restaurant.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.restaurant.userservice.dto.UsersDTO;
import com.restaurant.userservice.entity.Users;

@Mapper
public interface UsersMapper {

	UsersMapper Instance = Mappers.getMapper(UsersMapper.class);
	
	Users mapUsersDTOToUsers(UsersDTO userDto);
	
	UsersDTO mapUserToUsersDTO(Users user);
}
