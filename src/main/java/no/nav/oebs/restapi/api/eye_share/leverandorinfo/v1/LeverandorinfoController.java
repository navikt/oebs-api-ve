package no.nav.oebs.restapi.api.eye_share.leverandorinfo.v1;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.Application;
import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;
import no.nav.security.token.support.core.api.Protected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import no.nav.oebs.restapi.config.SwaggerConfig;
import org.springframework.web.bind.annotation.RequestParam;
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
public class LeverandorinfoController {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private LeverandorinfoService service;

	public LeverandorinfoController(LeverandorinfoService service) { //,
			this.service = service;
	}
	@Protected
	@GetMapping(path = "/leverandorinfo")
	@EyeShareSwagger
	public String finnLeverandortransaksjoner(
			@RequestParam(name = "org_id", defaultValue = "202" ) Integer org_id,
			@RequestParam(name = "leverandornavn", required = false)
				@Parameter(description = "f.eks. ANKER HOTEL") String leverandornavn,
			@RequestParam(name = "leverandornummer", required = false)
				@Parameter(description = "f.eks. 18539") String leverandornummer,
			@RequestParam(name = "leverandorsted", required = false)
				@Parameter(description = "f.eks. 90010628775") String leverandorsted,
			@RequestParam(name = "lastupdatedate", required = false)
				@Parameter(description = "f.eks. 2022-10-25")
					@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastupdatedate)

		{

				return service.finnLeverandortransaksjoner(org_id, leverandornavn, leverandornummer, leverandorsted, lastupdatedate);
	}
}
