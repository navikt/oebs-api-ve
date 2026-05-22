package no.nav.oebs.restapi.api.eye_share.bestillingsinfo.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;
import no.nav.oebs.restapi.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.EYESHARE, description = "Eye-Share")
public class BestillingsinfoController {

	private final BestillingsinfoService service;

	public BestillingsinfoController(BestillingsinfoService service) { //,
			this.service = service;
	}

	@Protected
	@GetMapping(path = "/bestillingsinfo")
	@EyeShareSwagger
	public String finnBestillingstransaksjoner(
			@RequestParam(name = "org_id", defaultValue = "202") Integer orgid,
			@RequestParam(name = "po_number") @Parameter(description = "f.eks. 3170085") String ponumber)
			{

		return service.finnBestillingstransaksjoner(orgid, ponumber);
	}
}
