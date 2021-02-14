# Cadastro de Clientes
Backend da aplicação de cadastro de clientes

## Banco de dados

Esta aplicação utiliza o banco de dados MySql. O esquema e tabela utilizados pela aplicação serão criados pelas migrations do Flyway

```
jdbc:mysql://localhost:3306/cadastroCliente?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
username=root
password=root

```

## Executando a aplicação localmente

```
cd cadastrocliente
mvn spring-boot:run
```

## Executando os testes unitários

Os teste foram desenvolvidos com JUnit test

## Serviços Disponibilizados

### Cliente

#### Salvar o Cliente

POST localhost:8080/cliente/

##### Body

```
{
    "nome" : "",
    "dataNascimento" : "1988-04-26",
    "sexo" : "MASCULINO",
    "cidade" : { "codigo" : 1 }
}
```

#### Buscar cliente por código

GET http://localhost:8080/clientes?&codigoCliente=1

#### Exclui Cliente

DELETE http://localhost:8080/clientes?&codigoCliente=10

#### Alterar nome do cliente

PUT http://localhost:8080/clientes/alterarNome

##### Body

```
{
    "codigo" : 1,
    "nome": "Nome do Cliente"
}
```

### Cidade

#### Salva a cidade

POST http://localhost:8080/cidades

##### Body

```
{
    "nome": "Rio Branco",
    "estado": {"sigla" : "AC","nome": "Acre"}
}
```

#### Busca cidades por nome

GET http://localhost:8080/cidades/nome?&nomeCidade=Rio Branco

##### Response

```
[
    {
        "codigo": 1,
        "nome": "Rio Branco",
        "estado": {
            "sigla": "AC",
            "nome": "Acre"
        }
    }
]
```

#### Buscar cidade por estado

GET http://localhost:8080/cidades/estado?&siglaEstado=MG

##### Response

```
[
    {
        "codigo": 2,
        "nome": "Uberlandia",
        "estado": {
            "sigla": "MG",
            "nome": "Minas Gerais"
        }
    },
    {
        "codigo": 3,
        "nome": "Araguari",
        "estado": {
            "sigla": "MG",
            "nome": "Minas Gerais"
        }
    }
]
```



