# CorpPhoneBook

A little Android application that organizes a corporate phonebook.

It takes data from a remote server. The server creates a JSON file from the Active Directory.

Example of this file:

```JSON
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

I decided to create this file with a PHP script. But it can have any other ways of resolving.
