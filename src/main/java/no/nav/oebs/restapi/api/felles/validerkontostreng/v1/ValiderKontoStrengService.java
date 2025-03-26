package no.nav.oebs.restapi.api.felles.validerkontostreng.v1;

import lombok.extern.slf4j.Slf4j;


import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.api.felles.validerkontostreng.v1.model.ValiderKontoStrengRequest;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class ValiderKontoStrengService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_hent_kstreng";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public ValiderKontoStrengService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnValiderKontoStreng(Integer org_id,
			   String artskonto,
			   String ksted,
			   String produktoppgave,
			   String deloppgave,
			   String fellesoppgave,
			   String statskonto,
			   String kilde,
			   String tilsagnsaar,
			   String fritt_felt_1,
			   String fritt_felt_2,
			   String fullmaktskode,
			   String regnskapsforer,
			   String system) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(org_id,
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
				system));

		return result.getData();

		}

	private ValiderKontoStrengRequest buildRequest(Integer org_id,
                                                   String artskonto,
                                                   String ksted,
                                                   String produktoppgave,
                                                   String deloppgave,
                                                   String fellesoppgave,
                                                   String statskonto,
                                                   String kilde,
                                                   String tilsagnsaar,
                                                   String fritt_felt_1,
                                                   String fritt_felt_2,
                                                   String fullmaktskode,
                                                   String regnskapsforer,
                                                   String system) {
		return ValiderKontoStrengRequest.builder()
				.org_id(org_id)
				.artskonto(artskonto)
				.ksted(ksted)
				.produktoppgave(produktoppgave)
				.deloppgave(deloppgave)
			  	.fellesoppgave(fellesoppgave)
			 	.statskonto(statskonto)
				.kilde(kilde)
				.tilsagnsaar(tilsagnsaar)
				.fritt_felt_1(fritt_felt_1)
				.fritt_felt_2(fritt_felt_2)
				.fullmaktskode(fullmaktskode)
				.regnskapsforer(regnskapsforer)
				.system(system)
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(ValiderKontoStrengRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}
}
