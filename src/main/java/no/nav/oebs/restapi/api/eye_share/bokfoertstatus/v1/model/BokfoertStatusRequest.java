package no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1.model;

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
@JsonPropertyOrder({ "p_org_id", "p_eyeshare_dok_id" })
public class BokfoertStatusRequest {

    private Integer p_org_id;

    private String p_eyeshare_dok_id;
}
