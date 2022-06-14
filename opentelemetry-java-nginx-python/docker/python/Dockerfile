FROM kubile/python3-otel:1.0

ENV OTEL_WORKDIR=/app
WORKDIR $OTEL_WORKDIR

COPY ./python/ .

RUN ln -s $OTEL_WORKDIR/server.sh /usr/local/bin/entrypoint

RUN pip uninstall -yr requirements.txt && \
    pip install -r requirements.txt

# 利于调试
RUN apt -y install procps && \
    apt -y install inetutils-ping && \
    apt -y install telnet && \
    apt -y install netcat-traditional && \
    apt -y install lsof

ENTRYPOINT ["entrypoint"]