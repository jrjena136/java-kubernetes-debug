FROM alpine:latest
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=*:5005,server=y,suspend=n -Xms256m -Xmx512m -XX:MaxMetaspaceSize=128m"
WORKDIR /tmp
RUN apk update && apk --no-cache add --update bash wget tar
ARG JDK_URL="https://download.java.net/java/GA/jdk13.0.1/cec27d702aa74d5a8630c65ae61e4305/9/GPL/openjdk-13.0.1_linux-x64_bin.tar.gz"
RUN wget -O openjdk.tar.gz "$JDK_URL" && tar -xzf openjdk.tar.gz && rm openjdk.tar.gz
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub \
    && wget -q https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.30-r0/glibc-2.30-r0.apk \
    && wget -q https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.30-r0/glibc-bin-2.30-r0.apk \
    && apk add --no-cache --allow-untrusted glibc-2.30-r0.apk glibc-bin-2.30-r0.apk \
    && rm glibc-2.30-r0.apk glibc-bin-2.30-r0.apk
RUN wget "https://www.archlinux.org/packages/core/x86_64/zlib/download" -O /tmp/libz.tar.xz \
    && mkdir -p /tmp/libz \
    && tar -xf /tmp/libz.tar.xz -C /tmp/libz \
    && cp /tmp/libz/usr/lib/libz.so.1.2.11 /usr/glibc-compat/lib \
    && /usr/glibc-compat/sbin/ldconfig \
    && rm -rf /tmp/libz && rm /tmp/libz.tar.xz
RUN mkdir /usr/myapp
ENV JAVA_HOME=/usr/myapp/java
RUN mv /tmp/jdk-13.0.1 /usr/myapp/ && cd /usr/myapp/ && ln -s jdk-13.0.1 java && chown -R root:root java && chmod -R +x java/bin/
RUN echo "PATH=${PATH}:${JAVA_HOME}/bin; export PATH" >> /root/.bashrc \
    && source /root/.bashrc
COPY target/*.jar /usr/myapp/app.jar
WORKDIR /usr/myapp
EXPOSE 8080
ENTRYPOINT [ "/bin/bash", "-c", "${JAVA_HOME}/bin/java $JAVA_OPTS -jar app.jar" ]
#ENTRYPOINT ["tail", "-f", "/dev/null"]
