@echo off
echo building PKCS12Builder.jar as library ...
javac ../src/com/leon_junio/*.java
jar cvef src.com.leon_junio.PKCS12Builder PKCS12Builder.jar ../src/com/leon_junio/*.class
del /f ..\src\com\leon_junio\*.class