package no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1;


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
import io.swagger.v3.oas.annotations.Parameter;


@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.EYESHARE)
public class BokfoeftStatusController {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private BokfoertStatusService service;

	public BokfoeftStatusController(BokfoertStatusService service) { //,
			this.service = service;
	}

	@Protected
	@GetMapping(path = "/bokfoertstatus")
	@EyeShareSwagger
	public String finnBokfoertStatus(
			@RequestParam(name="org_id", defaultValue = "202") Integer p_org_id,
			@RequestParam(name="p_eyeshare_dok_id")
				@Parameter(description = "f.eks. 771a8ce6-0876-4726-add1-d0185492304") String p_eyeshare_dok_id)
			{

		return service.finnBokfoertStatus(p_org_id, p_eyeshare_dok_id);
	}
}
