FROM kubile/ubuntu-sdkman:jdk.8.0.322-zulu

ENV OTEL_WORKDIR=/app
WORKDIR $OTEL_WORKDIR

COPY ./java/ .
ENV PATH=/root/.sdkman/candidates/java/current/bin:$PATH

RUN ln -s $OTEL_WORKDIR/bootstrap.sh /usr/local/bin/entrypoint

RUN apt -y install procps && \
    apt -y install inetutils-ping && \
    apt -y install telnet && \
    apt -y install netcat-traditional && \
    apt -y install lsof

ENTRYPOINT ["entrypoint"]