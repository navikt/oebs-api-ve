package no.nav.oebs.restapi.api.eye_share.konteringsinfo.v1;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;
import no.nav.security.token.support.core.api.Protected;

import java.time.LocalDate;
import no.nav.oebs.restapi.config.SwaggerConfig;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.EYESHARE)
@AllArgsConstructor
public class KonteringsinfoController {

	private final KonteringsinfoService service;

	@Protected
	@GetMapping(path = "/konteringsinfo")
	@EyeShareSwagger
	public String finnKonteringstransaksjoner(
			@RequestParam(name = "org id", defaultValue = "202") Integer orgid,
			@RequestParam(name = "segmentnavn")
				@Parameter(description = "f.eks OR_ART") String segmentname,
			@RequestParam(name = "segmentverdi", required = false)
				@Parameter(description = "f.eks. 593000000000") String segmentverdi,
			@RequestParam(name = "lastupdatedate", defaultValue = "")
				@Parameter(description = "f.eks. 2022-10-25")
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastupdatedate)

			{

		return service.finnKonteringstransaksjoner(orgid, segmentname, segmentverdi, lastupdatedate);
	}
}
