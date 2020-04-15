package ru.sgu.springbank.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.sgu.springbank.dto.AccountDto;
import ru.sgu.springbank.dto.TransferDto;
import ru.sgu.springbank.dto.UserDto;
import ru.sgu.springbank.entity.Account;
import ru.sgu.springbank.entity.Transfer;
import ru.sgu.springbank.entity.User;

import java.util.List;
import java.util.UUID;

@Mapper(imports = UUID.class)
public interface MapperConfig {

    MapperConfig INSTANCE = Mappers.getMapper(MapperConfig.class);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    @Mapping(target = "id", expression = "java(UUID.randomUUID().toString())")
    AccountDto toDto(Account account);

    @Mapping(target = "id", expression = "java(accountDto.getId() == null ? null : UUID.fromString(accountDto.getId()))")
    Account toEntity(AccountDto accountDto);

    @Mapping(target = "date", dateFormat = "dd.MM.yyy HH:mm:ss")
    TransferDto toDto(Transfer transfer);

    @Mapping(target = "date", dateFormat = "dd.MM.yyy HH:mm:ss")
    Transfer toEntity(TransferDto transferDto);

    List<TransferDto> toDtoList(List<Transfer> transfers);

    List<Transfer> toEntityList(List<TransferDto> transfers);

}
