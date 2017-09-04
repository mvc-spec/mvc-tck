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

Now execute the sample runner to run the tests against Ozark deployed to Glassfish:

    cd <TCK_HOME>/sample/
    mvn clean test
    
Of course all the tests should pass. :-)
