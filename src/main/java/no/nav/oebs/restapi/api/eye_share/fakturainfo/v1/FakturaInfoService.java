package no.nav.oebs.restapi.api.eye_share.fakturainfo.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.fakturainfo.v1.model.FakturaInfoRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import no.nav.oebs.restapi.exception.TechnicalPlsqlException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class FakturaInfoService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_fakturainfo";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public FakturaInfoService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnFakturaInfo(String json_faktura) {


		PlsqlProcedureResult result = executePlsqlProcedure(json_faktura);

		return result.getData();

	}

	private FakturaInfoRequest buildRequest(String json_faktura) {
		return FakturaInfoRequest.builder() //
				.json_faktura(json_faktura) //
				.build();
	}

	private PlsqlProcedureResult executePlsqlProcedure(String message) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, message);
	}

	public String lagreFaktura(String message) {
		try {

			PlsqlProcedureResult result = executePlsqlProcedure(message);
			if (result.getMessageNumber() < 0) {
			 	throwPlsqlException(result);
		     }

			return result.getMessage();

		} catch (Exception e) {
			String error = "Feilet under lagring av faktura i Oebs; feilmelding=" + e.getMessage();

			throw new TechnicalPlsqlException(error + e);
		}
	}
}
