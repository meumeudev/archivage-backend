package sn.webg.archivage.service.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Schema(description = "AuthUsername request authentication")
public class AuthUsername {

    @Schema(description = "Username or email user", required = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @Schema(description = "User password")
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    private boolean rememberMe;
}
