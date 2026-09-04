FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre@sha256:f2d779dda60f8f966d5a0ee3c5aa49ac0193ca25c4d5bd8e492fe256c5a6b825
ENV TZ="Europe/Oslo"
COPY target/*.jar app.jar
CMD ["-jar","app.jar"]