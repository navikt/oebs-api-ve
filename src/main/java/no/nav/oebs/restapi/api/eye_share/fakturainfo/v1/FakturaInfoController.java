package no.nav.oebs.restapi.api.eye_share.fakturainfo.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.common.swagger.EyeShareSwagger;
import no.nav.oebs.restapi.config.SwaggerConfig;
import no.nav.security.token.support.core.api.Protected;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@Slf4j
@RestController
@Validated
@RequestMapping(path = "/api/v1")
@Tag(name = SwaggerConfig.EYESHARE)
@AllArgsConstructor
public class FakturaInfoController {

	private final FakturaInfoService service;

	@Protected
	@EyeShareSwagger
	@PostMapping(path = "/fakturainfo", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> lagreFaktura( @RequestParam(defaultValue = "202") String orgid,
										@Valid @RequestBody String message) {

		return ResponseEntity
				.ok()
				.body(service.lagreFaktura(message)
		);
	}
}