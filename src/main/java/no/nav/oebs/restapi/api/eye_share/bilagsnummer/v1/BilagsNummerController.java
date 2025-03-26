package no.nav.oebs.restapi.api.eye_share.bilagsnummer.v1;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.Application;
import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;
import no.nav.oebs.restapi.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.EYESHARE)
public class BilagsNummerController {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private BilagsNummerService service;

	public BilagsNummerController(BilagsNummerService service) { //,
			this.service = service;
	}
	@Protected
	@GetMapping(path = "/bilagsnummer")
	@EyeShareSwagger
	public String finnBilagsNummer(
			@RequestParam(name = "org_id", defaultValue = "202") Integer org_id,
			@RequestParam(name = "esguid") @Parameter(description = "f.eks. 5bc11c52-7934-406f-9e94-7df882217HM") String esguid)
			{

		return service.finnBilagsNummer(org_id, esguid);
	}
}
