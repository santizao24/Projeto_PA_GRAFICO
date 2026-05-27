/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     27/05/2026 11:36:10                          */
/*==============================================================*/


drop table if exists CLIENTE;

drop table if exists EQUIPAMENTO;

drop table if exists FUNCIONARIO;

drop table if exists LOGS;

drop table if exists NOTIFICACAO;

drop table if exists PECA;

drop table if exists REPARACAO;

drop table if exists REPARACAO_PECA;

drop table if exists REPARACAO_REJEITADA;

drop table if exists TESTE_OPERACIONALIDADE;

drop table if exists UTILIZADOR;

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE
(
   U_ID_UTILIZADOR      int not null,
   C_NUM_CONTRIBUINTE   varchar(9) not null UNIQUE,
   NUM_TELEFONE_        varchar(9) not null,
   C_MORADA             varchar(255) not null,
   C_SETOR_ATIVIDADE    varchar(100) not null,
   C_ESCALAO            varchar(1) not null,
   primary key (U_ID_UTILIZADOR)
);

/*==============================================================*/
/* Table: EQUIPAMENTO                                           */
/*==============================================================*/
create table EQUIPAMENTO
(
   E_ID_EQUIPAMENTO     int not null AUTO_INCREMENT,
   U_ID_UTILIZADOR      int not null,
   E_MARCA              varchar(50) not null,
   E_CODIGO_MODELO      varchar(100) not null,
   E_CODIGO_SKU         int not null UNIQUE,
   E_DATA_FABRICO       date not null,
   E_LOTE               varchar(50) not null,
   primary key (E_ID_EQUIPAMENTO)
);

/*==============================================================*/
/* Table: FUNCIONARIO                                           */
/*==============================================================*/
create table FUNCIONARIO
(
   U_ID_UTILIZADOR      int not null,
   C_NUM_CONTRIBUINTE   varchar(9) not null UNIQUE,
   F_NUM_TELEFONE       varchar(9) not null,
   C_MORADA             varchar(255) not null,
   F_ESPECIALIZACAO     int not null,
   F_DATA_INICIO_ATIVIDADE date not null,
   primary key (U_ID_UTILIZADOR)
);

/*==============================================================*/
/* Table: LOGS                                                  */
/*==============================================================*/
create table LOGS
(
   L_ID_LOG             int not null AUTO_INCREMENT,
   L_DATA               date,
   L_HORA               time,
   L_UTILIZADOR         varchar(50),
   L_ACAO               varchar(255),
   primary key (L_ID_LOG)
);

/*==============================================================*/
/* Table: NOTIFICACAO                                           */
/*==============================================================*/
create table NOTIFICACAO
(
   N_ID_NOTIFICACAO     int not null AUTO_INCREMENT,
   U_ID_UTILIZADOR      int not null,
   N_MENSAGEM           varchar(1024) not null,
   N_DATA_CRIACAO       varchar(20) not null,
   N_ESTADO             varchar(20) not null,
   N_CATEGORIA          varchar(30) not null,
   primary key (N_ID_NOTIFICACAO)
);

/*==============================================================*/
/* Table: PECA                                                  */
/*==============================================================*/
create table PECA
(
   P_CODIGO_INTERNO     varchar(50) not null,
   T_P_DESIGNACAO       varchar(100),
   P_FABRICANTE         varchar(100),
   P_QUANTIDADE         int,
   primary key (P_CODIGO_INTERNO)
);

/*==============================================================*/
/* Table: REPARACAO                                             */
/*==============================================================*/
create table REPARACAO
(
   R_ID_REPARACAO       int not null AUTO_INCREMENT,
   E_ID_EQUIPAMENTO     int not null,
   U_ID_UTILIZADOR      int null,
   R_NUM_REPARACAO      varchar(50) not null UNIQUE,
   R_DATA_CRIACAO       datetime not null,
   R_DATA_FIM_REPARACAO datetime,
   R_TEMPO_DECORRIDO    int,
   R_CUSTO              decimal,
   R_ESTADO             varchar(15) not null,
   R_OBSERVACOES        text,
   R_DATA_INICIO        datetime,
   primary key (R_ID_REPARACAO)
);

