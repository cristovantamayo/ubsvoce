![Logo UBSVoce](http://webconstrutores.com/ubsvoce/favicon.png)
# UBSVoce

Seja bem-vindo ao projeto **UBSVoce**.

A proposta do Aplicativo é ajudar <b>Você</b> e a população brasilera em geral a localizar a **Unidade Básica de Saúde** (**UBS**) <u><i>mais próxima</i></u> de sua localização ou localização informada.

O projeto jaz uso de outros projetos e serviços como, por exemplo, as APIs [Geocondig]([https://developers.google.com/maps/documentation/geocoding/start](https://developers.google.com/maps/documentation/geocoding/start)) e [Maps]([https://cloud.google.com/maps-platform/maps/](https://cloud.google.com/maps-platform/maps/)) do Google.

Além sa fonte da informação relativa a geolocalização provida pela API do Google, também se faz uso dos dados fornecidos pelo **Portal Brasileiro de Dados Abertos** ([ubs.csv](http://dados.gov.br/dataset/unidades-basicas-de-saude-ubs)).

Uma vez que o CSV contém <i>Latitude</i> e <i>Logitude</i> de cade UBS, o processo lógico para se obter o resultado esperado é relativamente simple, podendo quer descrito em poucos passos:

* Receber a consulta via formulário \<String>
* Encaminhar a consulta à **Geocoding API**  \<String> e tratar o rico response \<GeocodingResult[]> extraindo o que convês, incluvice as Geocoordenadas (lat, lng).
* Filtrar as UBSs (já em banco de dados) com base em cálculos, segundo a proximidade com ponto e referência (lat, lng) ... lá do Google.
* Ordenar, Numerar e devolver para o Google por meio de sua **Maps Javascript API**.

E viva o Google! **\o/**

# Tecnologia
Esta é uma aplicação Java, ou melhor, esta á uma aplicação cujo o Back-end é desenvolvido em Java fazendo uso do aclamado trio de desenvolvimento Spring Boot, Hibernate e JPA, apresentando endpoints tanto para consumo das interfaces com o usuário quanto para a saída REST da API. O Front-end nada mais é que html, javascript (jQuery), css e... photoshop. 

Aqui vamos focar nosso entendimento no Back-end da Aplicação:

# Arquitetura
## Diagrama de Classes
A Estrutura do Banco de Dados (<i>Relacional</i>) é pequena ...<u>e poderia ser ainda mais</u>. Mas visando um <u>futuro aperfeiçoamento</u> e <u>recursos adicionais<u> a modelagem tomou a seguinte forma:
![enter image description here](http://webconstrutores.com/ubsvoce/diagrama-ubsvoce.jpg)

A tabela Locais existe, mas neste primeiro momento apenas coleta dados, seu uso ainda está em fazem de experimentação: muitas consultas à API poderam ser poupadas.

## Camadas da Aplicação


![enter image description here](http://webconstrutores.com/ubsvoce/camadas-da-aplicacao-ubsvoce.jpg)


Seguindo a linha da tecnologia acima citada temos:

 - **Camada de Entidades**  *@*Entity** - nada mais que os domínios da solução, são classes, representações de objetos quantitativos da vida real. São elas> Unidade, Geocode, Score e Locais. Importante destacar que Geocode e Score estão ambas em um relacionamento @OneToOne com Unidade, sendo a Unidade o lado forte da relação. Locais corre em paralelo e não se relacionando diretamente com as demais entidade mas coletando dados de pesquisa.

- **Camada de Repositórios** *@*Repository** - os repositórios estão na mesma proporção das entidades. São Interfaces repletas de métodos patterns para persistência e recuperação de dados (Não queremos reinventar a roda!). Implementamos JpaRepository<> do springframework. Para não dizer que nada foi criado, temos dentro de UnidadeRepository uma método personalizado o qual foi essencial para o excelente desempenho da Aplicação. Vale a pela conferir, está documentado.
- **Camada de Serviços** *@*Service* - Serviços corresponde ao centro lógico da aplicação. É nesta camada que o aplicativos "diz à que veio". Serviços obviamente servem a praticamente todas as funcionalidade disponíveis. Neste projeto temos **três serviços** tratando de pontos chaves da aplicação. São eles: (**1**) <u>***FileSystemStorageService***</u> que implementa a interface SorageService no tratamente do upload e armazenamento do arquivo [ubs.csv](http://dados.gov.br/dataset/unidades-basicas-de-saude-ubs) quando submetido pelo formulário de envio. (**2**) <u>***GeocodingService***</u> responsável todo o trato das Requisições ao Google e processamento da responda obtida, realizando todo o processamento para definir o resultado esperado da pesquisa. GeocodingService faz uso de uma classe que está em seu mesmo pacote: a GeocodingUtil que contém o cálculo matemático que encontra a distância entre dois pares (lat1, lng1) e (lat2, lng2) de Geocoordenadas. (**3**) <u>***UnidadeService***</u> que atua no primeiro momento da aplicação, pois é o serviço responsável por tornar as linhas do arquivo [ubs.csv](http://dados.gov.br/dataset/unidades-basicas-de-saude-ubs) em Objetos Java; no caso: Unidade, Geocode e Score
- **Camada de Controladores** *@Controller* e *@RestController* - O <u>*@Controller*</u> de hoje é <u>*@WebServlet*</u> instanciado de anos atrás. E de fato ainda o é: por de trás de cada <u>*@Controller*</u>, lá está o <u>*@WebServlet*</u>. Os controladores constituem a camada responsável por absorver as interações com os usuário por meio de seus <u>*endpoints*</u> de acesso, URLs, links para o mundo exterior. Trata as requisições (<u>*request*</u>) e as respostas (<u>*response*</u>) proveniente de formulários e outras fontes. Neste projeto temos poucos <u>*endpoints*</u>, o que não tira a funcionalidade da aplicação.  São os Controladores:

	> ***@Controller FileUploadController***</u> relativo ao upload do <u>*arquivo CSV*</u>, e que  disponibiliza <u>*3 endpoints*</u>:<br />(**1**) (GET) <u>*/config/import*</u> que aponta para a página com o formulário de importação. <br/>(**2**) (POST) <u>*/config/uploader*</u>  ao qual é submetido pelo primeiro formulário  e<br />(**3**) (GET) <u>/import/files/{filename:.+}</u>  que não é acessado diretamente mas que <u>retorna na o link do arquivo já carregado</u> junto ao formulário de /config/import,

	> ***@Controller UbsvoceController***</u> onde encontrasse  o ***home*** (GET) (**/**) da aplicação.

	> ***@Controller UnidadeResourceAPI*** que disponibiliza o <u>endpoint da API<u>: <br /> (GET) <u>/v1/find_ubs?**address**&**radius**&**page**&**per_page**</u> onde **radius**, **page** e **per_page** são opcionais.
- Auxiliando na formatação do JSON de resposta da API temos dois **Objetos Estruturais**. Esses objetos são POJOs, como são as entidades que representam dados, mas não são objetos quantitativos, só trazem um <u>*container*</u> para unir e organizar informações de resposta e um <u>*append*</u> enriquecer o conteúdo de resposta . São eles: ***Coods*** que agregam um par de geo-coordenadas para o  endereço pesquisado e  ***Estrutura***, que agrupa os dados em um formado adequado para uma API.
- A **Camada de Banco de Dados** corresponde a uma instância do Banco de Dados Mysql o qual terá suas tabelas criadas automaticamente pelo Hibernate e pelo JPA.

## Estrutura de Pacotes

```
├── src/main/java
│   ├── com.ubsvoce
│   │   │
│   │   └── UbsvoceApplication.java
│   │   
│   ├── com.ubsvoce.controllers
│   │   │
│   │   ├── FileUploadController.java
│   │   └── UbsvoceController.java
│   │   
│   ├── com.ubsvoce.controllers.rest
│   │   │
│   │   └── UnidadeResourceAPI.java
│   │   
│   ├── com.ubsvoce.entities
│   │   │
│   │   ├── Geocode.java
│   │   ├── Local.java
│   │   ├── Score.java
│   │   └── Unidade.java
│   │   
│   ├── com.ubsvoce.entities.enums
│   │   │
│   │   └── ScoreType.java
│   │   
│   ├── com.ubsvoce.entities.util
│   │   │
│   │   ├── Coods.java
│   │   └── Estrutura.java
│   │   
│   ├── com.ubsvoce.repositories
│   │   │
│   │   ├── GeocodeRepository.java
│   │   ├── LocalRepository.java
│   │   ├── ScoreRepository.java
│   │   └── UnidadeRepository.java
│   │   
│   ├── com.ubsvoce.services
│   │   │
│   │   ├── FileSystemStorageService.java
│   │   ├── StorageService.java
│   │   ├── GeocodingSerive.java
│   │   ├── GeocodingUtil.java
│   │   └── UnidadeService.java
│   │   
│   ├── com.ubsvoce.services.exceptions
│   │   │
│   │   ├── CSVException.java
│   │   ├── GeocodingServiceException.java
│   │   ├── StorageException.java
│   │   └── StorageFileNotFoundException.java
│   │   
│   ├── com.ubsvoce.services.storage
│   │   │
│   │   └── StorageProperties.java
│   │   
│   └── com.ubsvoce.services.util
│       │
│       └── Paginador.java
│   
├── src/main/resources
│   ├── static
│   │   ├── css
│   │   │	└── **/*.css
│   │   ├── image
│   │   │    ├── markers
│   │   │    │		└── **/*.png
│   │   │    └── **/*.png
│   │   └── js
│   │   	└── **/*.js
│   └── templates
│	  	├── Home.html
│	 	└── insert.html
│
├── src/test/java
│   │
│   ├── com.ubsvoce
│   │   │
│   │   └── UbsvoceApplicationTest.java
│   │   
│   ├── com.ubsvoce.controllers
│   │   │
│   │   └──FileUploadTest.java
│   │   
│   ├── com.ubsvoce.entities
│   │   │
│   │   └──UnidadeTest.java
│   │   
│   └── com.ubsvoce.service
│       │
│	  	├── GeocodingService.java
│	  	├── PaginadorTest.java
│	  	├── TestConfiguration.java
│       └──UnidadeServiceTest.java
│
├── docker-compose.yml
├── Dockerfile
├── README.md
├── pom.xml 
└── .gitignore
```

## API /v1/find_ubs

![enter image description here](http://webconstrutores.com/ubsvoce/api-rest.jpg)