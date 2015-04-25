@echo off
REM call flyway.cmd -configFile=<properties file> -url=<DB Connection URL> -locations=<SQL file location> migrate
call flyway.cmd -configFile=conf\flyway.properties migrate
