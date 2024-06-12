# BudgetBuddy
## Descrição
Este é um projeto Android que segue os princípios da Clean Architecture e o padrão MVVM (Model-View-ViewModel). A arquitetura é dividida em três camadas principais: Domain, Data e Presentation, cada uma com responsabilidades bem definidas para garantir um código modular, testável e fácil de manter. Utilizei Room para persistência local, Flow e Coroutines para programação assíncrona, Jetpack Compose para a interface do usuário, e Dagger Hilt para injeção de dependências. A idéia do app é armazenar produtos e clientes, onde que posteriormente eu possa fazer um orçamento para os meus clientes.

## Home Screen
<img src="https://github.com/renan-oliveira1/BudgetBuddy/assets/71901877/de05d335-3282-4ebf-97b1-8bba88b7f4b0" alt="Descrição da imagem" width="200"/>

## Clientes Screen
<img src="https://github.com/renan-oliveira1/BudgetBuddy/assets/71901877/11b9b2f9-d901-4583-a2c5-de1792ee2b69" alt="Descrição da imagem" width="200"/>

## Produtos Screen

<img src="https://github.com/renan-oliveira1/BudgetBuddy/assets/71901877/eb55b11b-5650-4976-9aa6-fb1f3e2bdf7d" alt="Descrição da imagem" width="200"/>

## Orçamentos Screen
<div style="display: flex; justify-content: space-around;">
  <img src="https://github.com/renan-oliveira1/BudgetBuddy/assets/71901877/5308c1e3-61d9-4e32-8c80-2e6d1980f3fc" alt="Descrição da imagem 2" width="300"/>
   <img src="https://github.com/renan-oliveira1/BudgetBuddy/assets/71901877/935fd35b-886a-40b9-a507-01993e240019" alt="Descrição da imagem 1" width="300"/>
</div>


## Estrutura do Projeto


### Domain
A camada de Domain contém a lógica de negócios e as regras da aplicação.

- **Model**: Classes de modelo que representam os dados principais da aplicação.
- **Use Cases**: Contêm a lógica de negócios da aplicação. Cada caso de uso encapsula uma única ação de negócio.
- **Repositories**: Interfaces que definem os métodos para acessar dados. As implementações destas interfaces são fornecidas pela camada de Data.

### Data
A camada de Data é responsável por fornecer dados à aplicação. Esta camada implementa os repositórios definidos na camada de Domain e contém as fontes de dados, que podem ser locais (banco de dados) ou remotas (APIs). Os principais componentes desta camada são:

- **Repositories**: Implementações das interfaces de repositório definidas na camada de Domain.
- **Data Sources**: Fontes de dados concretas, como APIs, bancos de dados locais, caches, etc.

### Presentation
A camada de Presentation é responsável pela interface com o usuário e contém toda a lógica relacionada à UI. Utilizamos o Jetpack Compose para construção da UI. Os principais componentes desta camada são:

- **ViewModels**: Gerenciam os dados para as views e lidam com a lógica da interface do usuário. Comunicam-se com os use cases para realizar ações de negócio.
- **Composable Functions**: Funções anotadas com `@Composable` que definem os componentes da interface do usuário.

## Configuração e Execução do Projeto

### Passos para Clonar e Executar
1. Clone o repositório:
   ```sh
   git clone https://github.com/renan-oliveira1/BudgetBuddy.git

```plaintext
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/com/example/budgetbuddy
│ │ │ │ ├── presentation/
│ │ │ │ │ ├── screen-name/
│ │ │ │ │ │ ├── view/
│ │ │ │ │ │ └──  viewmodel/
│ │ │ │ ├── domain/
│ │ │ │ │ ├── use_case/
│ │ │ │ │ ├── repository/
│ │ │ │ │ └── model/
│ │ │ │ ├── data/
│ │ │ │ │ ├── repository/
│ │ │ │ │ └── data_source/
│ │ │ │ │ | ├── dao/
│ │ │ │ │ | └── database/
│ │ │ │ └── di/
│   └── res/
├── build.gradle
└── settings.gradle
