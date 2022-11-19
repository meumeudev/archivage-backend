package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.OrganizationEntity;
import sn.webg.archivage.service.entities.QOrganizationEntity;


@Repository
public interface OrganizationRepository extends MongoRepository<OrganizationEntity, String>, QuerydslPredicateExecutor<OrganizationEntity> {

    default Page<OrganizationEntity> readAllByFilters(Pageable pageable, String name, String phone, String responsible, String organizationTypeId) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(name)) {
            booleanBuilder.and(QOrganizationEntity.organizationEntity.name.containsIgnoreCase(name));
        }
        if (StringUtils.isNotEmpty(phone)) {
            booleanBuilder.and(QOrganizationEntity.organizationEntity.phone.containsIgnoreCase(phone));
        }
        if (StringUtils.isNotEmpty(responsible)) {
            booleanBuilder.and(QOrganizationEntity.organizationEntity.responsible.containsIgnoreCase(responsible));
        }
        if (StringUtils.isNotEmpty(organizationTypeId)) {
            booleanBuilder.and(QOrganizationEntity.organizationEntity.organizationType.id.eq(organizationTypeId));
        }
        return findAll(booleanBuilder, pageable);
    }
}
