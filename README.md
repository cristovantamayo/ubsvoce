# UBSVoce

Seja bem-vindo ao projeto UBSVoce.
A proposta do Aplicativo é ajudar <a>Você</a> e a população brasilera em geral a localizar a <b>Unidade Básica de Saúde (UBS)</b> <i>mais próxima</i> de sua localixação ou localização informada.
O projeto jaz uso de outros projetos e serviços como, por exemplo, as APIs Geocondig e Maps do Google.

Além sa fonte da informação relativa a geolocalização provida pela API do Google, também se faz uso dos dados fornecidos pelo Portal Brasileiro de Dados Abertos ([ubs.csv](http://dados.gov.br/dataset/unidades-basicas-de-saude-ubs)).

Uma vez que o CSV contém Latitude e Logitude da UBs o processo lógico para se obter o resultado esperado é relativamente simple, podendo quer descrito em poucos passos:

* Receber a consulta via formulário <String>
* Encaminhar a Consulta à Geocoding API do Google <String> e tratar o rico response <ResultSet> restraindo o que convês, incluvice as Geocoordenadas (lat, lng).
* Filtrar as UBSs (já em banco de dados) com base em cálculos, segundo a proximidade com ponto e referência (lat, lng) ... lá do Google.
* Ordenar, Numerar e devolver para o Google por meio de sua Maps Javascript API.

E viva o Google! \o/

A Estrutura do Banco de Dados (Relacional) é pequena ...e poderia ser ainda mais. Mas visando um futuro aperfeiçoamento e recursos adicionais a modelagem tomou a seguinte forma:

<center>![Diagrama de Classe](http://webconstrutores.com/ubsvoce/diagrama-de-classe-ubsvoce.jpg)</center>

