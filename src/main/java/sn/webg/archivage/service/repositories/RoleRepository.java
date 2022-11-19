package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.QRoleEntity;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.models.SecurityPermissions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, String>, QuerydslPredicateExecutor<RoleEntity> {

    Optional<RoleEntity> findByName(String name);

    default Page<RoleEntity> readAllByFilters(Pageable pageable, String name, List<SecurityPermissions> securityPermissions) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(name)) {
            booleanBuilder.and(QRoleEntity.roleEntity.name.eq(name));
        }
        if (Objects.nonNull(securityPermissions) && !securityPermissions.isEmpty()) {
            booleanBuilder.and(QRoleEntity.roleEntity.permissions.any().in(securityPermissions));
        }
        return findAll(booleanBuilder, pageable);


    }

}
