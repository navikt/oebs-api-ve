package no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1.model.BokfoertStatusRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BokfoertStatusService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "apps.xxrtv_restapi_oebs_ve_v1.xxrtv_bokfoertstatus";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public BokfoertStatusService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBokfoertStatus(Integer pOrgId, String pEyeshareDokId) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(pOrgId, pEyeshareDokId));

		return result.getData();

	}

	private BokfoertStatusRequest buildRequest(Integer pOrgId, String pEyeshareDokId) {
		return BokfoertStatusRequest.builder() //
				.pOrgid(pOrgId) //
				.pEyeshareDokid(pEyeshareDokId) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(BokfoertStatusRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
