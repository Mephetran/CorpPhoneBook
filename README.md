# CorpPhoneBook

Little Android application that organizes corporate phonebook.

It takes data from remote server. The server creates json file from Active Directory.

Example of this file:

```json
{
    "code": 1234,
    "list": [
        {
            "sn": "Ivanov",
            "givenname": "Ivan",
            "title": "Manager",
            "department": "Sales Department",
            "mobile": "XXXXXXXXXXX"
        },
        {
            "sn": "Petrov",
            "givenname": "Petr",
            "title": "Engineer",
            "department": "Engineering Department",
            "mobile": "XXXXXXXXXXX"
        }
    ]
}
```

I desided to create this file with PHP script. But it can have any other ways for resolving.
