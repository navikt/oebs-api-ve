package no.nav.oebs.restapi.api.eye_share.bilagsnummer.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.bilagsnummer.v1.model.BilagsNummerRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BilagsNummerService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_bilagsnummer";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public BilagsNummerService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBilagsNummer(Integer org_id, String esguid) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(org_id, esguid));

		return result.getData();
	}

	private BilagsNummerRequest buildRequest(Integer org_id, String esguid) {
		return BilagsNummerRequest.builder() //
				.org_id(org_id) //
				.esguid(esguid) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(BilagsNummerRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
