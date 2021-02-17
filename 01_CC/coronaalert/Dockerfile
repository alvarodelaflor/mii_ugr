FROM alpine:latest

ARG SBT_VERSION=1.2.8
ENV SBT_HOME=/usr/local/sbt
VOLUME /app/test

LABEL maintainer ="Alvaro de la Flor Bonilla <alvdebon@correo.ugr.es>" \
        com.coronaalert.version="3.0.0" \
        com.coronaalert.release-date="2020-11-27" \
        com.coronaalert.repository="https://github.com/alvarodelaflor/CoronaAlert"

WORKDIR /app/test/

COPY ./coronaalert /app/test

RUN addgroup -S coronaalert && \
    adduser -S usuario -G coronaalert -s /bin/ash && \
    chown -R usuario . && \
    apk add --no-cache openjdk8 curl bash && mkdir $SBT_HOME && \
    apk add --update make && \
    curl -sL https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.tgz | \
    tar -xz --strip-components=1 -C $SBT_HOME && ln -s $SBT_HOME/bin/sbt /usr/bin/ && \
    sh -c "$(curl -sf https://gobinaries.com/tj/mmake/cmd/mmake)" && \
    alias make=mmake && \
    apk del curl

USER usuario

EXPOSE 9000

CMD ["make", "run"]
