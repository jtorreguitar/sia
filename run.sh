mvn clean -Dmaven.test.skip=true install > /dev/null
cd main
mvn exec:java -Dexec.mainClass="ar.edu.itba.sia.main.Main" -Dexec.args="$1 $2 $3"
