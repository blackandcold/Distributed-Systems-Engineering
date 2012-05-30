rm -Rf ~/.m2/at
mvn compile
mvn jar:jar -DclassesDirectory=target/classes/ 
mvn install:install-file -Dfile=target/domain-1.0.jar -DgroupId=at.ac.tuwien.dse.fairsurgeries -DartifactId=domain -Dversion=1.0 -Dpackaging=jar -DlocalRepositoryPath=../lib
