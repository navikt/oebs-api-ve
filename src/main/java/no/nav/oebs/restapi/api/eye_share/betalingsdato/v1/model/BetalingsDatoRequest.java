package no.nav.oebs.restapi.api.eye_share.betalingsdato.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "org_id", "esguid" })
public class BetalingsDatoRequest {

    private Integer org_id;

    private String esguid;
}
