mkdir out
javac -d out *.java
cd out
dx --dex --output=../hello_dex.jar example/hello/*.class
