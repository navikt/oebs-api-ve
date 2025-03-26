package no.nav.oebs.restapi.api.vieri.konteringsinfovieri.v1;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.nav.oebs.restapi.Application;
import no.nav.oebs.restapi.api.common.swagger.VieriSwagger;
import no.nav.security.token.support.core.api.Protected;

import no.nav.oebs.restapi.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Unprotected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.VIERI, description = "Vieri API")
public class KonteringsrinfoControllerVieri {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${vieri.api.loaddimensions}")
	private String apiLoadDimensions;

	@Value("${vieri.ocp.management}")
	private String ocpApiManagement;
	private final KonteringsinfoServiceVieri serviceKont;

	public KonteringsrinfoControllerVieri(KonteringsinfoServiceVieri serviceKont) { //,
		this.serviceKont = serviceKont;
	}

	@Protected
	@PostMapping(path = "/konteringsinfovieri")
	@VieriSwagger
	public String konteringsinfovieritransaksjoner(
			@RequestParam(name="org id", defaultValue = "202") Integer org_id,
			@RequestParam(name="segmentnavn", required = false)
				@Parameter(description = "f.eks. OR_AKTIVITET") String segmentname,
			@RequestParam(name="segmentverdi", required = false)
				@Parameter(description = "f.eks. B00001") String segmentverdi,
			@RequestParam(name = "lastupdatedate", defaultValue = "")
				@Parameter(description = "f.eks. 2022-12-25")
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastupdatedate) {


		String url = apiLoadDimensions;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Ocp-Apim-Subscription-Key", ocpApiManagement);

		String kont = serviceKont.finnKonteringsinfoVieritransaksjoner(org_id, segmentname, segmentverdi, lastupdatedate);

		HttpEntity<String> entity = new HttpEntity<>(kont, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			logger.info("200 OK: {}", response.getBody());
			return kont;
		} else {
			logger.info("{}", response.getStatusCode());
			return "request failet";
		}
	}
}