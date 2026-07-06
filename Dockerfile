FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre@sha256:4b74592038b3c9631c5ef47bcde5b6f426ef1d569201a169f990730b1c73fa17
ENV TZ="Europe/Oslo"
COPY target/*.jar app.jar
CMD ["-jar","app.jar"]