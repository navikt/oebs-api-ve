package no.nav.oebs.restapi.api.eye_share.bestillingsinfo.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import no.nav.oebs.restapi.api.eye_share.bestillingsinfo.v1.model.BestillingsRequest;
import no.nav.oebs.restapi.api.common.utils.ObjektMaps;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional(noRollbackFor = { Exception.class })
public class BestillingsinfoService extends ObjektMaps {

	private static final String PLSQL_PROCEDURE = "xxrtv_restapi_oebs_ve_v1.xxrtv_bestillingsinfo";

	private PlsqlProcedureRepository plsqlProcedureRepository;

	public BestillingsinfoService(PlsqlProcedureRepository plsqlProcedureRepository, ObjectMapper objectMapper) {
		super(objectMapper);
		this.plsqlProcedureRepository = plsqlProcedureRepository;
	}

	public String finnBestillingstransaksjoner(Integer org_id, String po_number) {


	//public List<Leverandortransaksjon> finnKonteringstransaksjoner(String companycode, String segmentname,
	//															LocalDate lastupdatedate) {

		PlsqlProcedureResult result = executePlsqlProcedure(buildRequest(org_id, po_number));
		/* if (result.getMessageNumber() < 0) {
			 throwPlsqlException(result);
		/} */

		return result.getData();

		// return getApiResponse(result.getData());
	}

	/**
	 * Bygger et requestobjekt som skal konverteres til JSON.
	 */
	private BestillingsRequest buildRequest(Integer org_id, String po_number) {
		return BestillingsRequest.builder() //
				.org_id(org_id) //
				.po_number(po_number) //
				.build();
	}

	/**
	 * Kaller PL/SQL-prosedyren som utfører forretningslogikken til operasjonen.
	 * @param request
	 */
	private PlsqlProcedureResult executePlsqlProcedure(BestillingsRequest request) {

		return plsqlProcedureRepository.executeInOutProcedure(PLSQL_PROCEDURE, toJson(request));
	}

	/**
	 * Konverterer en respons-JSON til et responsobjekt som API'et skal returnere.
	 */
	/*private List<Leverandortransaksjon> getApiResponse(String json) {
		if (json == null) {
			throw new TechnicalPlsqlException("Uventet null-verdi istedenfor JSON-objekt fra " + PLSQL_PROCEDURE);
		}

		return toObject(json, new TypeReference<List<Leverandortransaksjon>>() {
		});
	}*/
}
