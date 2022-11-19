package sn.webg.archivage.service.models.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Schema(description = "SigninAuthentication representation")
public class SignInAuthentication {

    @JsonProperty("access_token")
    @JsonAlias("access_token")
    String accessToken;

    @JsonProperty("token_type")
    @JsonAlias("token_type")
    String tokenType;

}
