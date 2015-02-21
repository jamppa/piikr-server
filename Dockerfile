FROM ubuntu:14.10
MAINTAINER Jani Arvonen <jani.arvonen@gmail.com>

# Update
RUN apt-get update

# Add developer user
RUN adduser piikr --home /home/piikr --gecos "" --disabled-password

# Prepare apt-get
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install python-software-properties
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install software-properties-common

# Install Java 8
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN echo debconf shared/accepted-oracle-license-v1-1 seen true | /usr/bin/debconf-set-selections
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install oracle-java8-installer

# Install Leiningen
RUN wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein -O /bin/lein && chmod +x /bin/lein

EXPOSE 3000

USER piikr
WORKDIR /home/piikr
RUN mkdir -p code
RUN lein
WORKDIR /home/piikr/code

CMD ["bash"]
