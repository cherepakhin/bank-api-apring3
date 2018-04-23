#!/usr/bin/env bash
http --json POST http://localhost:8080/bank-api/client/ name="222" phone="2"
http --json POST http://localhost:8080/bank-api/client/0 name="222" phone="2" id=0
http://127.0.0.1:8080/bank_api/client/?
http --json GET http://localhost:8080/bank-api/client/
http --json DELETE http://localhost:8080/bank-api/client/1
http --json GET http://localhost:8080/bank-api/client/2
http --json GET http://localhost:8080/bank-api/client/ name=="--"
http --json GET http://localhost:8080/bank-api/client/ ids=="0,2" name=="-"

http POST http://localhost:8080/bank-api/account/ @account.json
http POST http://localhost:8080/bank-api/operation/ @operation.json
http POST http://localhost:8080/bank-api/account/

http --json GET http://localhost:8080/bank-api/operation/