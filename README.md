# mvc-tck

Technology Compatibility Kit for JSR 371

## Build

Run the Maven build for the TCK:

    cd <TCK_HOME>
    mvn clean install

## Run Sample Runner

Download and unpack the latest Glassfish 5 Promoted Build:

    https://javaee.github.io/glassfish/download
    
Start Glassfish:

    cd <GLASSFISH_HOME>/glassfish/bin/
    ./startserv

Now execute the sample runner to run the tests against Eclipse Krazo deployed to Glassfish:

    cd <TCK_HOME>/sample/
    mvn clean test
    
Of course all the tests should pass. :-)

## Run the signature tests

Download the 3.0 dev version of the SigTest tool here:

    https://download.java.net/sigtest/download.html

Now run the tests for a specific package with a command similar to this: 

    java -jar ${SIGTEST_HOME}/lib/sigtestdev.jar SignatureTest -Static \
      -FileName ${HOME}/.m2/repository/org/mvc-spec/tck/mvc-tck-sigtest/1.0-SNAPSHOT/mvc-tck-sigtest-1.0-SNAPSHOT.sigfile \
      -Classpath ${JAVA_HOME}/jre/lib/rt.jar:${HOME}/.m2/repository/javax/mvc/javax.mvc-api/1.0-SNAPSHOT/javax.mvc-api-1.0-SNAPSHOT.jar:${HOME}/.m2/repository/javax/javaee-web-api/8.0/javaee-web-api-8.0.jar \
      -Package javax.mvc
