package no.nav.oebs.restapi.api.eye_share.bestillingsinfo.v1.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "org_id", "po_number" })
public class BestillingsRequest {

    @JsonProperty("org_id")
    private Integer orgid;

    @JsonProperty("po_number")
    private String ponumber;
}
