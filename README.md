<h1 align="center">Planilha Financeira</h1>
<h3>Desenvolvido por Danilo Ferreira e <a href="https://github.com/diegods-ferreira/">Diego Ferreira</a></h3>

Este aplicativo foi desenvolvido para uma atividade da disciplina de Engenharia de Software III na Fatec Taubaté-SP.

O programa conta com vários recursos, são eles:
<ul>
  <li>Cadastro de transações de entrada/saída</li>
  <li>Cadastro de contas</li>
  <li>Exibição de relatório resumo</li>
  <li><strong>Observer/Listener</strong> que irá facilitar na atualização automática dos dados</li>
  <li>Algoritmo responsável por <strong>criar o próprio banco de dados MySQL</strong> (a partir de usuário e senha fornecidos pelo usuário)</li>
</ul>

Abaixo você poderá ver algumas images do funcionamento do app,

01 - Ao iniciar o aplicativo, será solicitado ao usuário o USERNAME e PASSWORD do servidor LOCAL MySQL para que ele possa fazer a criação do banco de dados e tabelas.
<img src="https://imgur.com/eYD38VP.gif">


02 - A tela "Sobre" exibe algumas informações sobre o aplicativo e disponibiliza o link do GitHub de seus desenvolvedores.
<img src="https://imgur.com/IwrZywU.gif">


03 - A tela de cadastro de contas permite você criar/atualizar/excluir contas quando quiser, mas atenção, se a conta que quiser excluir possuir transações lançados, o sistema irá alertá-lo.
<img src="https://imgur.com/qR36yhc.gif">


04 - A tela principal é onde você fará o gerenciamento das transações - entrada ou saída - em todas as contas. Nela você poderá fazer o cadastro/atualização/exclusão das mesmas.
<img src="https://imgur.com/nZGhx1R.gif">


05 - Se você já possui transações lançadas em uma conta e atualiza o nome da mesma, o Observer/Listener cuidará para atualizar automaticamente os dados do grid da tela principal.
<img src="https://imgur.com/pjFWMEC.gif">


06 - Por fim, o usuário tem a possibilidade de exibir um simples de relatório contendo o resumo dos valores lançados em cada um das contas cadastradas.
<img src="https://imgur.com/yGlRdDI.gif">


<strong>Espero que goste!</strong>
