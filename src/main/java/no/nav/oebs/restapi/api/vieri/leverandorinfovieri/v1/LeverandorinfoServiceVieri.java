package no.nav.oebs.restapi.api.vieri.leverandorinfovieri.v1;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.api.common.model.LevRequest;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;


@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class LeverandorinfoServiceVieri extends ObjektMaps {
	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_hent_leverandorervieri";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public LeverandorinfoServiceVieri(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnLeverandortransaksjoner(Integer orgid, String leverandornavn,
											  String leverandornummer, String leverandorsted, LocalDate lastupdatedate) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(orgid, leverandornavn,
				leverandornummer, leverandorsted, lastupdatedate));
		 if (result.getMessageNumber() < 0) {
			 throwPlsqlException(result);
		}

		return result.getData();
	}

	private LevRequest buildRequest(Integer orgid, String leverandornavn,
												String leverandornummer, String leverandorsted, LocalDate lastupdatedate) {
		return LevRequest.builder()
				.orgid(orgid) //
				.leverandornavn(leverandornavn) //
				.leverandornummer(leverandornummer) //
				.leverandorsted(leverandorsted) //
				.lastupdatedate(lastupdatedate) //
				.build();
	}

	/**
	 * Kaller PL/SQL-prosedyren som utfører forretningslogikken til operasjonen.
     */
	private PlsqlProcedureResult executePlsqlProcedure(LevRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
