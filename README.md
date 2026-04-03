# Ariane 5 Simulator

Simulação em Java inspirada no incidente do Ariane 5, com foco em arquitetura, modularidade e boas práticas. O projeto implementa os componentes do diagrama em módulos como `OBC`, `SRI`, sensores, telemetria, segurança e base de controle em solo.

## Arquitetura escolhida

Foi utilizada **Arquitetura Híbrida**.

### Por que não Microservices?

Microservices não são a melhor escolha para um software embarcado crítico, porque adicionariam complexidade de rede, latência e distribuição onde o objetivo principal é responder localmente com previsibilidade.

### Por que não Client-server?

Client-server cobre bem a comunicação com a base em solo, mas não organiza de forma adequada os subsistemas internos do foguete, que precisam cooperar em tempo real dentro do mesmo processo.

### Por que não somente Event-driven?

Eventos são muito úteis para desacoplamento, porém só eles não bastam para estruturar responsabilidades técnicas como navegação, segurança, controle e telemetria.

### Por que Arquitetura Híbrida?

A solução combina:

- **modularização em camadas**, para separar domínio, interfaces, módulos e orquestração;
- **fluxo orientado a eventos**, para telemetria, diagnósticos e falhas;
- **comunicação estilo client-server**, limitada ao envio de transmissão para a base em solo.

Assim o projeto fica mais próximo de um sistema embarcado real: coeso, local, tolerante a falhas e fácil de evoluir.

## Design patterns utilizados

### 1. Strategy

Aplicado em `SoftwareNavegacao` e no tratamento de exceções do `SRI`.

- Permite alternar algoritmos por modelo de parâmetros (`ARIANE_4` e `ARIANE_5`);
- evita condicionais gigantes espalhadas pelo sistema;
- facilita simular o erro histórico de reutilização inadequada de parâmetros.

### 2. Observer / Event Bus

Aplicado em `SimpleEventBus`.

- Módulos publicam eventos sem conhecer diretamente seus consumidores;
- `SRI`, `OBC`, solo e diagnóstico ficam desacoplados;
- deixa o código mais modular e aderente a um sistema com telemetria e falhas.

### 3. Command

Aplicado em `ComandoTrajetoria`.

- Cada comando de correção encapsula a intenção de atuação;
- o `OBC` decide o que emitir e o `AtuadorMotor` executa;
- melhora extensibilidade para novos tipos de correções.

### 4. Facade

Aplicado em `Ariane5LaunchSimulation`.

- Centraliza a montagem da simulação;
- simplifica a execução do fluxo principal;
- esconde a complexidade de inicialização dos subsistemas.

### 5. Factory

Aplicado em `NavigationAlgorithmFactory`.

- Cria a estratégia correta de navegação conforme o modelo configurado;
- reforça o princípio de responsabilidade única.

## Estrutura

```text
src/ariane5
  app/                -> ponto de entrada
  bus/                -> barramento de eventos
  core/               -> contratos centrais
  domain/             -> entidades, enums e eventos
  interfaces/         -> contratos entre módulos
  modules/
    telemetry/        -> sensores e coleta
    navigation/       -> software de navegação
    safety/           -> SRI e políticas de falha
    obc/              -> computador de bordo
    ground/           -> transmissão para o solo
  simulation/         -> orquestração da simulação
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
- geração e validação de telemetria;
- processamento redundante em dois SRIs;
- erro de overflow ao usar parametros incompativeis;
- entrada do `OBC` em modo seguro;
- envio de diagnósticos e transmissão para a base em solo;
- armamento do módulo de autodestruição quando não há pacote válido.
