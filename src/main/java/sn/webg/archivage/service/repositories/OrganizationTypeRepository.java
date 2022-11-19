package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.OrganizationTypeEntity;
import sn.webg.archivage.service.entities.QOrganizationTypeEntity;

@Repository
public interface OrganizationTypeRepository extends MongoRepository<OrganizationTypeEntity, String>, QuerydslPredicateExecutor<OrganizationTypeEntity> {

    OrganizationTypeEntity findByCode(String code);

    default Page<OrganizationTypeEntity> readAllByFilters(Pageable pageable, String code, String label) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(QOrganizationTypeEntity.organizationTypeEntity.code.containsIgnoreCase(code));
        }
        if (StringUtils.isNotEmpty(label)) {
            booleanBuilder.and(QOrganizationTypeEntity.organizationTypeEntity.label.containsIgnoreCase(label));
        }
        return findAll(booleanBuilder, pageable);
    }
}
