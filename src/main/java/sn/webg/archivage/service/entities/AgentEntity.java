package sn.webg.archivage.service.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgentEntity {

    @EqualsAndHashCode.Include
    String lastName;

    @EqualsAndHashCode.Include
    String firstName;

    @EqualsAndHashCode.Include
    String email;

    String phone;

    String function;

    String address;

    boolean activated = true;

    @DBRef
    OrganizationEntity organization;
}
