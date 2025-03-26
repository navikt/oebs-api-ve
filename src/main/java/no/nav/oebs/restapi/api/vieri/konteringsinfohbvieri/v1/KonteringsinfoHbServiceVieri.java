package no.nav.oebs.restapi.api.vieri.konteringsinfohbvieri.v1;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.api.common.model.KontRequest;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class KonteringsinfoHbServiceVieri extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_hent_kont_info_hb_vieri";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public KonteringsinfoHbServiceVieri(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}
	public String finnKonteringsinfoHbVieritransaksjoner(Integer org_id, String segmentname, String segmentverdi, LocalDate lastupdatedate ) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(org_id, segmentname, segmentverdi, lastupdatedate));
		return result.getData();

	}
	private KontRequest buildRequest(Integer org_id, String segmentname, String segmentverdi, LocalDate lastupdatedate) {
		return KontRequest.builder()
				.org_id(org_id) //
				.segmentname(segmentname) //
				.segmentverdi(segmentverdi) //
				.lastupdatedate(lastupdatedate) //
				.build();
	}
	private PlsqlProcedureResult executePlsqlProcedure(KontRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
