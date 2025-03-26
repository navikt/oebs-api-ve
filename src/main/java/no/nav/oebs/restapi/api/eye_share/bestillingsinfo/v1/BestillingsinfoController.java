package no.nav.oebs.restapi.api.eye_share.bestillingsinfo.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.Application;
import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;
import no.nav.oebs.restapi.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
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

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private BestillingsinfoService service;
	// private LocalDate lastupdatedate;

	public BestillingsinfoController(BestillingsinfoService service) { //,
			this.service = service;
	}

	@Protected
	@GetMapping(path = "/bestillingsinfo")
	@EyeShareSwagger
	public String finnBestillingstransaksjoner(
			@RequestParam(name = "org_id", defaultValue = "202") Integer org_id,
			@RequestParam(name = "po_number") @Parameter(description = "f.eks. 3170085") String po_number)
			{

		return service.finnBestillingstransaksjoner(org_id, po_number);
	}
}
