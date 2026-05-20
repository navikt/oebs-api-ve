package no.nav.oebs.restapi.api.eye_share.fakturainfo.v1;

import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import no.nav.oebs.restapi.exception.TechnicalPlsqlException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class FakturaInfoService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_fakturainfo";

	private final PlsqlProcedureRepository plsqlProcedureRepository;

	public FakturaInfoService(PlsqlProcedureRepository plsqlProcedureRepository, JsonMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnFakturaInfo(String jsonFaktura) {


		PlsqlProcedureResult result = executePlsqlProcedure(jsonFaktura);

		return result.getData();

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
