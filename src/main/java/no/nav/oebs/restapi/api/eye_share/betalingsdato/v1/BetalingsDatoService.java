package no.nav.oebs.restapi.api.eye_share.betalingsdato.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.betalingsdato.v1.model.BetalingsDatoRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BetalingsDatoService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "apps.xxrtv_restapi_oebs_ve_v1.xxrtv_betalingsdato";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BetalingsDatoService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBetalingsDato(Integer orgid, String esguid) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(orgid, esguid));
		return result.getData();
	}

	private BetalingsDatoRequest buildRequest(Integer orgid, String esguid) {
		return BetalingsDatoRequest.builder() //
				.orgid(orgid) //
				.esguid(esguid) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(BetalingsDatoRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