/*==============================================================*/
/* Table: REPARACAO_PECA                                        */
/*==============================================================*/
create table REPARACAO_PECA
(
   R_ID_REPARACAO       int not null,
   P_CODIGO_INTERNO     varchar(50) not null,
   RP_QUANTIDADE_USADA  int,
   primary key (R_ID_REPARACAO, P_CODIGO_INTERNO)
);

/*==============================================================*/
/* Table: REPARACAO_REJEITADA                                   */
/*==============================================================*/
create table REPARACAO_REJEITADA
(
   R_ID_REPARACAO       int not null,
   U_ID_UTILIZADOR      int not null,
   primary key (R_ID_REPARACAO, U_ID_UTILIZADOR)
);

/*==============================================================*/
/* Table: TESTE_OPERACIONALIDADE                                */
/*==============================================================*/
create table TESTE_OPERACIONALIDADE
(
   T_ID_TESTE           int not null AUTO_INCREMENT,
   R_ID_REPARACAO       int not null,
   T_P_DESIGNACAO       varchar(100) not null,
   T_DESCRICAO          text not null,
   T_DATA_REALIZACAO    datetime not null,
   primary key (T_ID_TESTE)
);

/*==============================================================*/
/* Table: UTILIZADOR                                            */
/*==============================================================*/
create table UTILIZADOR
(
   U_ID_UTILIZADOR      int not null AUTO_INCREMENT,
   U_NOME               varchar(100) not null,
   U_USERNAME           varchar(50) not null UNIQUE,
   U_EMAIL              varchar(100) not null UNIQUE,
   U_PASSWORD           varchar(255) not null,
   U_TIPO               varchar(15) not null,
   U_ESTADO             varchar(15),
   U_FOTO               varchar(255),
   primary key (U_ID_UTILIZADOR)
);

alter table CLIENTE add constraint FK_HERANCA2 foreign key (U_ID_UTILIZADOR)
      references UTILIZADOR (U_ID_UTILIZADOR) on delete restrict on update restrict;

alter table EQUIPAMENTO add constraint FK_CLIENTE_EQUIPAMENTO foreign key (U_ID_UTILIZADOR)
      references CLIENTE (U_ID_UTILIZADOR) on delete restrict on update restrict;

alter table FUNCIONARIO add constraint FK_HERANCA foreign key (U_ID_UTILIZADOR)
      references UTILIZADOR (U_ID_UTILIZADOR) on delete restrict on update restrict;

alter table NOTIFICACAO add constraint FK_UTILIZADOR_NOTIFICACAO foreign key (U_ID_UTILIZADOR)
      references UTILIZADOR (U_ID_UTILIZADOR) on delete restrict on update restrict;

alter table REPARACAO add constraint FK_EQUIPAMENTO_REPARACAO foreign key (E_ID_EQUIPAMENTO)
      references EQUIPAMENTO (E_ID_EQUIPAMENTO) on delete restrict on update restrict;

alter table REPARACAO add constraint FK_FUNCIONARIO_REPARACAO foreign key (U_ID_UTILIZADOR)
      references FUNCIONARIO (U_ID_UTILIZADOR) on delete restrict on update restrict;

alter table REPARACAO_PECA add constraint FK_PECA_REPARACAO_PECA foreign key (P_CODIGO_INTERNO)
      references PECA (P_CODIGO_INTERNO) on delete restrict on update restrict;

alter table REPARACAO_PECA add constraint FK_REPARACAO_REPARACAO_PECA foreign key (R_ID_REPARACAO)
      references REPARACAO (R_ID_REPARACAO) on delete restrict on update restrict;

alter table REPARACAO_REJEITADA add constraint FK_REPARACAO_REPARACAO_REJEITADA foreign key (R_ID_REPARACAO)
      references REPARACAO (R_ID_REPARACAO) on delete restrict on update restrict;

alter table REPARACAO_REJEITADA add constraint FK_UTILIZADOR_REPARACAO_REJEITADA foreign key (U_ID_UTILIZADOR)
      references UTILIZADOR (U_ID_UTILIZADOR) on delete restrict on update restrict;

alter table TESTE_OPERACIONALIDADE add constraint FK_REPARACAO_TESTE_OPERACIONALIDADE foreign key (R_ID_REPARACAO)
      references REPARACAO (R_ID_REPARACAO) on delete restrict on update restrict;

