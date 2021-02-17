FROM centos:latest

ARG SBT_VERSION=1.4.1
ENV SBT_HOME=/usr/local/sbt
ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk/
VOLUME /app/test /app/app

LABEL maintainer ="Alvaro de la Flor Bonilla <alvdebon@correo.ugr.es>" \
        com.coronaalert.version="3.0.0" \
        com.coronaalert.release-date="2020-11-27" \
        com.coronaalert.repository="https://github.com/alvarodelaflor/CoronaAlert"

RUN adduser usuario -s /bin/ash && \
    chown -R usuario /app && \
    yum install -y \
    java-1.8.0-openjdk \
    java-1.8.0-openjdk-devel \
    wget \
    curl && \
    wget http://dl.bintray.com/sbt/rpm/sbt-${SBT_VERSION}.rpm && \
    yum install -y sbt-${SBT_VERSION}.rpm && \
    sh -c "$(curl -sf https://gobinaries.com/tj/mmake/cmd/mmake)"

WORKDIR /app/test

USER usuario

COPY build.sbt .
COPY Makefile .

WORKDIR /app

CMD ["sbt", "test"]
