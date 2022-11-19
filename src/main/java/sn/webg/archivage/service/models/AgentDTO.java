package sn.webg.archivage.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Schema(description = "Agent representation")
public class AgentDTO {

    @EqualsAndHashCode.Include
    @NotEmpty
    String lastName;

    @EqualsAndHashCode.Include
    @NotEmpty
    String firstName;

    @EqualsAndHashCode.Include
    @NotEmpty
    String email;

    String roleName;

    String phone;

    String function;

    String address;

    boolean activated = false;

    String organizationId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    OrganizationDTO organization;
}
