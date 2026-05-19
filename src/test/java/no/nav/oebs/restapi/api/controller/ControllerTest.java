package no.nav.oebs.restapi.api.controller;

import com.sun.net.httpserver.HttpServer;
import no.nav.oebs.restapi.api.vieri.konteringsinfohbvieri.v1.KonteringsrinfoHbControllerVieri;
import no.nav.oebs.restapi.api.vieri.konteringsinfohbvieri.v1.KonteringsinfoHbServiceVieri;
import no.nav.oebs.restapi.api.vieri.konteringsinfovieri.v1.KonteringsrinfoControllerVieri;
import no.nav.oebs.restapi.api.vieri.konteringsinfovieri.v1.KonteringsinfoServiceVieri;
import no.nav.oebs.restapi.api.vieri.leverandorinfovieri.v1.LeverandorinfoControllerVieri;
import no.nav.oebs.restapi.api.vieri.leverandorinfovieri.v1.LeverandorinfoServiceVieri;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    private HttpServer server;
    private int port;

    private void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        port = server.getAddress().getPort();
        server.createContext("/", exchange -> {
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.write("ok".getBytes());
            os.close();
        });
        server.start();
    }

    @AfterEach
    void stopServer() {
        if (server != null) server.stop(0);
    }

    // -------------------------------------------------------------------------

    @Nested
    class KonteringsrinfoControllerVieriTests {

        @Mock
        private KonteringsinfoServiceVieri service;

        private KonteringsrinfoControllerVieri controller;

        @BeforeEach
        void setUp() throws IOException {
            startServer();
            controller = new KonteringsrinfoControllerVieri(service);
            ReflectionTestUtils.setField(controller, "apiLoadDimensions", "http://localhost:" + port);
            ReflectionTestUtils.setField(controller, "ocpApiManagement", "dummy-key");
        }

        @Test
        void konteringsinfovieritransaksjoner_returns200_returnsServiceData() {
            when(service.finnKonteringsinfoVieritransaksjoner(any(), any(), any(), any()))
                    .thenReturn("[{\"segment\":\"data\"}]");

            String result = controller.konteringsinfovieritransaksjoner(202, "KSTED", "1234", LocalDate.now());

            assertEquals("[{\"segment\":\"data\"}]", result);
            verify(service).finnKonteringsinfoVieritransaksjoner(202, "KSTED", "1234", LocalDate.now());
        }

        @Test
        void konteringsinfovieritransaksjoner_withNullParams_returnsServiceData() {
            when(service.finnKonteringsinfoVieritransaksjoner(any(), any(), any(), any()))
                    .thenReturn("[]");

            String result = controller.konteringsinfovieritransaksjoner(202, null, null, null);

            assertEquals("[]", result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class KonteringsrinfoHbControllerVieriTests {

        @Mock
        private KonteringsinfoHbServiceVieri service;

        private KonteringsrinfoHbControllerVieri controller;

        @BeforeEach
        void setUp() throws IOException {
            startServer();
            controller = new KonteringsrinfoHbControllerVieri(service);
            ReflectionTestUtils.setField(controller, "apiLoadAccounts", "http://localhost:" + port);
            ReflectionTestUtils.setField(controller, "ocpApiManagement", "dummy-key");
        }

        @Test
        void hentkonteringsinfoHb_returns200_returnsServiceData() {
            when(service.finnKonteringsinfoHbVieritransaksjoner(any(), any(), any(), any()))
                    .thenReturn("[{\"segment\":\"hb\"}]");

            String result = controller.hentkonteringsinfoHb(202, "OR_ART", "12345", LocalDate.now());

            assertEquals("[{\"segment\":\"hb\"}]", result);
            verify(service).finnKonteringsinfoHbVieritransaksjoner(202, "OR_ART", "12345", LocalDate.now());
        }

        @Test
        void hentkonteringsinfoHb_withNullParams_returnsServiceData() {
            when(service.finnKonteringsinfoHbVieritransaksjoner(any(), any(), any(), any()))
                    .thenReturn("[]");

            String result = controller.hentkonteringsinfoHb(202, null, null, null);

            assertEquals("[]", result);
        }
    }

    // -------------------------------------------------------------------------

    @Nested
    class LeverandorinfoControllerVieriTests {

        @Mock
        private LeverandorinfoServiceVieri service;

        private LeverandorinfoControllerVieri controller;

        @BeforeEach
        void setUp() throws IOException {
            startServer();
            controller = new LeverandorinfoControllerVieri(service);
            ReflectionTestUtils.setField(controller, "apiLoadSuppliers", "http://localhost:" + port);
            ReflectionTestUtils.setField(controller, "ocpApiManagement", "dummy-key");
        }

        @Test
        void finnLeverandortransaksjoner_returns200_returnsServiceData() {
            when(service.finnLeverandortransaksjoner(any(), any(), any(), any(), any()))
                    .thenReturn("[{\"leverandor\":\"BOUVET ASA\"}]");

            String result = controller.finnLeverandortransaksjoner(202, "BOUVET ASA", "7048", "NYDALEN", LocalDate.now());

            assertEquals("[{\"leverandor\":\"BOUVET ASA\"}]", result);
            verify(service).finnLeverandortransaksjoner(202, "BOUVET ASA", "7048", "NYDALEN", LocalDate.now());
        }

        @Test
        void finnLeverandortransaksjoner_withNullParams_returnsServiceData() {
            when(service.finnLeverandortransaksjoner(any(), any(), any(), any(), any()))
                    .thenReturn("[]");

            String result = controller.finnLeverandortransaksjoner(202, null, null, null, null);

            assertEquals("[]", result);
        }
    }
}
