@echo off
echo Welcome!

set filename=%1
set compress=%2
set api=%3
set debug=%4
set ext=%5
set isFrameworkFile=%6

ECHO Deodexing %filename%.%ext%
ECHO Compression=%compress%
ECHO API=%api%
ECHO EXTENSION=%ext%


copy source\%filename%.%ext% framework /Y
copy source\%filename%.odex framework /Y

cd framework
java -jar baksmali.jar -a %api% -x %filename%.odex -d %cd% 2> error.log
type error.log
java -jar smali.jar out/ -o classes.dex 2> error.log
type error.log
del error.log
rmdir out /S /Q
cd ..


if "%isFrameworkFile%"=="true" ( copy framework\%filename%.%ext% working ) else (
move framework\%filename%.%ext% working
del framework\%filename%.odex )

move framework\classes.dex working\build

cd working
7za.exe x -o"build" %filename%.%ext%
del %filename%.%ext%

cd build
..\7za.exe a -tzip %filename%.%ext% * -mx%compress% 
cd ..

copy build\%filename%.%ext% ..\done /Y
rmdir build /S /Q
md build
cd ..

echo DEODEXING COMPLETE!

exit


