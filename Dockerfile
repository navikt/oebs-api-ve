FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre@sha256:980b1fddafe492d16d13726e296e76491200dab64d206fbcf8474d2d25fa5418
ENV TZ="Europe/Oslo"
COPY target/*.jar app.jar
CMD ["-jar","app.jar"]