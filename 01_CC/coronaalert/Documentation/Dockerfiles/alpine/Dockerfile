FROM alpine:latest

ARG SBT_VERSION=1.4.1
ENV SBT_HOME=/usr/local/sbt
VOLUME /app/test /app/app

LABEL maintainer ="Alvaro de la Flor Bonilla <alvdebon@correo.ugr.es>" \
        com.coronaalert.version="3.0.0" \
        com.coronaalert.release-date="2020-11-27" \
        com.coronaalert.repository="https://github.com/alvarodelaflor/CoronaAlert"

RUN addgroup -S coronaalert && \
    adduser -S usuario -G coronaalert -s /bin/ash && \
    chown -R usuario /app && \
    apk add --no-cache openjdk8 curl bash && mkdir $SBT_HOME && \
    curl -sL https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.tgz | \
    tar -xz --strip-components=1 -C $SBT_HOME && ln -s $SBT_HOME/bin/sbt /usr/bin/ && \
    sh -c "$(curl -sf https://gobinaries.com/tj/mmake/cmd/mmake)" && \
    alias make=mmake && \
    apk del curl

WORKDIR /app/test

USER usuario

COPY build.sbt .
COPY Makefile .

WORKDIR /app

CMD ["sbt", "test"]
