# Ariane 5 Simulator

Simulacao em Java inspirada no incidente do Ariane 5, com foco em arquitetura, modularidade e boas praticas. O projeto implementa os componentes do diagrama em modulos como `OBC`, `SRI`, sensores, telemetria, seguranca e base de controle em solo.

## Arquitetura escolhida

Foi utilizada **Arquitetura Hibrida**.

### Por que nao Microservices?

Microservices nao sao a melhor escolha para um software embarcado critico, porque adicionariam complexidade de rede, latencia e distribuicao onde o objetivo principal e responder localmente com previsibilidade.

### Por que nao Client-server?

Client-server cobre bem a comunicacao com a base em solo, mas nao organiza de forma adequada os subsistemas internos do foguete, que precisam cooperar em tempo real dentro do mesmo processo.

### Por que nao somente Event-driven?

Eventos sao muito uteis para desacoplamento, porem so eles nao bastam para estruturar responsabilidades tecnicas como navegacao, seguranca, controle e telemetria.

### Por que Arquitetura Hibrida?

A solucao combina:

- **modularizacao em camadas**, para separar dominio, interfaces, modulos e orquestracao;
- **fluxo orientado a eventos**, para telemetria, diagnosticos e falhas;
- **comunicacao estilo client-server**, limitada ao envio de transmissao para a base em solo.

Assim o projeto fica mais proximo de um sistema embarcado real: coeso, local, tolerante a falhas e facil de evoluir.

## Design patterns utilizados

### 1. Strategy

Aplicado em `SoftwareNavegacao` e no tratamento de excecoes do `SRI`.

- Permite alternar algoritmos por modelo de parametros (`ARIANE_4` e `ARIANE_5`);
- evita condicionais gigantes espalhadas pelo sistema;
- facilita simular o erro historico de reutilizacao inadequada de parametros.

### 2. Observer / Event Bus

Aplicado em `SimpleEventBus`.

- Modulos publicam eventos sem conhecer diretamente seus consumidores;
- `SRI`, `OBC`, solo e diagnostico ficam desacoplados;
- deixa o codigo mais modular e aderente a um sistema com telemetria e falhas.

### 3. Command

Aplicado em `ComandoTrajetoria`.

- Cada comando de correcao encapsula a intencao de atuacao;
- o `OBC` decide o que emitir e o `AtuadorMotor` executa;
- melhora extensibilidade para novos tipos de correcoes.

### 4. Facade

Aplicado em `Ariane5LaunchSimulation`.

- Centraliza a montagem da simulacao;
- simplifica a execucao do fluxo principal;
- esconde a complexidade de inicializacao dos subsistemas.

### 5. Factory

Aplicado em `NavigationAlgorithmFactory`.

- Cria a estrategia correta de navegacao conforme o modelo configurado;
- reforca o principio de responsabilidade unica.

## Estrutura

```text
src/ariane5
  app/                -> ponto de entrada
  bus/                -> barramento de eventos
  core/               -> contratos centrais
  domain/             -> entidades, enums e eventos
  interfaces/         -> contratos entre modulos
  modules/
    telemetry/        -> sensores e coleta
    navigation/       -> software de navegacao
    safety/           -> SRI e politicas de falha
    obc/              -> computador de bordo
    ground/           -> transmissao para o solo
  simulation/         -> orquestracao da simulacao
```

## Como executar

Compile:

```bash
javac -d out $(find src -name "*.java")
```

Execute:

```bash
java -cp out ariane5.app.Main
```

## O que a simulacao demonstra

- leitura mockada de sensores;
- geracao e validacao de telemetria;
- processamento redundante em dois SRIs;
- erro de overflow ao usar parametros incompativeis;
- entrada do `OBC` em modo seguro;
- envio de diagnosticos e transmissao para a base em solo;
- armamento do modulo de autodestruicao quando nao ha pacote valido.
