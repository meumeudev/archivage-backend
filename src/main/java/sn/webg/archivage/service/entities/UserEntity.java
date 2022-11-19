package sn.webg.archivage.service.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = UserEntity.COLLECTION_NAME)
@CompoundIndexes({
        @CompoundIndex(name = "username_index", def = "{'username': 1}", unique = true),
        @CompoundIndex(name = "email_index", def = "{'agent.email': 1}", unique = true)
})
public class UserEntity extends AbstractAuditingEntity implements Serializable {

    public static final String COLLECTION_NAME = "AR_USER";

    @Id
    String userId;

    @EqualsAndHashCode.Include
    String username;

    @ToString.Exclude
    String password;

    AgentEntity agent;

    @DBRef
    RoleEntity role;

    @Builder
    public UserEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String userId, String username, String password, AgentEntity agent, RoleEntity role) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.agent = agent;
        this.role = role;
    }
}
