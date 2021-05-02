# Product-ms

**Informações gerais**
- Para desenvolvimento foi utilizado a versão 15 do Java. 
- Para iniciar a base de dados do projeto, basta entrar na pasta .docker e executar o comando docker-compose up.
- Após isso, executar os comandos:
    ./mvnw clean install - limpa o projeto, gera o jar e as dependências do mapstruct
    ./mvnw clean teste - limpa o projeto e executa os testes isoladamente
    ./mvnw spring-boot:run - executa o projeto na porta 9999


**Observações**
- Serão gerados dois schemas na base de dados, um que será usado para teste e um que será o "oficial".
- Como foi descrito na documentação no seguinte trecho: "Importante que antes da atualização as mesmas regras de validação do POST /products devem ser executadas", não é permitido alterar apenas o nome, descrição ou preço. Todos os dados devem ser informados. Caso seja necessário permitir a alteração de um único atributo, favor informar que eu altero.
- Como não foi citado na documentação, não foi gerado paginação para a busca de todos os dados, nem autenticação ou integração com Spring Cloud.


**Documentação e Collection**
<br>
https://documenter.getpostman.com/view/4512599/TzRLmBBx 
<br>
https://www.getpostman.com/collections/59040b15691ef4a54ade
