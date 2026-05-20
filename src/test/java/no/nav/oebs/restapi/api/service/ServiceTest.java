package no.nav.oebs.restapi.api.service;

import no.nav.oebs.restapi.api.eye_share.bestillingsinfo.v1.BestillingsinfoService;
import no.nav.oebs.restapi.api.eye_share.betalingsdato.v1.BetalingsDatoService;
import no.nav.oebs.restapi.api.eye_share.bilagsnummer.v1.BilagsNummerService;
import no.nav.oebs.restapi.api.eye_share.bokfoertstatus.v1.BokfoertStatusService;
import no.nav.oebs.restapi.api.eye_share.fakturainfo.v1.FakturaInfoService;
import no.nav.oebs.restapi.api.eye_share.konteringsinfo.v1.KonteringsinfoService;
import no.nav.oebs.restapi.api.eye_share.leverandorinfo.v1.LeverandorinfoService;
import no.nav.oebs.restapi.api.felles.validerkontostreng.v1.ValiderKontoStrengService;
import no.nav.oebs.restapi.api.vieri.konteringsinfohbvieri.v1.KonteringsinfoHbServiceVieri;
import no.nav.oebs.restapi.api.vieri.konteringsinfovieri.v1.KonteringsinfoServiceVieri;
import no.nav.oebs.restapi.api.vieri.leverandorinfovieri.v1.LeverandorinfoServiceVieri;
import no.nav.oebs.restapi.exception.UgyldigInputException;
import no.nav.oebs.restapi.db.repository.PlsqlMessageCodes;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureRepository;
import no.nav.oebs.restapi.db.repository.PlsqlProcedureResult;
import no.nav.oebs.restapi.exception.TechnicalPlsqlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private PlsqlProcedureRepository plsqlProcedureRepository;

    private final JsonMapper jsonMapper = new JsonMapper();

    private PlsqlProcedureResult resultWithData(String data) {
        return new PlsqlProcedureResult(data, PlsqlMessageCodes.OK, "OK");
    }

    private PlsqlProcedureResult resultWithError(int messageNumber, String message) {
        return new PlsqlProcedureResult((String) null, messageNumber, message);
    }

    // -------------------------------------------------------------------------

    @Nested
    class BestillingsinfoServiceTests {

        private BestillingsinfoService service;

        @BeforeEach
        void setUp() {
            service = new BestillingsinfoService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnBestillingstransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"po_number\":\"3170085\"}]"));

            String result = service.finnBestillingstransaksjoner(202, "3170085");

            assertEquals("[{\"po_number\":\"3170085\"}]", result);
        }

        @Test
        void finnBestillingstransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnBestillingstransaksjoner(202, "3170085");

            assertNull(result);
        }

        @Test
        void finnBestillingstransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnBestillingstransaksjoner(202, "3170085");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_bestillingsinfo"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class BetalingsDatoServiceTests {

        private BetalingsDatoService service;

        @BeforeEach
        void setUp() {
            service = new BetalingsDatoService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnBetalingsDato_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("2024-01-15"));

            String result = service.finnBetalingsDato(202, "5bc11c52-7934-406f");

            assertEquals("2024-01-15", result);
        }

        @Test
        void finnBetalingsDato_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnBetalingsDato(202, "some-guid");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_betalingsdato"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class BilagsNummerServiceTests {

        private BilagsNummerService service;

        @BeforeEach
        void setUp() {
            service = new BilagsNummerService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnBilagsNummer_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("12345"));

            String result = service.finnBilagsNummer(202, "some-guid");

            assertEquals("12345", result);
        }

        @Test
        void finnBilagsNummer_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnBilagsNummer(202, "some-guid");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_bilagsnummer"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class BokfoertStatusServiceTests {

        private BokfoertStatusService service;

        @BeforeEach
        void setUp() {
            service = new BokfoertStatusService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnBokfoertStatus_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("POSTED"));

            String result = service.finnBokfoertStatus(202, "doc-id-123");

            assertEquals("POSTED", result);
        }

        @Test
        void finnBokfoertStatus_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnBokfoertStatus(202, "doc-id-123");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_bokfoertstatus"), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class FakturaInfoServiceTests {

        private FakturaInfoService service;

        @BeforeEach
        void setUp() {
            service = new FakturaInfoService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnFakturaInfo_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("{\"faktura\":\"data\"}"));

            String result = service.finnFakturaInfo("{\"json\":\"input\"}");

            assertEquals("{\"faktura\":\"data\"}", result);
        }

        @Test
        void finnFakturaInfo_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnFakturaInfo("{}");

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_fakturainfo"), any());
        }

        @Test
        void lagreFaktura_withSuccessResult_returnsMessage() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(new PlsqlProcedureResult((String) null, PlsqlMessageCodes.OK, "Lagret OK"));

            String result = service.lagreFaktura("{\"faktura\":\"data\"}");

            assertEquals("Lagret OK", result);
        }

        @Test
        void lagreFaktura_withNegativeMessageNumber_throwsTechnicalPlsqlException() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithError(PlsqlMessageCodes.EXCEPTION, "DB error"));

            assertThrows(TechnicalPlsqlException.class, () ->
                    service.lagreFaktura("{\"faktura\":\"data\"}"));
        }

        @Test
        void lagreFaktura_whenRepositoryThrows_throwsTechnicalPlsqlException() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenThrow(new RuntimeException("Connection failed"));

            assertThrows(TechnicalPlsqlException.class, () ->
                    service.lagreFaktura("{}"));
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class KonteringsinfoServiceTests {

        private KonteringsinfoService service;

        @BeforeEach
        void setUp() {
            service = new KonteringsinfoService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnKonteringstransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"segment\":\"data\"}]"));

            String result = service.finnKonteringstransaksjoner(202, "KSTED", "1234", LocalDate.of(2024, 1, 1));

            assertEquals("[{\"segment\":\"data\"}]", result);
        }

        @Test
        void finnKonteringstransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnKonteringstransaksjoner(202, "KSTED", "1234", LocalDate.now());

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_hent_segmenter"), any());
        }

        @Test
        void finnKonteringstransaksjoner_withNullOptionalParams_returnsData() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            String result = service.finnKonteringstransaksjoner(202, null, null, null);

            assertEquals("[]", result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class ValiderKontoStrengServiceTests {

        private ValiderKontoStrengService service;

        @BeforeEach
        void setUp() {
            service = new ValiderKontoStrengService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnValiderKontoStreng_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("{\"gyldig\":true}"));

            String result = service.finnValiderKontoStreng(
                    202, "4900", "1234", null, null, null, null, null, null, null, null, null, null, null);

            assertEquals("{\"gyldig\":true}", result);
        }

        @Test
        void finnValiderKontoStreng_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnValiderKontoStreng(
                    202, "4900", "1234", null, null, null, null, null, null, null, null, null, null, null);

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_hent_kstreng"), any());
        }

        @Test
        void finnValiderKontoStreng_withAllParams_callsRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnValiderKontoStreng(
                    202, "4900", "1234", "PROD", "DEL", "FELLES",
                    "STATS", "KILDE", "2024", "FF1", "FF2", "FULL", "REG", "SYS");

            verify(plsqlProcedureRepository, times(1)).executeInOutProcedure(any(), any());
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class LeverandorinfoServiceTests {

        private LeverandorinfoService service;

        @BeforeEach
        void setUp() {
            service = new LeverandorinfoService(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnLeverandortransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"leverandor\":\"Navn AS\"}]"));

            String result = service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.of(2024, 1, 1));

            assertEquals("[{\"leverandor\":\"Navn AS\"}]", result);
        }

        @Test
        void finnLeverandortransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.now());

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_hent_leverandorer"), any());
        }

        @Test
        void finnLeverandortransaksjoner_withNullOptionalParams_returnsData() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            String result = service.finnLeverandortransaksjoner(202, null, null, null, null);

            assertEquals("[]", result);
        }

        @Test
        void finnLeverandortransaksjoner_withNullData_returnsNull() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData(null));

            String result = service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.now());

            assertNull(result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class KonteringsinfoHbServiceVieriTests {

        private KonteringsinfoHbServiceVieri service;

        @BeforeEach
        void setUp() {
            service = new KonteringsinfoHbServiceVieri(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnKonteringsinfoHbVieritransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"segment\":\"hb\"}]"));

            String result = service.finnKonteringsinfoHbVieritransaksjoner(202, "KSTED", "1234", LocalDate.of(2024, 1, 1));

            assertEquals("[{\"segment\":\"hb\"}]", result);
        }

        @Test
        void finnKonteringsinfoHbVieritransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnKonteringsinfoHbVieritransaksjoner(202, "KSTED", "1234", LocalDate.now());

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_hent_kont_info_hb_vieri"), any());
        }

        @Test
        void finnKonteringsinfoHbVieritransaksjoner_withNullOptionalParams_returnsData() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            String result = service.finnKonteringsinfoHbVieritransaksjoner(202, null, null, null);

            assertEquals("[]", result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class KonteringsinfoServiceVieriTests {

        private KonteringsinfoServiceVieri service;

        @BeforeEach
        void setUp() {
            service = new KonteringsinfoServiceVieri(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnKonteringsinfoVieritransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"segment\":\"vieri\"}]"));

            String result = service.finnKonteringsinfoVieritransaksjoner(202, "KSTED", "1234", LocalDate.of(2024, 1, 1));

            assertEquals("[{\"segment\":\"vieri\"}]", result);
        }

        @Test
        void finnKonteringsinfoVieritransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnKonteringsinfoVieritransaksjoner(202, "KSTED", "1234", LocalDate.now());

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_hent_kont_info_vieri"), any());
        }

        @Test
        void finnKonteringsinfoVieritransaksjoner_withNullOptionalParams_returnsData() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            String result = service.finnKonteringsinfoVieritransaksjoner(202, null, null, null);

            assertEquals("[]", result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class LeverandorinfoServiceVieriTests {

        private LeverandorinfoServiceVieri service;

        @BeforeEach
        void setUp() {
            service = new LeverandorinfoServiceVieri(plsqlProcedureRepository, jsonMapper);
        }

        @Test
        void finnLeverandortransaksjoner_returnsDataFromRepository() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[{\"leverandor\":\"navn\"}]"));

            String result = service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.of(2024, 1, 1));

            assertEquals("[{\"leverandor\":\"navn\"}]", result);
        }

        @Test
        void finnLeverandortransaksjoner_callsCorrectProcedure() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.now());

            verify(plsqlProcedureRepository).executeInOutProcedure(
                    eq("xxrtv_restapi_oebs_ve_v1.xxrtv_hent_leverandorervieri"), any());
        }

        @Test
        void finnLeverandortransaksjoner_withNullOptionalParams_returnsData() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithData("[]"));

            String result = service.finnLeverandortransaksjoner(202, null, null, null, null);

            assertEquals("[]", result);
        }

        @Test
        void finnLeverandortransaksjoner_withNegativeMessageNumber_throwsPlsqlException() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithError(PlsqlMessageCodes.EXCEPTION, "DB error"));

            assertThrows(TechnicalPlsqlException.class, () ->
                    service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.now()));
        }

        @Test
        void finnLeverandortransaksjoner_withFeilIInput_throwsUgyldigInputException() {
            when(plsqlProcedureRepository.executeInOutProcedure(any(), any()))
                    .thenReturn(resultWithError(PlsqlMessageCodes.FEIL_I_INPUT, "Invalid input"));

            assertThrows(UgyldigInputException.class, () ->
                    service.finnLeverandortransaksjoner(202, "Navn AS", "12345", "Oslo", LocalDate.now()));
        }
    }
}
