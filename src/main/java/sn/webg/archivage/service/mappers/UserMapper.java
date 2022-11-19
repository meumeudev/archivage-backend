package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.entities.UserEntity;
import sn.webg.archivage.service.models.RoleDTO;
import sn.webg.archivage.service.models.UserDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, UserEntity>{
}
