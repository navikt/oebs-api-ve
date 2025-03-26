package no.nav.oebs.restapi.api.felles.validerkontostreng.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.Application;
import no.nav.oebs.restapi.api.common.swagger.FellesSwagger;
import no.nav.security.token.support.core.api.Protected;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import no.nav.oebs.restapi.config.SwaggerConfig;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;

/*
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
*/

import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;

import javax.validation.constraints.NotEmpty;

@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Tag(name = SwaggerConfig.FELLES, description = "Felles API")
public class ValiderKontoStrengController {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	private ValiderKontoStrengService service;
	private String produktoppgave;

	public ValiderKontoStrengController(ValiderKontoStrengService service) { //,
			this.service = service;
	}

	@Protected
	@GetMapping(path = "/validerkontostreng")
	@FellesSwagger
	public String finnValiderKontoStreng(
			@RequestParam(name = "org_id", defaultValue = "202") Integer org_id, //
			@RequestParam(name = "artskonto") @NotEmpty @Parameter(description = "eks. 669200000000") String artskonto,
			@RequestParam(name = "ksted")  @NotEmpty @Parameter(description = "f.eks. 243299") String ksted,
			@RequestParam(name = "produkt") @NotEmpty @Parameter(description = "f.eks. 000000") String produktoppgave,
			@RequestParam(name = "oppgave") @NotEmpty @Parameter(description = "f.eks. 000000") String deloppgave,
			@RequestParam(name = "felles") @NotEmpty @Parameter(description = "f.eks. L01002") String fellesoppgave,
			@RequestParam(name = "statskonto") @NotEmpty @Parameter(description = "f.eks. 266175000000") String statskonto,
			@RequestParam(name = "kilde") @NotEmpty @Parameter(description = "f.eks. 000000") String kilde,
			@RequestParam(name = "tilsagnsaar") @NotEmpty @Parameter(description = "f.eks. 000000") String tilsagnsaar,
			@RequestParam(name = "fritt_felt_1") @NotEmpty @Parameter(description = "f.eks. 000000") String fritt_felt_1,
			@RequestParam(name = "fritt_felt_2") @NotEmpty @Parameter(description = "f.eks. 000000") String fritt_felt_2,
			@RequestParam(name = "fullmaktskode") @NotEmpty @Parameter(description = "f.eks. Z1") String fullmaktskode,
			@RequestParam(name = "regnskapsforer") @NotEmpty @Parameter(description = "f.eks. 80") String regnskapsforer,
			@RequestParam(name = "system") @NotEmpty @Parameter(description = "f.eks. EYE-SHARE eller VIERI") String system)

			{

		return service.finnValiderKontoStreng(org_id,
				artskonto,
				ksted,
				produktoppgave,
				deloppgave,
				fellesoppgave,
				statskonto,
				kilde,
				tilsagnsaar,
				fritt_felt_1,
				fritt_felt_2,
				fullmaktskode,
				regnskapsforer,
			    system);
	}
}
