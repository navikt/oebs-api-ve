FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre@sha256:4e5232760a4c30104b4339b4ec4f9cf56684254ea98aa7e7bd8f2008793bcf40
ENV TZ="Europe/Oslo"
COPY target/*.jar app.jar
CMD ["-jar","app.jar"]