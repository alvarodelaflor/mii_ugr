FROM ubuntu:latest

ENV TZ=Europe

VOLUME /app/test /app/app

LABEL maintainer ="Alvaro de la Flor Bonilla <alvdebon@correo.ugr.es>" \
        com.coronaalert.version="3.0.0" \
        com.coronaalert.release-date="2020-11-27" \
        com.coronaalert.repository="https://github.com/alvarodelaflor/CoronaAlert"

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && \
    useradd -m usuario && \
    chown -R usuario /app && \
    apt-get update && apt-get install curl gnupg2 -y && \
    echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
    apt-get update && \
    apt-get install -y sbt openjdk-11-jdk && \
    sh -c "$(curl -sf https://gobinaries.com/tj/mmake/cmd/mmake)" && \
    alias make=mmake && \
    apt-get remove -y curl gnupg

WORKDIR /app/test

USER usuario

COPY build.sbt .
COPY Makefile .

WORKDIR /app

CMD ["sbt", "test"]
