package no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1.model.BokfoertStatusRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BokfoertStatusService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_bokfoertstatus";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public BokfoertStatusService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBokfoertStatus(Integer p_org_id, String p_eyeshare_dok_id) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(p_org_id, p_eyeshare_dok_id));

		return result.getData();

	}

	private BokfoertStatusRequest buildRequest(Integer p_org_id, String p_eyeshare_dok_id) {
		return BokfoertStatusRequest.builder() //
				.p_org_id(p_org_id) //
				.p_eyeshare_dok_id(p_eyeshare_dok_id) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(BokfoertStatusRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
