package no.nav.oebs.restapi.api.vieri.leverandorinfovieri.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.Application;
import no.nav.oebs.restapi.api.common.swagger.VieriSwagger;
import no.nav.security.token.support.core.api.Protected;
import io.swagger.v3.oas.annotations.Parameter;
import no.nav.oebs.restapi.config.SwaggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.VIERI, description = "Vieri API")
public class LeverandorinfoControllerVieri {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${vieri.api.loadsuppliers}")
	private String apiLoadSuppliers;

	@Value("${vieri.ocp.management}")
	private String ocpApiManagement;

	private final LeverandorinfoServiceVieri serviceLev;
	public LeverandorinfoControllerVieri(LeverandorinfoServiceVieri serviceLev) { //,
		this.serviceLev = serviceLev;
	}

	@Protected
	@PostMapping(path = "/leverandorinfo-vieri")
	@VieriSwagger
	public String finnLeverandortransaksjoner(
			@RequestParam(name="org id", defaultValue = "202") Integer org_id,
			@RequestParam(name="leverandornavn", required = false)
				@Parameter(description = "f.eks. BOUVET ASA") String leverandornavn,
			@RequestParam(name = "leverandornummer", required = false)
				@Parameter(description = "f.eks. 7048") String leverandornummer,
			@RequestParam(name = "leverandorsted", required = false)
				@Parameter(description = "f.eks. NYDALEN") String leverandorsted,
			@RequestParam(name = "lastupdatedate", defaultValue = "")
				@Parameter(description = "f.eks. 2022-10-25")
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastupdatedate) {


		String url = apiLoadSuppliers;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Ocp-Apim-Subscription-Key", ocpApiManagement);

		String lev = serviceLev.finnLeverandortransaksjoner(org_id, leverandornavn, leverandornummer, leverandorsted, lastupdatedate);

		HttpEntity<String> entity = new HttpEntity<>(lev, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			logger.info("200 OK: {}", response.getBody());
			return lev;
		} else {
			logger.info("{}", response.getStatusCode());
			return "request failet";
		}
	}
}
