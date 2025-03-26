package no.nav.oebs.restapi.api.eye_share.leverandorinfo.v1;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;


import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.api.common.model.LevRequest;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;


@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class LeverandorinfoService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_hent_leverandorer";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public LeverandorinfoService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnLeverandortransaksjoner(Integer org_id, String leverandornavn,
											  String leverandornummer, String leverandorsted, LocalDate lastupdatedate) {


		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(org_id, leverandornavn,
				leverandornummer, leverandorsted, lastupdatedate));

		return result.getData();

	}

	private LevRequest buildRequest(Integer org_id, String leverandornavn,
									String leverandornummer, String leverandorsted, LocalDate lastupdatedate) {
		return LevRequest.builder() //
				.org_id(org_id) //
				.leverandornavn(leverandornavn) //
				.leverandornummer(leverandornummer) //
				.leverandorsted(leverandorsted) //
				.lastupdatedate(lastupdatedate) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(LevRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
