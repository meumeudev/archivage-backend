package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.QUserEntity;
import sn.webg.archivage.service.entities.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String>, QuerydslPredicateExecutor<UserEntity> {

    Optional<UserEntity> findOneByAgentEmailIgnoreCase(String email);

    Optional<UserEntity> findOneByUsername(String username);


    default Page<UserEntity> readAllByFilters(Pageable pageable, String lastName, String firstName, String email, Boolean activated, String address) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(lastName)) {
            booleanBuilder.and(QUserEntity.userEntity.agent.lastName.containsIgnoreCase(lastName));
        }
        if (StringUtils.isNotEmpty(firstName)) {
            booleanBuilder.and(QUserEntity.userEntity.agent.firstName.containsIgnoreCase(firstName));
        }
        if (StringUtils.isNotEmpty(email)) {
            booleanBuilder.and(QUserEntity.userEntity.agent.email.containsIgnoreCase(email));
        }
        if (StringUtils.isNotEmpty(address)) {
            booleanBuilder.and(QUserEntity.userEntity.agent.address.containsIgnoreCase(address));
        }
        if (activated != null) {
            booleanBuilder.and(QUserEntity.userEntity.agent.activated.eq(activated));
        }
        return findAll(booleanBuilder, pageable);
    }



    }
