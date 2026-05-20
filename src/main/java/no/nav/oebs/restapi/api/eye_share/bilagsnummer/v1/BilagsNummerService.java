package no.nav.oebs.restapi.api.eye_share.bilagsnummer.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.bilagsnummer.v1.model.BilagsNummerRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BilagsNummerService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_bilagsnummer";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BilagsNummerService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBilagsNummer(Integer orgid, String esguid) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(orgid, esguid));

		return result.getData();
	}

	private BilagsNummerRequest buildRequest(Integer orgid, String esguid) {
		return BilagsNummerRequest.builder() //
				.org_id(orgid) //
				.esguid(esguid) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(BilagsNummerRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
