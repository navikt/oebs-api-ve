package no.nav.oebs.restapi.db.repository;

import no.nav.oebs.restapi.exception.UgyldigInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.test.util.ReflectionTestUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
class PlsqlProcedureRepositoryTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private KallLoggRepository kallLoggRepository;

    @Mock
    private SimpleJdbcCall simpleJdbcCall;

    private PlsqlProcedureRepository repository;

    private static final String VALID_PROCEDURE = "SCHEMA.PACKAGE.PROCEDURE";

    @BeforeEach
    void setUp() {
        repository = new PlsqlProcedureRepository(dataSource, kallLoggRepository);

        // Inject mocked SimpleJdbcCall into the cache to avoid real DB calls
        ConcurrentMap<String, SimpleJdbcCall> cache = new ConcurrentHashMap<>();
        cache.put(VALID_PROCEDURE, simpleJdbcCall);
        ReflectionTestUtils.setField(repository, "jdbcCallCache", cache);
    }

    @Test
    void executeInOutProcedure_withInvalidProcedureNameFormat_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                repository.executeInOutProcedure("INVALIDNAME", "{}"));
    }

    @Test
    void executeInOutProcedure_withMissingDot_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                repository.executeInOutProcedure("PACKAGEPROCEDURE", "{}"));
    }

    @Test
    void executeInOutProcedure_withTooManyDots_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                repository.executeInOutProcedure("A.B.C.D", "{}"));
    }

    @Test
    void executeInOutProcedure_withNegativeMessageNumber_throwsUgyldigInputException() {
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("data_out", null);
        outParams.put("msg_no", new BigDecimal(-1));
        outParams.put("msg", "No data found");

        when(simpleJdbcCall.execute(any(SqlParameterSource.class))).thenReturn(outParams);

        assertThrows(UgyldigInputException.class, () ->
                repository.executeInOutProcedure(VALID_PROCEDURE, "{}"));
    }

    @Test
    void executeInOutProcedure_withValidResult_returnsPlsqlProcedureResult() {
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("data_out", null);
        outParams.put("msg_no", new BigDecimal(0));
        outParams.put("msg", "OK");

        when(simpleJdbcCall.execute(any(SqlParameterSource.class))).thenReturn(outParams);

        PlsqlProcedureResult result = repository.executeInOutProcedure(VALID_PROCEDURE, "{}");

        assertNotNull(result);
        assertEquals(0, result.getMessageNumber());
        assertEquals("OK", result.getMessage());
    }

    @Test
    void executeInOutProcedure_withPositiveMessageNumber_returnsResult() {
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("data_out", null);
        outParams.put("msg_no", new BigDecimal(1));
        outParams.put("msg", "Success");

        when(simpleJdbcCall.execute(any(SqlParameterSource.class))).thenReturn(outParams);

        PlsqlProcedureResult result = repository.executeInOutProcedure(VALID_PROCEDURE, "{}");

        assertNotNull(result);
        assertEquals(1, result.getMessageNumber());
    }


    @Test
    void cacheMiss_createsAndCachesJdbcCall() {
        ConcurrentMap<String, SimpleJdbcCall> emptyCache = new ConcurrentHashMap<>();
        ReflectionTestUtils.setField(repository, "jdbcCallCache", emptyCache);

        JdbcTemplate mockJdbcTemplate = mock(JdbcTemplate.class);
        when(mockJdbcTemplate.getDataSource()).thenReturn(dataSource);
        ReflectionTestUtils.setField(repository, "jdbcTemplate", mockJdbcTemplate);

        assertThrows(Exception.class, () ->
                repository.executeInOutProcedure("SCHEMA.PKG.PROC", "{}"));

        @SuppressWarnings("unchecked")
        ConcurrentMap<String, SimpleJdbcCall> cache =
                (ConcurrentMap<String, SimpleJdbcCall>) ReflectionTestUtils.getField(repository, "jdbcCallCache");
        assert cache != null;
        assertTrue(cache.containsKey("SCHEMA.PKG.PROC"));
    }

    @Test
    void executeInOutProcedure_cacheHit_reusesExistingJdbcCall() {
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("data_out", null);
        outParams.put("msg_no", new BigDecimal(0));
        outParams.put("msg", "OK");

        when(simpleJdbcCall.execute(any(SqlParameterSource.class))).thenReturn(outParams);

        repository.executeInOutProcedure(VALID_PROCEDURE, "{}");
        repository.executeInOutProcedure(VALID_PROCEDURE, "{}");

        // The same cached SimpleJdbcCall was used both times
        verify(simpleJdbcCall, times(2)).execute(any(SqlParameterSource.class));
    }

    @Test
    void executeInOutProcedure_withZeroMessageNumber_returnsResult() {
        Map<String, Object> outParams = new HashMap<>();
        outParams.put("data_out", null);
        outParams.put("msg_no", BigDecimal.ZERO);
        outParams.put("msg", "Boundary check");

        when(simpleJdbcCall.execute(any(SqlParameterSource.class))).thenReturn(outParams);

        PlsqlProcedureResult result = repository.executeInOutProcedure(VALID_PROCEDURE, "{}");

        assertEquals(0, result.getMessageNumber());
    }

}
