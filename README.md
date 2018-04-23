# Пример Rest сервиса для тестового задания банка на Spring3 #

**Тех.задание**

Нужно хранить данные о:
- клиентах банка (идентификатор, имя — адрес, возраст по желанию);
- счетах (идентификатор, идентификатор владельца, количество денег);
- транзакциях — переводах денег между счетами или их поступлениях / списаниях.
Приложение по минимуму должно содержать три страницы:
- список клиентов банка, при щелчке по имени открываются счета выбранного
клиента (плюс внизу форма для добавления нового клиента);
- список счетов заданного клиента (плюс внизу форма для добавления нового счёта);
- форма для перевода денег между счетами (и для пополнения / списания денег:
затраты, покупки — м. б., это отдельные формы, на ваш вкус);
- страница со списком транзакций (сверху форма фильтра, чтобы можно было
выбрать за период и/или по заданному пользователю);
- всякие дополнения — на ваш вкус, например, в списке клиентов можно сумму на
всех счетах у каждого выводить и т. п.
Java 1.8 или выше, Maven


## Серверная часть. Включает набор Rest-сервисов ##

**Запуск тестов**
```bash
mvn test
```

**Запуск приложения**
```bash
mvn tomcat7:run
```

Rest-сервисы будут доступны по адресу: `http://localhost:8080/bank-api/ `

**Описание Rest-сервисов**

Включают работу с клиентами, счетами клиента, операциями. Ответы формируются в виде JSON-объектов.

**Клиент банка**

Адрес [http://localhost:8080/bank-api/client/](http://localhost:8080/bank-api/client/)

*Типы запросов:*

GET `http://localhost:8080/bank-api/client` - получение всех клиентов банка. Для отбора по имени добавить параметр `name`, для отбора по списку идентификаторов используется параметр в виде массива `id`

*Примеры:*

Получение всех клиентов

[http://localhost:8080/bank-api/client/](http://localhost:8080/bank-api/client/)

Результат:
```json
{"clients":[
    {"id":0,"name":"NAME_0","phone":""},
    {"id":1,"name":"NAME_1","phone":""}
  ]
}
```

Отбор по имени: [http://localhost:8080/bank-api/client/?name=NAME_0](http://localhost:8080/bank-api/client/?name=NAME_0)

Результат:
```json
{"clients":[
    {"id":0,"name":"NAME_0","phone":""}
  ]
}
```

Отбор по идентификатору: [http://localhost:8080/bank-api/client/1](http://localhost:8080/bank-api/client/1)

Результат:
```json
{"id":1,"name":"NAME_1","phone":""}
```

*Создание клиента*

Отправить POST запрос с JSON данными клиента на адрес
[http://localhost:8080/bank-api/client/]()

*Удаление клиента*

Отправить DELETE запрос на адрес
[http://localhost:8080/bank-api/client/{id_клиента}/]()

*Изменение клиента*

Отправить POST запрос с JSON данными клиента на адрес
[http://localhost:8080/bank-api/client/{id_клиента}/]()

Пример JSON:
```json
{"id":1,"name":"NEW_NAME","phone":"NEW_PHONE"}
```

**Счет клиента**

У клиента может быть несколько счетов.
Адрес [http://localhost:8080/bank-api/account/](http://localhost:8080/bank-api/account/)

*Типы запросов:*

GET [http://localhost:8080/bank-api/account/](http://localhost:8080/bank-api/account/) -
получение всех счетов всех клиентов.
Для отбора по имени клиента, добавить параметр `name_client`, для отбора по списку идентификаторов используется параметр в виде массива `id`

Получение всех счетов

[http://localhost:8080/bank-api/account/](http://localhost:8080/bank-api/account/)

Результат:
```json
{
    "accounts": [
        {
            "balance": "100.00",
            "client": {
                "id": 0,
                "name": "NAME_0",
                "phone": ""
            },
            "id": 1,
            "name": "ACCOUNT_1"
        },
        {
            "balance": "100.00",
            "client": {
                "id": 1,
                "name": "NAME_1",
                "phone": ""
            },
            "id": 2,
            "name": "ACCOUNT_2"
        }
    ]
}
```

Отбор по имени клиента: [http://localhost:8080/bank-api/account/?name=NAME_0](http://localhost:8080/bank-api/account/?name_client=NAME_0)

Результат:
```json
{
    "accounts": [
        {
            "id": 1,
            "name": "ACCOUNT_1",
            "balance": "100.00",
            "client": {
                "id": 0,
                "name": "NAME_0",
                "phone": ""
            }
        }
    ]
}
```

Отбор по идентификатору: [http://localhost:8080/bank-api/account/1](http://localhost:8080/bank-api/account/1)

Результат:
```json
{
    "id": 1,
    "name": "ACCOUNT_1",
    "balance": "100.00",
    "client": {
        "id": 0,
        "name": "NAME_0",
        "phone": ""
    }
}
```

*Создание счета*

Отправить POST запрос с JSON данными счета на адрес
[http://localhost:8080/bank-api/account]()

Пример JSON:
```json
{
    "name": "ACCOUNT_1",
    "balance": "100.00",
    "client": {
        "id": 0,
        "name": "NAME_0",
        "phone": ""
    }
}
```


*Удаление счета*

Отправить DELETE запрос на адрес
[http://localhost:8080/bank-api/account/{id_счета}]()

*Изменение счета клиента*

Отправить POST запрос с JSON данными счета клиента на адрес
[http://localhost:8080/bank-api/account/{id_счета}]()

Пример JSON:
```json
{
    "id": 1,
    "name": "NEW_NAME_ACCOUNT",
    "balance": "10000000000.00",
    "client": {
        "id": 0
    }
}
```

**Операции по счету**

Адрес [http://localhost:8080/bank-api/operation/](http://localhost:8080/bank-api/operation/)

*Типы запросов:*

GET `http://localhost:8080/bank-api/operation` -
получение всех движений по всем счетам всех клиентов.
Параметры отбора:

from_date - с какой даты. Строка в виде ```2018-01-31```

Запрос: [http://localhost:8080/bank-api/operation/?from_date=2018-03-09]()

Результат:
```json
{
    "operations": [
        {
            "amount": "150.00",
            "comment": "",
            "ddate": "2018-03-09",
            "dstAccount": {
                "balance": "100.00",
                "client": {
                    "id": 0,
                    "name": "NAME_0",
                    "phone": ""
                },
                "id": 1,
                "name": "ACCOUNT_1"
            },
            "id": 1,
            "srcAccount": {
                "balance": "100.00",
                "client": {
                    "id": 1,
                    "name": "NAME_1",
                    "phone": ""
                },
                "id": 2,
                "name": "ACCOUNT_2"
            }
        }
    ]
}
```
to_date - по какую дату. Строка в виде ```2018-01-31```

src_account_id - выбрать операции с id отправителя src_account_id

dst_account_id - выбрать операции с id получателя dst_account_id

ids - массив идентификаторов операций

*Создание операции*

Отправить POST запрос с JSON данными операции на адрес
[http://localhost:8080/bank-api/operation/]()

Пример JSON:
```json
        {
            "amount": "150.00",     // сумма перевола
            "comment": "",          // КОмментарий
            "ddate": "2018-03-09",  // дата, если не указана, будет текущая
            "srcAccount": {         // счет отправителя
                "id": 2
            },
            "dstAccount": {         // счет получателя
                "id": 1
            }
        }
```

*Удаление операции*

Отправить DELETE запрос на адрес
[http://localhost:8080/bank-api/operation/{id_операции}]()

Изменение операции не предусмотрено, только создание и удаление.



