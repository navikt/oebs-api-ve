FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre@sha256:2954ceba5d76628a0fcbac6f32eb4d8b9fbaf8ceda5f9043082cf1e996429e29
ENV TZ="Europe/Oslo"
COPY target/*.jar app.jar
CMD ["-jar","app.jar"]