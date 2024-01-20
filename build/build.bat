@echo off
echo building PKCS12Builder.jar as library ...
javac -d . ../src/com/leonjunio/*.java
jar cvef src.com.leonjunio.PKCS12Builder PKCS12Builder.jar src/com/leonjunio/*.class
jar cf PKCS12BuilderLib.jar src/com/leonjunio/*.class ../README.MD ../example.p12
del /f ..\build\src\com\leonjunio\*.class