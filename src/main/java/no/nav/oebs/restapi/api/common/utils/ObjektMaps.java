package no.nav.oebs.restapi.api.common.utils;


import no.nav.oebs.restapi.db.repository.PlsqlMessageCodes;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import no.nav.oebs.restapi.exception.JsonMappingException;
import no.nav.oebs.restapi.exception.TechnicalPlsqlException;
import no.nav.oebs.restapi.exception.UgyldigInputException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

/**
 * Superklasse med felles funksjonalitet for implementasjon av tjenestespesifikke Service-klasser.
 */
public class ObjektMaps {

	private JsonMapper jsonMapper;

	protected ObjektMaps() {
	}

	protected ObjektMaps(JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	/**
	 * Kaster exception iht. feilkoden returnert fra PL/SQL-prosedyren.
	 */
	protected void throwPlsqlException(PlsqlProcedureResult result) {
		if (result.getMessageNumber().equals(PlsqlMessageCodes.FEIL_I_INPUT)) {
			throw new UgyldigInputException(result.getMessage());
		} else {
			throw new TechnicalPlsqlException(result.getMessageNumber(), result.getMessage());
		}
	}

	/**
	 * Mapper fra Java- til JSON-objekt.
	 */
	protected <T> String toJson(T object) {
		try {
			return jsonMapper.writeValueAsString(object);
		} catch (JacksonException e) {
			throw new JsonMappingException(e);
		}
	}

	/**
	 * Mapper fra JSON- til Java-objekt.
	 */
	protected <T> T toObject(String json, Class<T> valueType) {
		try {
			return jsonMapper.readValue(json, valueType);
		} catch (JacksonException e) {
			throw new JsonMappingException(e);
		}
	}

	/**
	 * Mapper fra JSON- til Java-objekt der generisk typeinformasjon må brukes under mappingen. Dette gjelder typisk for List-
	 * og Map-objekter.
	 */
	protected <T> T toObject(String json, TypeReference<T> objectTypeRef) {
		try {
			return jsonMapper.readValue(json, objectTypeRef);
		} catch (JacksonException e) {
			throw new JsonMappingException(e);
		}
	}
}
