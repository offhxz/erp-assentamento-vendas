--
-- PostgreSQL database dump
--

\restrict 5bBjahkqJIa2TwZPh2j65653asng518cmdMvPRCz7uJeTYhzUSvsPRfbdMC3NtX

-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2025-11-17 18:41:45

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 257 (class 1259 OID 16559)
-- Name: area; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.area (
    id bigint NOT NULL,
    associado_id bigint,
    nome character varying(255),
    area_total numeric,
    area_utilizada numeric,
    ph numeric
);


--
-- TOC entry 256 (class 1259 OID 16558)
-- Name: area_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.area_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5395 (class 0 OID 0)
-- Dependencies: 256
-- Name: area_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.area_id_seq OWNED BY public.area.id;


--
-- TOC entry 277 (class 1259 OID 16655)
-- Name: armazem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.armazem (
    id bigint NOT NULL,
    nome character varying(100),
    endereco_id bigint
);


--
-- TOC entry 276 (class 1259 OID 16654)
-- Name: armazem_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.armazem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5396 (class 0 OID 0)
-- Dependencies: 276
-- Name: armazem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.armazem_id_seq OWNED BY public.armazem.id;


--
-- TOC entry 226 (class 1259 OID 16418)
-- Name: associado; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.associado (
    id bigint NOT NULL,
    endereco_id bigint,
    cnpj character varying(18) NOT NULL,
    razao_social character varying(255) NOT NULL,
    nome_fantasia character varying(255) NOT NULL,
    inscricao_estadual character varying(20),
    inscricao_municipal character varying(20),
    telefone character varying(20),
    email character varying(255),
    data_cadastrado timestamp without time zone NOT NULL,
    ativo boolean NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16417)
-- Name: associado_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.associado_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5397 (class 0 OID 0)
-- Dependencies: 225
-- Name: associado_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.associado_id_seq OWNED BY public.associado.id;


--
-- TOC entry 246 (class 1259 OID 16512)
-- Name: associado_item_pedido; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.associado_item_pedido (
    id bigint NOT NULL,
    associado_id bigint,
    item_pedido_id bigint,
    quantidade_atribuida numeric
);


--
-- TOC entry 245 (class 1259 OID 16511)
-- Name: associado_item_pedido_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.associado_item_pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5398 (class 0 OID 0)
-- Dependencies: 245
-- Name: associado_item_pedido_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.associado_item_pedido_id_seq OWNED BY public.associado_item_pedido.id;


--
-- TOC entry 265 (class 1259 OID 16599)
-- Name: atividade; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.atividade (
    id bigint NOT NULL,
    nome_atividade character varying(255),
    descricao text,
    observacoes text,
    status character varying(50)
);


--
-- TOC entry 266 (class 1259 OID 16608)
-- Name: atividade_canteiro; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.atividade_canteiro (
    canteiro_id bigint NOT NULL,
    atividade_id bigint NOT NULL,
    tempo_gasto_horas numeric,
    data_atividade timestamp without time zone
);


--
-- TOC entry 269 (class 1259 OID 16623)
-- Name: atividade_has_material; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.atividade_has_material (
    material_id bigint NOT NULL,
    atividade_id bigint NOT NULL,
    quantidade_utilizada numeric
);


--
-- TOC entry 264 (class 1259 OID 16598)
-- Name: atividade_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.atividade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5399 (class 0 OID 0)
-- Dependencies: 264
-- Name: atividade_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.atividade_id_seq OWNED BY public.atividade.id;


--
-- TOC entry 263 (class 1259 OID 16589)
-- Name: canteiro; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.canteiro (
    id bigint NOT NULL,
    plano_id bigint,
    nome character varying(255),
    area_canteiro_m2 numeric,
    observacoes text,
    kg_gerados numeric
);


--
-- TOC entry 262 (class 1259 OID 16588)
-- Name: canteiro_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.canteiro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5400 (class 0 OID 0)
-- Dependencies: 262
-- Name: canteiro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.canteiro_id_seq OWNED BY public.canteiro.id;


--
-- TOC entry 271 (class 1259 OID 16629)
-- Name: categoria; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.categoria (
    id bigint NOT NULL,
    nome character varying(100)
);


--
-- TOC entry 270 (class 1259 OID 16628)
-- Name: categoria_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.categoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5401 (class 0 OID 0)
-- Dependencies: 270
-- Name: categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.categoria_id_seq OWNED BY public.categoria.id;


--
-- TOC entry 224 (class 1259 OID 16408)
-- Name: cliente; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.cliente (
    id bigint NOT NULL,
    endereco_id bigint,
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL,
    telefone character varying(20) NOT NULL,
    email character varying(255) NOT NULL,
    data_cadastro timestamp without time zone NOT NULL,
    ativo boolean NOT NULL,
    cpf_cnpj character varying(20) NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 16407)
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.cliente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5402 (class 0 OID 0)
-- Dependencies: 223
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id;


--
-- TOC entry 220 (class 1259 OID 16390)
-- Name: despesa; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.despesa (
    id bigint NOT NULL,
    associado_id bigint,
    categoria_gasto character varying(100) NOT NULL,
    destinatario character varying(100) NOT NULL,
    valor_gasto numeric(15,2) NOT NULL,
    data_transacao timestamp without time zone NOT NULL,
    descricao_despesa character varying(255)
);


--
-- TOC entry 219 (class 1259 OID 16389)
-- Name: despesa_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.despesa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5403 (class 0 OID 0)
-- Dependencies: 219
-- Name: despesa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.despesa_id_seq OWNED BY public.despesa.id;


--
-- TOC entry 222 (class 1259 OID 16398)
-- Name: endereco; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.endereco (
    id bigint NOT NULL,
    estado character varying(2) NOT NULL,
    cidade character varying(255) NOT NULL,
    bairro character varying(255),
    rua character varying(255),
    numero integer,
    cep character varying(8) NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16397)
-- Name: endereco_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.endereco_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5404 (class 0 OID 0)
-- Dependencies: 221
-- Name: endereco_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.endereco_id_seq OWNED BY public.endereco.id;


--
-- TOC entry 273 (class 1259 OID 16637)
-- Name: especie; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.especie (
    id bigint NOT NULL,
    categoria_id bigint,
    nome character varying(100),
    descricao character varying(255),
    tempo_colheita integer,
    rendimento_kg_m2 numeric
);


--
-- TOC entry 272 (class 1259 OID 16636)
-- Name: especie_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.especie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5405 (class 0 OID 0)
-- Dependencies: 272
-- Name: especie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.especie_id_seq OWNED BY public.especie.id;


--
-- TOC entry 279 (class 1259 OID 16663)
-- Name: estoque_atual; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.estoque_atual (
    id bigint NOT NULL,
    associado_id bigint,
    produto_id bigint,
    armazem_id bigint,
    quantidade numeric(10,2)
);


--
-- TOC entry 278 (class 1259 OID 16662)
-- Name: estoque_atual_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.estoque_atual_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5406 (class 0 OID 0)
-- Dependencies: 278
-- Name: estoque_atual_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.estoque_atual_id_seq OWNED BY public.estoque_atual.id;


--
-- TOC entry 242 (class 1259 OID 16494)
-- Name: forma_pagamento; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.forma_pagamento (
    id bigint NOT NULL,
    descricao character varying(100)
);


--
-- TOC entry 241 (class 1259 OID 16493)
-- Name: forma_pagamento_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.forma_pagamento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5407 (class 0 OID 0)
-- Dependencies: 241
-- Name: forma_pagamento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.forma_pagamento_id_seq OWNED BY public.forma_pagamento.id;


--
-- TOC entry 244 (class 1259 OID 16502)
-- Name: item_pedido; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.item_pedido (
    id bigint NOT NULL,
    pedido_id bigint,
    produto_id bigint,
    quantidade_total numeric,
    valor_unitario numeric,
    valor_total numeric
);


--
-- TOC entry 243 (class 1259 OID 16501)
-- Name: item_pedido_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.item_pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5408 (class 0 OID 0)
-- Dependencies: 243
-- Name: item_pedido_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.item_pedido_id_seq OWNED BY public.item_pedido.id;


--
-- TOC entry 268 (class 1259 OID 16614)
-- Name: material; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.material (
    id bigint NOT NULL,
    associado_id bigint,
    nome character varying(255),
    quantidade numeric,
    unidade_medida character varying(50)
);


--
-- TOC entry 267 (class 1259 OID 16613)
-- Name: material_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.material_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5409 (class 0 OID 0)
-- Dependencies: 267
-- Name: material_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.material_id_seq OWNED BY public.material.id;


--
-- TOC entry 287 (class 1259 OID 16695)
-- Name: movimentacao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.movimentacao (
    id bigint NOT NULL,
    tipo_id bigint,
    origem_id bigint,
    produto_id bigint,
    armazem_id bigint,
    quantidade numeric(10,2),
    data_movimento timestamp without time zone
);


--
-- TOC entry 286 (class 1259 OID 16694)
-- Name: movimentacao_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.movimentacao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5410 (class 0 OID 0)
-- Dependencies: 286
-- Name: movimentacao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.movimentacao_id_seq OWNED BY public.movimentacao.id;


--
-- TOC entry 228 (class 1259 OID 16428)
-- Name: nota_fiscal_eletronica; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nota_fiscal_eletronica (
    id bigint NOT NULL,
    associado_id bigint NOT NULL,
    chave_acesso character varying(44) NOT NULL,
    razao_social character varying(255) NOT NULL,
    data_emissao timestamp without time zone NOT NULL,
    valor_total numeric(15,2) NOT NULL,
    tipo_ambiente smallint NOT NULL,
    tipo_operacao smallint NOT NULL,
    tipo_forma_emissao smallint NOT NULL,
    tipo_status_envio_sefaz smallint NOT NULL,
    numero_protocolo smallint,
    data_inclusao timestamp without time zone NOT NULL,
    ativo boolean NOT NULL,
    numero_nota_fiscal character varying(6) NOT NULL,
    numero_serie character varying(3) NOT NULL,
    dados_adicionais text,
    valor_frete numeric(15,2) NOT NULL
);


--
-- TOC entry 227 (class 1259 OID 16427)
-- Name: nota_fiscal_eletronica_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.nota_fiscal_eletronica_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5411 (class 0 OID 0)
-- Dependencies: 227
-- Name: nota_fiscal_eletronica_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.nota_fiscal_eletronica_id_seq OWNED BY public.nota_fiscal_eletronica.id;


--
-- TOC entry 230 (class 1259 OID 16438)
-- Name: nota_fiscal_item; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nota_fiscal_item (
    id bigint NOT NULL,
    nota_fiscal_eletronica_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    cfop character varying(4) NOT NULL,
    ncm character varying(8) NOT NULL,
    quantidade integer NOT NULL,
    valor_unitario numeric(15,2) NOT NULL,
    valor_total numeric(15,2) NOT NULL
);


--
-- TOC entry 229 (class 1259 OID 16437)
-- Name: nota_fiscal_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.nota_fiscal_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5412 (class 0 OID 0)
-- Dependencies: 229
-- Name: nota_fiscal_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.nota_fiscal_item_id_seq OWNED BY public.nota_fiscal_item.id;


--
-- TOC entry 232 (class 1259 OID 16446)
-- Name: nota_fiscal_xml; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.nota_fiscal_xml (
    id bigint NOT NULL,
    hash text NOT NULL,
    conteudo text NOT NULL
);


--
-- TOC entry 231 (class 1259 OID 16445)
-- Name: nota_fiscal_xml_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.nota_fiscal_xml_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5413 (class 0 OID 0)
-- Dependencies: 231
-- Name: nota_fiscal_xml_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.nota_fiscal_xml_id_seq OWNED BY public.nota_fiscal_xml.id;


--
-- TOC entry 251 (class 1259 OID 16533)
-- Name: ordem_producao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.ordem_producao (
    id bigint NOT NULL,
    plano_id bigint,
    quantidade integer,
    kg_total numeric
);


--
-- TOC entry 250 (class 1259 OID 16532)
-- Name: ordem_producao_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.ordem_producao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5414 (class 0 OID 0)
-- Dependencies: 250
-- Name: ordem_producao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.ordem_producao_id_seq OWNED BY public.ordem_producao.id;


--
-- TOC entry 285 (class 1259 OID 16687)
-- Name: origem; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.origem (
    id bigint NOT NULL,
    nome character varying(100)
);


--
-- TOC entry 284 (class 1259 OID 16686)
-- Name: origem_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.origem_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5415 (class 0 OID 0)
-- Dependencies: 284
-- Name: origem_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.origem_id_seq OWNED BY public.origem.id;


--
-- TOC entry 236 (class 1259 OID 16466)
-- Name: pedido; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.pedido (
    id bigint NOT NULL,
    projeto_id bigint,
    associado_id bigint,
    data_criacao timestamp without time zone,
    status_pedido_id bigint,
    valor_total numeric
);


--
-- TOC entry 235 (class 1259 OID 16465)
-- Name: pedido_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5416 (class 0 OID 0)
-- Dependencies: 235
-- Name: pedido_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.pedido_id_seq OWNED BY public.pedido.id;


--
-- TOC entry 261 (class 1259 OID 16579)
-- Name: plano; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.plano (
    id bigint NOT NULL,
    especie_id bigint,
    talhao_id bigint,
    nome_plano character varying(255),
    descricao text,
    data_inicio timestamp without time zone,
    data_fim timestamp without time zone,
    observacoes text,
    area_cultivo numeric
);


--
-- TOC entry 248 (class 1259 OID 16522)
-- Name: plano_especie; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.plano_especie (
    id bigint NOT NULL,
    especie_id bigint,
    nome_plano character varying(100)
);


--
-- TOC entry 249 (class 1259 OID 16529)
-- Name: plano_especie_has_atividade; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.plano_especie_has_atividade (
    plano_especie_id bigint,
    atividade_id bigint
);


--
-- TOC entry 247 (class 1259 OID 16521)
-- Name: plano_especie_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.plano_especie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5417 (class 0 OID 0)
-- Dependencies: 247
-- Name: plano_especie_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.plano_especie_id_seq OWNED BY public.plano_especie.id;


--
-- TOC entry 260 (class 1259 OID 16578)
-- Name: plano_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.plano_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5418 (class 0 OID 0)
-- Dependencies: 260
-- Name: plano_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.plano_id_seq OWNED BY public.plano.id;


--
-- TOC entry 281 (class 1259 OID 16671)
-- Name: preco_ppa; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.preco_ppa (
    id bigint NOT NULL,
    data_inicio timestamp without time zone,
    especie_id bigint,
    data_final timestamp without time zone,
    valor numeric(10,2)
);


--
-- TOC entry 280 (class 1259 OID 16670)
-- Name: preco_ppa_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.preco_ppa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5419 (class 0 OID 0)
-- Dependencies: 280
-- Name: preco_ppa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.preco_ppa_id_seq OWNED BY public.preco_ppa.id;


--
-- TOC entry 275 (class 1259 OID 16647)
-- Name: produto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.produto (
    id bigint NOT NULL,
    especie_id bigint,
    nome character varying(100),
    descricao character varying(255)
);


--
-- TOC entry 274 (class 1259 OID 16646)
-- Name: produto_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.produto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5420 (class 0 OID 0)
-- Dependencies: 274
-- Name: produto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.produto_id_seq OWNED BY public.produto.id;


--
-- TOC entry 234 (class 1259 OID 16456)
-- Name: projeto; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.projeto (
    id bigint NOT NULL,
    nome_projeto character varying(200),
    data_criacao timestamp without time zone,
    data_final timestamp without time zone,
    orcamento numeric
);


--
-- TOC entry 233 (class 1259 OID 16455)
-- Name: projeto_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.projeto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5421 (class 0 OID 0)
-- Dependencies: 233
-- Name: projeto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.projeto_id_seq OWNED BY public.projeto.id;


--
-- TOC entry 253 (class 1259 OID 16543)
-- Name: registrar_problema; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.registrar_problema (
    id bigint NOT NULL,
    ordem_producao_id bigint,
    tipo_problema_id bigint,
    quantidade_afetada integer,
    data_problema timestamp without time zone,
    observacoes character varying(255)
);


--
-- TOC entry 252 (class 1259 OID 16542)
-- Name: registrar_problema_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.registrar_problema_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5422 (class 0 OID 0)
-- Dependencies: 252
-- Name: registrar_problema_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.registrar_problema_id_seq OWNED BY public.registrar_problema.id;


--
-- TOC entry 238 (class 1259 OID 16476)
-- Name: status_pedido; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.status_pedido (
    id bigint NOT NULL,
    descricao character varying(100)
);


--
-- TOC entry 237 (class 1259 OID 16475)
-- Name: status_pedido_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.status_pedido_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5423 (class 0 OID 0)
-- Dependencies: 237
-- Name: status_pedido_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.status_pedido_id_seq OWNED BY public.status_pedido.id;


--
-- TOC entry 259 (class 1259 OID 16569)
-- Name: talhao; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.talhao (
    id bigint NOT NULL,
    area_id bigint,
    nome character varying(255),
    area_talhao numeric,
    observacoes text,
    status character varying(50)
);


--
-- TOC entry 258 (class 1259 OID 16568)
-- Name: talhao_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.talhao_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5424 (class 0 OID 0)
-- Dependencies: 258
-- Name: talhao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.talhao_id_seq OWNED BY public.talhao.id;


--
-- TOC entry 283 (class 1259 OID 16679)
-- Name: tipo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tipo (
    id bigint NOT NULL,
    nome character varying(100)
);


--
-- TOC entry 282 (class 1259 OID 16678)
-- Name: tipo_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tipo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5425 (class 0 OID 0)
-- Dependencies: 282
-- Name: tipo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tipo_id_seq OWNED BY public.tipo.id;


--
-- TOC entry 255 (class 1259 OID 16551)
-- Name: tipo_problema; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tipo_problema (
    id bigint NOT NULL,
    descricao character varying(100)
);


--
-- TOC entry 254 (class 1259 OID 16550)
-- Name: tipo_problema_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tipo_problema_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5426 (class 0 OID 0)
-- Dependencies: 254
-- Name: tipo_problema_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tipo_problema_id_seq OWNED BY public.tipo_problema.id;


--
-- TOC entry 240 (class 1259 OID 16484)
-- Name: venda; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.venda (
    id bigint NOT NULL,
    projeto_id bigint,
    associado_id bigint,
    pedido_id bigint,
    data_compra timestamp without time zone,
    valor_total numeric,
    data_entrega timestamp without time zone,
    forma_pagamento_id bigint
);


--
-- TOC entry 239 (class 1259 OID 16483)
-- Name: venda_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.venda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 5427 (class 0 OID 0)
-- Dependencies: 239
-- Name: venda_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.venda_id_seq OWNED BY public.venda.id;


--
-- TOC entry 5046 (class 2604 OID 16562)
-- Name: area id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.area ALTER COLUMN id SET DEFAULT nextval('public.area_id_seq'::regclass);


--
-- TOC entry 5055 (class 2604 OID 16658)
-- Name: armazem id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.armazem ALTER COLUMN id SET DEFAULT nextval('public.armazem_id_seq'::regclass);


--
-- TOC entry 5031 (class 2604 OID 16421)
-- Name: associado id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado ALTER COLUMN id SET DEFAULT nextval('public.associado_id_seq'::regclass);


--
-- TOC entry 5041 (class 2604 OID 16515)
-- Name: associado_item_pedido id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado_item_pedido ALTER COLUMN id SET DEFAULT nextval('public.associado_item_pedido_id_seq'::regclass);


--
-- TOC entry 5050 (class 2604 OID 16602)
-- Name: atividade id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade ALTER COLUMN id SET DEFAULT nextval('public.atividade_id_seq'::regclass);


--
-- TOC entry 5049 (class 2604 OID 16592)
-- Name: canteiro id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.canteiro ALTER COLUMN id SET DEFAULT nextval('public.canteiro_id_seq'::regclass);


--
-- TOC entry 5052 (class 2604 OID 16632)
-- Name: categoria id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id SET DEFAULT nextval('public.categoria_id_seq'::regclass);


--
-- TOC entry 5030 (class 2604 OID 16411)
-- Name: cliente id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id SET DEFAULT nextval('public.cliente_id_seq'::regclass);


--
-- TOC entry 5028 (class 2604 OID 16393)
-- Name: despesa id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.despesa ALTER COLUMN id SET DEFAULT nextval('public.despesa_id_seq'::regclass);


--
-- TOC entry 5029 (class 2604 OID 16401)
-- Name: endereco id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.endereco ALTER COLUMN id SET DEFAULT nextval('public.endereco_id_seq'::regclass);


--
-- TOC entry 5053 (class 2604 OID 16640)
-- Name: especie id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.especie ALTER COLUMN id SET DEFAULT nextval('public.especie_id_seq'::regclass);


--
-- TOC entry 5056 (class 2604 OID 16666)
-- Name: estoque_atual id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.estoque_atual ALTER COLUMN id SET DEFAULT nextval('public.estoque_atual_id_seq'::regclass);


--
-- TOC entry 5039 (class 2604 OID 16497)
-- Name: forma_pagamento id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.forma_pagamento ALTER COLUMN id SET DEFAULT nextval('public.forma_pagamento_id_seq'::regclass);


--
-- TOC entry 5040 (class 2604 OID 16505)
-- Name: item_pedido id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_pedido ALTER COLUMN id SET DEFAULT nextval('public.item_pedido_id_seq'::regclass);


--
-- TOC entry 5051 (class 2604 OID 16617)
-- Name: material id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.material ALTER COLUMN id SET DEFAULT nextval('public.material_id_seq'::regclass);


--
-- TOC entry 5060 (class 2604 OID 16698)
-- Name: movimentacao id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao ALTER COLUMN id SET DEFAULT nextval('public.movimentacao_id_seq'::regclass);


--
-- TOC entry 5032 (class 2604 OID 16431)
-- Name: nota_fiscal_eletronica id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_eletronica ALTER COLUMN id SET DEFAULT nextval('public.nota_fiscal_eletronica_id_seq'::regclass);


--
-- TOC entry 5033 (class 2604 OID 16441)
-- Name: nota_fiscal_item id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_item ALTER COLUMN id SET DEFAULT nextval('public.nota_fiscal_item_id_seq'::regclass);


--
-- TOC entry 5034 (class 2604 OID 16449)
-- Name: nota_fiscal_xml id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_xml ALTER COLUMN id SET DEFAULT nextval('public.nota_fiscal_xml_id_seq'::regclass);


--
-- TOC entry 5043 (class 2604 OID 16536)
-- Name: ordem_producao id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ordem_producao ALTER COLUMN id SET DEFAULT nextval('public.ordem_producao_id_seq'::regclass);


--
-- TOC entry 5059 (class 2604 OID 16690)
-- Name: origem id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.origem ALTER COLUMN id SET DEFAULT nextval('public.origem_id_seq'::regclass);


--
-- TOC entry 5036 (class 2604 OID 16469)
-- Name: pedido id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pedido ALTER COLUMN id SET DEFAULT nextval('public.pedido_id_seq'::regclass);


--
-- TOC entry 5048 (class 2604 OID 16582)
-- Name: plano id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano ALTER COLUMN id SET DEFAULT nextval('public.plano_id_seq'::regclass);


--
-- TOC entry 5042 (class 2604 OID 16525)
-- Name: plano_especie id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano_especie ALTER COLUMN id SET DEFAULT nextval('public.plano_especie_id_seq'::regclass);


--
-- TOC entry 5057 (class 2604 OID 16674)
-- Name: preco_ppa id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.preco_ppa ALTER COLUMN id SET DEFAULT nextval('public.preco_ppa_id_seq'::regclass);


--
-- TOC entry 5054 (class 2604 OID 16650)
-- Name: produto id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.produto ALTER COLUMN id SET DEFAULT nextval('public.produto_id_seq'::regclass);


--
-- TOC entry 5035 (class 2604 OID 16459)
-- Name: projeto id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.projeto ALTER COLUMN id SET DEFAULT nextval('public.projeto_id_seq'::regclass);


--
-- TOC entry 5044 (class 2604 OID 16546)
-- Name: registrar_problema id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.registrar_problema ALTER COLUMN id SET DEFAULT nextval('public.registrar_problema_id_seq'::regclass);


--
-- TOC entry 5037 (class 2604 OID 16479)
-- Name: status_pedido id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.status_pedido ALTER COLUMN id SET DEFAULT nextval('public.status_pedido_id_seq'::regclass);


--
-- TOC entry 5047 (class 2604 OID 16572)
-- Name: talhao id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.talhao ALTER COLUMN id SET DEFAULT nextval('public.talhao_id_seq'::regclass);


--
-- TOC entry 5058 (class 2604 OID 16682)
-- Name: tipo id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipo ALTER COLUMN id SET DEFAULT nextval('public.tipo_id_seq'::regclass);


--
-- TOC entry 5045 (class 2604 OID 16554)
-- Name: tipo_problema id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipo_problema ALTER COLUMN id SET DEFAULT nextval('public.tipo_problema_id_seq'::regclass);


--
-- TOC entry 5038 (class 2604 OID 16487)
-- Name: venda id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda ALTER COLUMN id SET DEFAULT nextval('public.venda_id_seq'::regclass);


--
-- TOC entry 5359 (class 0 OID 16559)
-- Dependencies: 257
-- Data for Name: area; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.area (id, associado_id, nome, area_total, area_utilizada, ph) VALUES (1, 1, 'Fazenda São João', 50.5, 35.2, 6.2);
INSERT INTO public.area (id, associado_id, nome, area_total, area_utilizada, ph) VALUES (2, 1, 'Sítio Esperança', 25.0, 18.7, 5.8);
INSERT INTO public.area (id, associado_id, nome, area_total, area_utilizada, ph) VALUES (3, 2, 'Chácara Boa Vista', 30.0, 22.3, 6.5);
INSERT INTO public.area (id, associado_id, nome, area_total, area_utilizada, ph) VALUES (4, 2, 'Fazenda Verde Vida', 45.2, 30.1, 6.0);
INSERT INTO public.area (id, associado_id, nome, area_total, area_utilizada, ph) VALUES (5, 3, 'Sítio Recanto Feliz', 15.8, 12.5, 5.9);


--
-- TOC entry 5379 (class 0 OID 16655)
-- Dependencies: 277
-- Data for Name: armazem; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.armazem (id, nome, endereco_id) VALUES (1, 'Armazém Central', 1);
INSERT INTO public.armazem (id, nome, endereco_id) VALUES (2, 'Galpão Refrigerado', 2);
INSERT INTO public.armazem (id, nome, endereco_id) VALUES (3, 'Estoque Sítio Boa Esperança', 3);
INSERT INTO public.armazem (id, nome, endereco_id) VALUES (4, 'Ponto de Coleta Sumaré', 4);
INSERT INTO public.armazem (id, nome, endereco_id) VALUES (5, 'Caixa de Campo 1', 5);


--
-- TOC entry 5328 (class 0 OID 16418)
-- Dependencies: 226
-- Data for Name: associado; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.associado (id, endereco_id, cnpj, razao_social, nome_fantasia, inscricao_estadual, inscricao_municipal, telefone, email, data_cadastrado, ativo) VALUES (1, 2, '12.345.678/0001-01', 'João da Silva', 'Sítio Terra Livre', '123456789', '321654', '(19)98888-0001', 'joao.terra@mst.org', '2025-11-17 17:30:53.342319', true);
INSERT INTO public.associado (id, endereco_id, cnpj, razao_social, nome_fantasia, inscricao_estadual, inscricao_municipal, telefone, email, data_cadastrado, ativo) VALUES (2, 3, '23.456.789/0001-02', 'Maria Oliveira', 'Chácara Raízes do Campo', '987654321', '654321', '(19)98888-0002', 'maria.raizes@mst.org', '2025-11-17 17:30:53.342319', true);
INSERT INTO public.associado (id, endereco_id, cnpj, razao_social, nome_fantasia, inscricao_estadual, inscricao_municipal, telefone, email, data_cadastrado, ativo) VALUES (3, 5, '34.567.890/0001-03', 'Carlos Souza', 'Sítio Boa Colheita', '456789123', '852963', '(19)98888-0003', 'carlos.colheita@mst.org', '2025-11-17 17:30:53.342319', true);
INSERT INTO public.associado (id, endereco_id, cnpj, razao_social, nome_fantasia, inscricao_estadual, inscricao_municipal, telefone, email, data_cadastrado, ativo) VALUES (4, 2, '45.678.901/0001-04', 'Ana Santos', 'Fazendinha Nova Vida', '741852963', '147258', '(19)98888-0004', 'ana.novavida@mst.org', '2025-11-17 17:30:53.342319', true);
INSERT INTO public.associado (id, endereco_id, cnpj, razao_social, nome_fantasia, inscricao_estadual, inscricao_municipal, telefone, email, data_cadastrado, ativo) VALUES (5, 1, '56.789.012/0001-05', 'Paulo Lima', 'Sítio Esperança Viva', '369258147', '369147', '(19)98888-0005', 'paulo.esperanca@mst.org', '2025-11-17 17:30:53.342319', true);


--
-- TOC entry 5348 (class 0 OID 16512)
-- Dependencies: 246
-- Data for Name: associado_item_pedido; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (1, 1, 1, 2);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (2, 2, 2, 1);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (3, 1, 3, 1);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (4, 3, 4, 1);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (5, 4, 5, 1);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (6, 1, 6, 3);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (7, 5, 7, 1);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (8, 2, 8, 2);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (9, 1, 9, 1);
INSERT INTO public.associado_item_pedido (id, associado_id, item_pedido_id, quantidade_atribuida) VALUES (10, 1, 10, 2);


--
-- TOC entry 5367 (class 0 OID 16599)
-- Dependencies: 265
-- Data for Name: atividade; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.atividade (id, nome_atividade, descricao, observacoes, status) VALUES (1, 'Preparação do Solo', 'Revolvimento e adubação do solo', 'Usar trator para aração', 'Concluída');
INSERT INTO public.atividade (id, nome_atividade, descricao, observacoes, status) VALUES (2, 'Plantio', 'Plantio das mudas no canteiro', 'Espaçamento de 30cm', 'Concluída');
INSERT INTO public.atividade (id, nome_atividade, descricao, observacoes, status) VALUES (3, 'Irrigação', 'Sistema de irrigação por gotejamento', 'Verificar vazamentos', 'Em Andamento');
INSERT INTO public.atividade (id, nome_atividade, descricao, observacoes, status) VALUES (4, 'Adubação', 'Aplicação de adubo orgânico', 'Usar esterco curtido', 'Pendente');
INSERT INTO public.atividade (id, nome_atividade, descricao, observacoes, status) VALUES (5, 'Colheita', 'Colheita manual dos produtos', 'Realizar pela manhã', 'Pendente');


--
-- TOC entry 5368 (class 0 OID 16608)
-- Dependencies: 266
-- Data for Name: atividade_canteiro; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.atividade_canteiro (canteiro_id, atividade_id, tempo_gasto_horas, data_atividade) VALUES (1, 1, 8.5, '2024-09-01 00:00:00');
INSERT INTO public.atividade_canteiro (canteiro_id, atividade_id, tempo_gasto_horas, data_atividade) VALUES (1, 2, 6.0, '2024-09-02 00:00:00');
INSERT INTO public.atividade_canteiro (canteiro_id, atividade_id, tempo_gasto_horas, data_atividade) VALUES (2, 1, 7.0, '2024-09-01 00:00:00');
INSERT INTO public.atividade_canteiro (canteiro_id, atividade_id, tempo_gasto_horas, data_atividade) VALUES (3, 3, 12.5, '2024-09-05 00:00:00');
INSERT INTO public.atividade_canteiro (canteiro_id, atividade_id, tempo_gasto_horas, data_atividade) VALUES (4, 2, 5.5, '2024-08-20 00:00:00');


--
-- TOC entry 5371 (class 0 OID 16623)
-- Dependencies: 269
-- Data for Name: atividade_has_material; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.atividade_has_material (material_id, atividade_id, quantidade_utilizada) VALUES (1, 1, 50.0);
INSERT INTO public.atividade_has_material (material_id, atividade_id, quantidade_utilizada) VALUES (2, 2, 0.5);
INSERT INTO public.atividade_has_material (material_id, atividade_id, quantidade_utilizada) VALUES (3, 3, 25.0);
INSERT INTO public.atividade_has_material (material_id, atividade_id, quantidade_utilizada) VALUES (4, 4, 15.0);
INSERT INTO public.atividade_has_material (material_id, atividade_id, quantidade_utilizada) VALUES (1, 4, 30.0);


--
-- TOC entry 5365 (class 0 OID 16589)
-- Dependencies: 263
-- Data for Name: canteiro; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.canteiro (id, plano_id, nome, area_canteiro_m2, observacoes, kg_gerados) VALUES (1, 1, 'Canteiro Alface 1', 120, 'Canteiro sombreado', 280.5);
INSERT INTO public.canteiro (id, plano_id, nome, area_canteiro_m2, observacoes, kg_gerados) VALUES (2, 1, 'Canteiro Alface 2', 100, 'Canteiro ensolarado', 240.2);
INSERT INTO public.canteiro (id, plano_id, nome, area_canteiro_m2, observacoes, kg_gerados) VALUES (3, 2, 'Canteiro Rúcula 1', 80, 'Irrigação automática', 132.8);
INSERT INTO public.canteiro (id, plano_id, nome, area_canteiro_m2, observacoes, kg_gerados) VALUES (4, 3, 'Canteiro Cenoura 1', 150, 'Solo profundo', 450.0);
INSERT INTO public.canteiro (id, plano_id, nome, area_canteiro_m2, observacoes, kg_gerados) VALUES (5, 4, 'Canteiro Beterraba 1', 130, 'Protegido do vento', 345.6);


--
-- TOC entry 5373 (class 0 OID 16629)
-- Dependencies: 271
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.categoria (id, nome) VALUES (1, 'Verdura');
INSERT INTO public.categoria (id, nome) VALUES (2, 'Legume');
INSERT INTO public.categoria (id, nome) VALUES (3, 'Fruta');
INSERT INTO public.categoria (id, nome) VALUES (4, 'Tempero');
INSERT INTO public.categoria (id, nome) VALUES (5, 'Raiz');


--
-- TOC entry 5326 (class 0 OID 16408)
-- Dependencies: 224
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.cliente (id, endereco_id, nome_fantasia, razao_social, telefone, email, data_cadastro, ativo, cpf_cnpj) VALUES (1, 1, 'Mercado Central', 'Mercado Central de Campinas LTDA', '(19)3232-1111', 'mercado@central.com', '2025-11-17 17:30:53.342319', true, '12.345.678/0001-00');
INSERT INTO public.cliente (id, endereco_id, nome_fantasia, razao_social, telefone, email, data_cadastro, ativo, cpf_cnpj) VALUES (2, 4, 'Restaurante Sabor da Terra', 'Sabor da Terra Alimentos LTDA', '(19)3333-2222', 'contato@saborterra.com', '2025-11-17 17:30:53.342319', true, '98.765.432/0001-11');
INSERT INTO public.cliente (id, endereco_id, nome_fantasia, razao_social, telefone, email, data_cadastro, ativo, cpf_cnpj) VALUES (3, 2, 'Empório Rural', 'Empório Rural Paulínia LTDA', '(19)3444-3333', 'emporio@rural.com', '2025-11-17 17:30:53.342319', true, '87.654.321/0001-22');
INSERT INTO public.cliente (id, endereco_id, nome_fantasia, razao_social, telefone, email, data_cadastro, ativo, cpf_cnpj) VALUES (4, 1, 'Feira Verde', 'Cooperativa Feira Verde', '(19)3555-4444', 'contato@feiraverde.org', '2025-11-17 17:30:53.342319', true, '76.543.210/0001-33');
INSERT INTO public.cliente (id, endereco_id, nome_fantasia, razao_social, telefone, email, data_cadastro, ativo, cpf_cnpj) VALUES (5, 3, 'Casa Natural', 'Casa Natural Alimentos', '(19)3666-5555', 'casa@natural.com', '2025-11-17 17:30:53.342319', true, '65.432.109/0001-44');


--
-- TOC entry 5322 (class 0 OID 16390)
-- Dependencies: 220
-- Data for Name: despesa; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (1, 1, 'outro', 'Posto DieselMax ', 1250.60, '2025-10-01 08:30:00', 'Combustível para trator');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (2, 2, 'produtos', 'AgroInsumos S.A.', 15200.00, '2025-10-01 14:15:00', 'Compra de sementes de milho');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (3, 3, 'outros', 'Contabilidade Rural Ltda', 950.00, '2025-10-02 19:00:00', 'Serviços contábeis ref. Setembro/25');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (4, 1, 'transporte', 'Transportadora GrãoForte', 3800.00, '2025-10-03 12:00:00', 'Frete para escoamento da colheita');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (5, 2, 'produtos', 'Fertilizantes Brasil', 22000.00, '2025-10-04 09:10:00', 'Aquisição de NPK 20-05-20 para cobertura');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (6, 3, 'outros', 'Desktop Internet', 450.00, '2025-10-05 18:00:00', 'Mensalidade internet');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (7, 1, 'transporte', 'Oficina TratorPeças', 1800.00, '2025-10-06 17:45:00', 'Manutenção corretiva Trator');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (8, 2, 'produtos', 'Defensivos Agro', 7300.00, '2025-10-07 11:20:00', 'Compra de herbicida pré-emergente');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (9, 3, 'outros', 'Sindicato Rural', 320.00, '2025-10-08 13:00:00', 'Anuidade associado 2025');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (10, 1, 'transporte', 'SemParar', 245.50, '2025-10-10 07:15:00', 'Pedágios');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (11, 2, 'produtos', 'Casa do Agricultor', 890.00, '2025-10-12 16:30:00', 'Aquisição de ferramentas (pás, enxadas)');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (12, 3, 'outros', 'CPFL Energia', 1400.80, '2025-10-14 10:00:00', 'Fatura de energia');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (13, 1, 'transporte', 'Posto DieselMax', 980.00, '2025-10-15 08:00:00', 'Diesel para caminhão');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (14, 2, 'produtos', 'IrrigaTech Soluções', 4500.00, '2025-10-18 09:00:00', 'Compra de gotejadores e conexões');
INSERT INTO public.despesa (id, associado_id, categoria_gasto, destinatario, valor_gasto, data_transacao, descricao_despesa) VALUES (15, 3, 'produtos', 'AgroInsumos S.A.', 8000.00, '2025-10-20 00:00:00', 'Compra de sementes de tomate');


--
-- TOC entry 5324 (class 0 OID 16398)
-- Dependencies: 222
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.endereco (id, estado, cidade, bairro, rua, numero, cep) VALUES (1, 'SP', 'Campinas', 'Centro', 'Rua das Palmeiras', 100, '13010000');
INSERT INTO public.endereco (id, estado, cidade, bairro, rua, numero, cep) VALUES (2, 'SP', 'Sumaré', 'Zona Rural', 'Estrada Terra Livre', 0, '13170000');
INSERT INTO public.endereco (id, estado, cidade, bairro, rua, numero, cep) VALUES (3, 'SP', 'Paulínia', 'Assentamento Esperança', 'Rua do Campo', 12, '13140000');
INSERT INTO public.endereco (id, estado, cidade, bairro, rua, numero, cep) VALUES (4, 'SP', 'Hortolândia', 'Jardim Verde', 'Rua das Flores', 56, '13184000');
INSERT INTO public.endereco (id, estado, cidade, bairro, rua, numero, cep) VALUES (5, 'SP', 'Americana', 'Zona Rural', 'Sítio Boa Vista', 0, '13470000');


--
-- TOC entry 5375 (class 0 OID 16637)
-- Dependencies: 273
-- Data for Name: especie; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.especie (id, categoria_id, nome, descricao, tempo_colheita, rendimento_kg_m2) VALUES (10, 1, 'Alface Crespa', 'Alface verde tradicional', 45, 1.5);
INSERT INTO public.especie (id, categoria_id, nome, descricao, tempo_colheita, rendimento_kg_m2) VALUES (11, 2, 'Tomate Italiano', 'Ideal para molhos', 90, 4.0);
INSERT INTO public.especie (id, categoria_id, nome, descricao, tempo_colheita, rendimento_kg_m2) VALUES (12, 3, 'Limão Tahiti', 'Limão comum sem semente', 120, 5.0);
INSERT INTO public.especie (id, categoria_id, nome, descricao, tempo_colheita, rendimento_kg_m2) VALUES (13, 5, 'Mandioca', 'Mandioca amarela (Aipim)', 240, 3.0);
INSERT INTO public.especie (id, categoria_id, nome, descricao, tempo_colheita, rendimento_kg_m2) VALUES (14, 4, 'Manjericão', 'Tempero fresco', 60, 0.8);


--
-- TOC entry 5381 (class 0 OID 16663)
-- Dependencies: 279
-- Data for Name: estoque_atual; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.estoque_atual (id, associado_id, produto_id, armazem_id, quantidade) VALUES (1, 1, 100, 1, 120.00);
INSERT INTO public.estoque_atual (id, associado_id, produto_id, armazem_id, quantidade) VALUES (2, 2, 101, 2, 70.00);
INSERT INTO public.estoque_atual (id, associado_id, produto_id, armazem_id, quantidade) VALUES (3, 1, 102, 1, 50.00);
INSERT INTO public.estoque_atual (id, associado_id, produto_id, armazem_id, quantidade) VALUES (4, 3, 103, 2, 200.00);
INSERT INTO public.estoque_atual (id, associado_id, produto_id, armazem_id, quantidade) VALUES (5, 4, 104, 5, 40.00);


--
-- TOC entry 5344 (class 0 OID 16494)
-- Dependencies: 242
-- Data for Name: forma_pagamento; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.forma_pagamento (id, descricao) VALUES (1, 'Débito');
INSERT INTO public.forma_pagamento (id, descricao) VALUES (2, 'Crédito');
INSERT INTO public.forma_pagamento (id, descricao) VALUES (3, 'Pix');
INSERT INTO public.forma_pagamento (id, descricao) VALUES (4, 'Boleto');
INSERT INTO public.forma_pagamento (id, descricao) VALUES (5, 'Outro');


--
-- TOC entry 5346 (class 0 OID 16502)
-- Dependencies: 244
-- Data for Name: item_pedido; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (1, 1, 100, 2, 60.00, 120.00);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (2, 2, 101, 1, 75.50, 75.50);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (3, 3, 102, 3, 70.33, 210.99);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (4, 4, 102, 1, 49.90, 49.90);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (5, 5, 101, 1, 60.00, 60.00);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (6, 6, 100, 5, 66.00, 330.00);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (7, 7, 103, 1, 85.00, 85.00);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (8, 8, 104, 3, 50.00, 150.00);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (9, 9, 100, 2, 49.95, 99.90);
INSERT INTO public.item_pedido (id, pedido_id, produto_id, quantidade_total, valor_unitario, valor_total) VALUES (10, 10, 101, 2, 99.95, 199.90);


--
-- TOC entry 5370 (class 0 OID 16614)
-- Dependencies: 268
-- Data for Name: material; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.material (id, associado_id, nome, quantidade, unidade_medida) VALUES (1, 1, 'Adubo Orgânico', 500.0, 'kg');
INSERT INTO public.material (id, associado_id, nome, quantidade, unidade_medida) VALUES (2, 1, 'Sementes de Alface', 2.5, 'kg');
INSERT INTO public.material (id, associado_id, nome, quantidade, unidade_medida) VALUES (3, 1, 'Mangueira Irrigação', 150.0, 'metros');
INSERT INTO public.material (id, associado_id, nome, quantidade, unidade_medida) VALUES (4, 2, 'Fertilizante NPK', 100.0, 'kg');
INSERT INTO public.material (id, associado_id, nome, quantidade, unidade_medida) VALUES (5, 3, 'Calcário Dolomítico', 300.0, 'kg');


--
-- TOC entry 5389 (class 0 OID 16695)
-- Dependencies: 287
-- Data for Name: movimentacao; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.movimentacao (id, tipo_id, origem_id, produto_id, armazem_id, quantidade, data_movimento) VALUES (1, 1, 1, 100, 1, 150.00, '2025-05-10 08:00:00');
INSERT INTO public.movimentacao (id, tipo_id, origem_id, produto_id, armazem_id, quantidade, data_movimento) VALUES (2, 1, 1, 101, 2, 80.00, '2025-05-10 09:00:00');
INSERT INTO public.movimentacao (id, tipo_id, origem_id, produto_id, armazem_id, quantidade, data_movimento) VALUES (3, 2, 2, 100, 3, 30.00, '2025-05-11 10:00:00');
INSERT INTO public.movimentacao (id, tipo_id, origem_id, produto_id, armazem_id, quantidade, data_movimento) VALUES (4, 5, 3, 101, 4, 10.00, '2025-05-11 11:00:00');
INSERT INTO public.movimentacao (id, tipo_id, origem_id, produto_id, armazem_id, quantidade, data_movimento) VALUES (5, 3, 3, 102, 5, 5.00, '2025-05-12 14:00:00');


--
-- TOC entry 5330 (class 0 OID 16428)
-- Dependencies: 228
-- Data for Name: nota_fiscal_eletronica; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.nota_fiscal_eletronica (id, associado_id, chave_acesso, razao_social, data_emissao, valor_total, tipo_ambiente, tipo_operacao, tipo_forma_emissao, tipo_status_envio_sefaz, numero_protocolo, data_inclusao, ativo, numero_nota_fiscal, numero_serie, dados_adicionais, valor_frete) VALUES (1, 1, '35190812345678000101550010000000111111111111', 'João da Silva', '2025-11-17 17:30:53.342319', 150.00, 1, 1, 1, 1, 1001, '2025-11-17 17:30:53.342319', true, '000001', '001', 'Venda local para restaurante', 0.00);
INSERT INTO public.nota_fiscal_eletronica (id, associado_id, chave_acesso, razao_social, data_emissao, valor_total, tipo_ambiente, tipo_operacao, tipo_forma_emissao, tipo_status_envio_sefaz, numero_protocolo, data_inclusao, ativo, numero_nota_fiscal, numero_serie, dados_adicionais, valor_frete) VALUES (2, 2, '35190823456789000102550010000000222222222222', 'Maria Oliveira', '2025-11-17 17:30:53.342319', 250.00, 1, 1, 1, 1, 1002, '2025-11-17 17:30:53.342319', true, '000002', '001', 'Venda orgânica p/ feira', 0.00);
INSERT INTO public.nota_fiscal_eletronica (id, associado_id, chave_acesso, razao_social, data_emissao, valor_total, tipo_ambiente, tipo_operacao, tipo_forma_emissao, tipo_status_envio_sefaz, numero_protocolo, data_inclusao, ativo, numero_nota_fiscal, numero_serie, dados_adicionais, valor_frete) VALUES (3, 3, '35190834567890000103550010000000333333333333', 'Carlos Souza', '2025-11-17 17:30:53.342319', 180.00, 1, 1, 1, 1, 1003, '2025-11-17 17:30:53.342319', true, '000003', '001', 'Venda de feira', 0.00);
INSERT INTO public.nota_fiscal_eletronica (id, associado_id, chave_acesso, razao_social, data_emissao, valor_total, tipo_ambiente, tipo_operacao, tipo_forma_emissao, tipo_status_envio_sefaz, numero_protocolo, data_inclusao, ativo, numero_nota_fiscal, numero_serie, dados_adicionais, valor_frete) VALUES (4, 4, '35190845678901000104550010000000444444444444', 'Ana Santos', '2025-11-17 17:30:53.342319', 220.00, 1, 1, 1, 1, 1004, '2025-11-17 17:30:53.342319', true, '000004', '001', 'Venda direta em feira', 0.00);
INSERT INTO public.nota_fiscal_eletronica (id, associado_id, chave_acesso, razao_social, data_emissao, valor_total, tipo_ambiente, tipo_operacao, tipo_forma_emissao, tipo_status_envio_sefaz, numero_protocolo, data_inclusao, ativo, numero_nota_fiscal, numero_serie, dados_adicionais, valor_frete) VALUES (5, 5, '35190856789012000105550010000000555555555555', 'Paulo Lima', '2025-11-17 17:30:53.342319', 310.00, 1, 1, 1, 1, 1005, '2025-11-17 17:30:53.342319', true, '000005', '001', 'Venda com entrega', 15.00);


--
-- TOC entry 5332 (class 0 OID 16438)
-- Dependencies: 230
-- Data for Name: nota_fiscal_item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.nota_fiscal_item (id, nota_fiscal_eletronica_id, produto_id, cfop, ncm, quantidade, valor_unitario, valor_total) VALUES (1, 3, 101, '5101', '07149000', 15, 4.00, 60.00);
INSERT INTO public.nota_fiscal_item (id, nota_fiscal_eletronica_id, produto_id, cfop, ncm, quantidade, valor_unitario, valor_total) VALUES (2, 4, 102, '5101', '04079000', 10, 15.00, 150.00);
INSERT INTO public.nota_fiscal_item (id, nota_fiscal_eletronica_id, produto_id, cfop, ncm, quantidade, valor_unitario, valor_total) VALUES (3, 4, 103, '5101', '07133319', 12, 8.00, 96.00);
INSERT INTO public.nota_fiscal_item (id, nota_fiscal_eletronica_id, produto_id, cfop, ncm, quantidade, valor_unitario, valor_total) VALUES (4, 5, 104, '5101', '10059010', 20, 2.00, 40.00);
INSERT INTO public.nota_fiscal_item (id, nota_fiscal_eletronica_id, produto_id, cfop, ncm, quantidade, valor_unitario, valor_total) VALUES (5, 5, 100, '5101', '07149090', 10, 5.00, 50.00);


--
-- TOC entry 5334 (class 0 OID 16446)
-- Dependencies: 232
-- Data for Name: nota_fiscal_xml; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.nota_fiscal_xml (id, hash, conteudo) VALUES (1, 'hash_001', '<xml>Nota 1</xml>');
INSERT INTO public.nota_fiscal_xml (id, hash, conteudo) VALUES (2, 'hash_002', '<xml>Nota 2</xml>');
INSERT INTO public.nota_fiscal_xml (id, hash, conteudo) VALUES (3, 'hash_003', '<xml>Nota 3</xml>');
INSERT INTO public.nota_fiscal_xml (id, hash, conteudo) VALUES (4, 'hash_004', '<xml>Nota 4</xml>');
INSERT INTO public.nota_fiscal_xml (id, hash, conteudo) VALUES (5, 'hash_005', '<xml>Nota 5</xml>');


--
-- TOC entry 5353 (class 0 OID 16533)
-- Dependencies: 251
-- Data for Name: ordem_producao; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.ordem_producao (id, plano_id, quantidade, kg_total) VALUES (1, 1, 100, 25.5);
INSERT INTO public.ordem_producao (id, plano_id, quantidade, kg_total) VALUES (2, 2, 200, 48.2);
INSERT INTO public.ordem_producao (id, plano_id, quantidade, kg_total) VALUES (3, 3, 150, 33.4);
INSERT INTO public.ordem_producao (id, plano_id, quantidade, kg_total) VALUES (4, 4, 300, 65.0);
INSERT INTO public.ordem_producao (id, plano_id, quantidade, kg_total) VALUES (5, 5, 120, 27.8);


--
-- TOC entry 5387 (class 0 OID 16687)
-- Dependencies: 285
-- Data for Name: origem; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.origem (id, nome) VALUES (1, 'Módulo de Produção (Colheita)');
INSERT INTO public.origem (id, nome) VALUES (2, 'Módulo de Vendas');
INSERT INTO public.origem (id, nome) VALUES (3, 'Tela de Inventário (Ajustes/Perdas)');
INSERT INTO public.origem (id, nome) VALUES (4, 'Módulo de Compras (Entrada NF)');
INSERT INTO public.origem (id, nome) VALUES (5, 'Sistema (Processo Automático)');


--
-- TOC entry 5338 (class 0 OID 16466)
-- Dependencies: 236
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (1, 1, 1, '2025-07-01 09:00:00', 1, 120.00);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (2, 1, 2, '2025-07-02 10:10:00', 1, 75.50);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (3, 2, 1, '2025-07-05 14:25:00', 2, 210.99);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (4, 2, 3, '2025-07-06 11:05:00', 3, 49.90);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (5, 3, 4, '2025-07-07 16:40:00', 1, 60.00);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (6, 4, 1, '2025-07-08 12:00:00', 2, 330.00);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (7, 5, 5, '2025-07-09 13:15:00', 1, 85.00);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (8, 6, 2, '2025-07-10 08:50:00', 1, 150.00);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (9, 7, 1, '2025-07-11 09:20:00', 2, 99.90);
INSERT INTO public.pedido (id, projeto_id, associado_id, data_criacao, status_pedido_id, valor_total) VALUES (10, 8, 1, '2025-07-12 10:30:00', 1, 199.90);


--
-- TOC entry 5363 (class 0 OID 16579)
-- Dependencies: 261
-- Data for Name: plano; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.plano (id, especie_id, talhao_id, nome_plano, descricao, data_inicio, data_fim, observacoes, area_cultivo) VALUES (1, 10, 1, 'Plano Alface Primavera', 'Cultivo de alface para primavera', '2024-09-01 00:00:00', '2024-11-15 00:00:00', 'Usar adubo orgânico', 5.0);
INSERT INTO public.plano (id, especie_id, talhao_id, nome_plano, descricao, data_inicio, data_fim, observacoes, area_cultivo) VALUES (2, 11, 2, 'Plano Rúcula Verão', 'Cultivo de rúcula para verão', '2024-10-01 00:00:00', '2024-12-10 00:00:00', 'Controlar irrigação', 4.5);
INSERT INTO public.plano (id, especie_id, talhao_id, nome_plano, descricao, data_inicio, data_fim, observacoes, area_cultivo) VALUES (3, 12, 4, 'Plano Cenoura Outono', 'Cultivo de cenoura outonal', '2024-08-15 00:00:00', '2024-10-30 00:00:00', 'Solo precisa correção', 6.0);
INSERT INTO public.plano (id, especie_id, talhao_id, nome_plano, descricao, data_inicio, data_fim, observacoes, area_cultivo) VALUES (4, 13, 5, 'Plano Beterraba Inverno', 'Cultivo de beterraba de inverno', '2024-07-01 00:00:00', '2024-09-20 00:00:00', 'Proteger de geadas', 5.5);
INSERT INTO public.plano (id, especie_id, talhao_id, nome_plano, descricao, data_inicio, data_fim, observacoes, area_cultivo) VALUES (5, 14, 3, 'Plano Espinafre Ano Todo', 'Cultivo contínuo de espinafre', '2024-09-10 00:00:00', '2025-03-10 00:00:00', 'Colheita escalonada', 3.2);


--
-- TOC entry 5350 (class 0 OID 16522)
-- Dependencies: 248
-- Data for Name: plano_especie; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.plano_especie (id, especie_id, nome_plano) VALUES (1, 10, 'Plano Alface');
INSERT INTO public.plano_especie (id, especie_id, nome_plano) VALUES (2, 11, 'Plano Tomate');
INSERT INTO public.plano_especie (id, especie_id, nome_plano) VALUES (3, 12, 'Plano Limão');
INSERT INTO public.plano_especie (id, especie_id, nome_plano) VALUES (4, 13, 'Plano Mandioca');
INSERT INTO public.plano_especie (id, especie_id, nome_plano) VALUES (5, 14, 'Plano Manjericão');


--
-- TOC entry 5351 (class 0 OID 16529)
-- Dependencies: 249
-- Data for Name: plano_especie_has_atividade; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.plano_especie_has_atividade (plano_especie_id, atividade_id) VALUES (1, 1);
INSERT INTO public.plano_especie_has_atividade (plano_especie_id, atividade_id) VALUES (2, 2);
INSERT INTO public.plano_especie_has_atividade (plano_especie_id, atividade_id) VALUES (3, 3);
INSERT INTO public.plano_especie_has_atividade (plano_especie_id, atividade_id) VALUES (4, 4);
INSERT INTO public.plano_especie_has_atividade (plano_especie_id, atividade_id) VALUES (5, 5);


--
-- TOC entry 5383 (class 0 OID 16671)
-- Dependencies: 281
-- Data for Name: preco_ppa; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.preco_ppa (id, data_inicio, especie_id, data_final, valor) VALUES (1, '2025-01-01 00:00:00', 10, '2025-06-30 23:59:59', 3.50);
INSERT INTO public.preco_ppa (id, data_inicio, especie_id, data_final, valor) VALUES (2, '2025-01-01 00:00:00', 11, '2025-06-30 23:59:59', 8.00);
INSERT INTO public.preco_ppa (id, data_inicio, especie_id, data_final, valor) VALUES (3, '2025-01-01 00:00:00', 12, '2025-06-30 23:59:59', 4.00);
INSERT INTO public.preco_ppa (id, data_inicio, especie_id, data_final, valor) VALUES (4, '2025-01-01 00:00:00', 13, '2025-06-30 23:59:59', 6.50);
INSERT INTO public.preco_ppa (id, data_inicio, especie_id, data_final, valor) VALUES (5, '2025-01-01 00:00:00', 14, '2025-06-30 23:59:59', 2.50);


--
-- TOC entry 5377 (class 0 OID 16647)
-- Dependencies: 275
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.produto (id, especie_id, nome, descricao) VALUES (100, 10, 'Alface Crespa (Maço)', 'Maço de Alface com aprox. 200g');
INSERT INTO public.produto (id, especie_id, nome, descricao) VALUES (101, 11, 'Tomate Italiano (Kg)', 'Tomate vendido a granel por peso');
INSERT INTO public.produto (id, especie_id, nome, descricao) VALUES (102, 12, 'Limão Tahiti (Saco 1kg)', 'Pacote de 1kg de limão');
INSERT INTO public.produto (id, especie_id, nome, descricao) VALUES (103, 13, 'Mandioca Descascada (500g)', 'Pacote embalado a vácuo');
INSERT INTO public.produto (id, especie_id, nome, descricao) VALUES (104, 14, 'Manjericão (Molho)', 'Molho de manjericão fresco');


--
-- TOC entry 5336 (class 0 OID 16456)
-- Dependencies: 234
-- Data for Name: projeto; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (1, 'projeto1', '2025-01-10 09:00:00', '2025-12-31 00:00:00', 150000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (2, 'projeto2', '2025-02-05 10:30:00', '2025-11-30 00:00:00', 80000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (3, 'projeto3', '2025-03-15 14:20:00', '2025-09-30 00:00:00', 35000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (4, 'projeto4', '2025-03-20 11:00:00', '2025-08-31 00:00:00', 20000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (5, 'projeto5', '2025-04-01 08:45:00', '2025-12-01 00:00:00', 120000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (6, 'projeto6', '2025-04-10 16:00:00', '2025-10-15 00:00:00', 50000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (7, 'projeto7', '2025-05-02 09:15:00', '2025-10-01 00:00:00', 65000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (8, 'projeto8', '2025-05-22 13:40:00', '2025-11-15 00:00:00', 42000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (9, 'projeto9', '2025-06-05 10:10:00', '2025-12-20 00:00:00', 70000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (10, 'projeto10', '2025-06-18 15:30:00', '2025-10-31 00:00:00', 30000.00);
INSERT INTO public.projeto (id, nome_projeto, data_criacao, data_final, orcamento) VALUES (11, 'INSTITUTO FEDERAL DE SÃO PAULO - HORTOLÂNDIA', '2025-11-17 18:35:12.858', '2027-11-17 18:35:13.182', 1000000);


--
-- TOC entry 5355 (class 0 OID 16543)
-- Dependencies: 253
-- Data for Name: registrar_problema; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.registrar_problema (id, ordem_producao_id, tipo_problema_id, quantidade_afetada, data_problema, observacoes) VALUES (1, 1, 1, 10, '2025-01-10 00:00:00', 'Insetos na plantação');
INSERT INTO public.registrar_problema (id, ordem_producao_id, tipo_problema_id, quantidade_afetada, data_problema, observacoes) VALUES (2, 2, 2, 15, '2025-01-12 00:00:00', 'Folhas amareladas');
INSERT INTO public.registrar_problema (id, ordem_producao_id, tipo_problema_id, quantidade_afetada, data_problema, observacoes) VALUES (3, 3, 3, 8, '2025-01-14 00:00:00', 'Quebra da bomba hidráulica');
INSERT INTO public.registrar_problema (id, ordem_producao_id, tipo_problema_id, quantidade_afetada, data_problema, observacoes) VALUES (4, 4, 1, 20, '2025-01-15 00:00:00', 'Ataque de lagartas');
INSERT INTO public.registrar_problema (id, ordem_producao_id, tipo_problema_id, quantidade_afetada, data_problema, observacoes) VALUES (5, 5, 5, 5, '2025-01-18 00:00:00', 'Problema não identificado');


--
-- TOC entry 5340 (class 0 OID 16476)
-- Dependencies: 238
-- Data for Name: status_pedido; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.status_pedido (id, descricao) VALUES (1, 'Aberto');
INSERT INTO public.status_pedido (id, descricao) VALUES (2, 'Concluído');
INSERT INTO public.status_pedido (id, descricao) VALUES (3, 'Cancelado');


--
-- TOC entry 5361 (class 0 OID 16569)
-- Dependencies: 259
-- Data for Name: talhao; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.talhao (id, area_id, nome, area_talhao, observacoes, status) VALUES (1, 1, 'Talhão A1', 5.2, 'Solo argiloso, boa drenagem', 'Ativo');
INSERT INTO public.talhao (id, area_id, nome, area_talhao, observacoes, status) VALUES (2, 1, 'Talhão A2', 4.8, 'Próximo ao rio, umidade alta', 'Ativo');
INSERT INTO public.talhao (id, area_id, nome, area_talhao, observacoes, status) VALUES (3, 2, 'Talhão B1', 3.5, 'Inclinação moderada', 'Inativo');
INSERT INTO public.talhao (id, area_id, nome, area_talhao, observacoes, status) VALUES (4, 3, 'Talhão C1', 6.1, 'Solo arenoso, precisa adubação', 'Ativo');
INSERT INTO public.talhao (id, area_id, nome, area_talhao, observacoes, status) VALUES (5, 4, 'Talhão D1', 5.7, 'Área plana, irrigação por gotejamento', 'Ativo');


--
-- TOC entry 5385 (class 0 OID 16679)
-- Dependencies: 283
-- Data for Name: tipo; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tipo (id, nome) VALUES (1, 'Entrada (Colheita)');
INSERT INTO public.tipo (id, nome) VALUES (2, 'Saída (Venda)');
INSERT INTO public.tipo (id, nome) VALUES (3, 'Perda (Descarte)');
INSERT INTO public.tipo (id, nome) VALUES (4, 'Ajuste (Balanço)');
INSERT INTO public.tipo (id, nome) VALUES (5, 'Transferência Interna');


--
-- TOC entry 5357 (class 0 OID 16551)
-- Dependencies: 255
-- Data for Name: tipo_problema; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tipo_problema (id, descricao) VALUES (1, 'Praga');
INSERT INTO public.tipo_problema (id, descricao) VALUES (2, 'Doença');
INSERT INTO public.tipo_problema (id, descricao) VALUES (3, 'Falha de irrigação');
INSERT INTO public.tipo_problema (id, descricao) VALUES (4, 'Clima adverso');
INSERT INTO public.tipo_problema (id, descricao) VALUES (5, 'Outro');


--
-- TOC entry 5342 (class 0 OID 16484)
-- Dependencies: 240
-- Data for Name: venda; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (1, 1, 1, 1, '2025-07-01 00:00:00', 120.00, '2025-07-03 00:00:00', 2);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (2, 1, 2, 2, '2025-07-02 00:00:00', 75.50, '2025-07-04 00:00:00', 3);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (3, 2, 1, 3, '2025-07-05 00:00:00', 210.99, '2025-07-06 00:00:00', 5);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (4, 2, 3, 4, '2025-07-06 00:00:00', 49.90, '2025-07-08 00:00:00', 3);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (5, 3, 4, 5, '2025-07-07 00:00:00', 60.00, '2025-07-10 00:00:00', 3);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (6, 4, 1, 6, '2025-07-08 00:00:00', 330.00, '2025-07-09 00:00:00', 2);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (7, 5, 5, 7, '2025-07-09 00:00:00', 85.00, '2025-07-11 00:00:00', 3);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (8, 6, 2, 8, '2025-07-10 00:00:00', 150.00, '2025-07-12 00:00:00', 2);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (9, 7, 1, 9, '2025-07-11 00:00:00', 99.90, '2025-07-13 00:00:00', 4);
INSERT INTO public.venda (id, projeto_id, associado_id, pedido_id, data_compra, valor_total, data_entrega, forma_pagamento_id) VALUES (10, 8, 1, 10, '2025-07-12 00:00:00', 199.90, '2025-07-14 00:00:00', 3);


--
-- TOC entry 5428 (class 0 OID 0)
-- Dependencies: 256
-- Name: area_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.area_id_seq', 5, true);


--
-- TOC entry 5429 (class 0 OID 0)
-- Dependencies: 276
-- Name: armazem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.armazem_id_seq', 1, false);


--
-- TOC entry 5430 (class 0 OID 0)
-- Dependencies: 225
-- Name: associado_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.associado_id_seq', 1, false);


--
-- TOC entry 5431 (class 0 OID 0)
-- Dependencies: 245
-- Name: associado_item_pedido_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.associado_item_pedido_id_seq', 1, false);


--
-- TOC entry 5432 (class 0 OID 0)
-- Dependencies: 264
-- Name: atividade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.atividade_id_seq', 5, true);


--
-- TOC entry 5433 (class 0 OID 0)
-- Dependencies: 262
-- Name: canteiro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.canteiro_id_seq', 5, true);


--
-- TOC entry 5434 (class 0 OID 0)
-- Dependencies: 270
-- Name: categoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.categoria_id_seq', 1, false);


--
-- TOC entry 5435 (class 0 OID 0)
-- Dependencies: 223
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.cliente_id_seq', 1, false);


--
-- TOC entry 5436 (class 0 OID 0)
-- Dependencies: 219
-- Name: despesa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.despesa_id_seq', 1, false);


--
-- TOC entry 5437 (class 0 OID 0)
-- Dependencies: 221
-- Name: endereco_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.endereco_id_seq', 1, false);


--
-- TOC entry 5438 (class 0 OID 0)
-- Dependencies: 272
-- Name: especie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.especie_id_seq', 1, false);


--
-- TOC entry 5439 (class 0 OID 0)
-- Dependencies: 278
-- Name: estoque_atual_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.estoque_atual_id_seq', 5, true);


--
-- TOC entry 5440 (class 0 OID 0)
-- Dependencies: 241
-- Name: forma_pagamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.forma_pagamento_id_seq', 1, false);


--
-- TOC entry 5441 (class 0 OID 0)
-- Dependencies: 243
-- Name: item_pedido_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.item_pedido_id_seq', 1, false);


--
-- TOC entry 5442 (class 0 OID 0)
-- Dependencies: 267
-- Name: material_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.material_id_seq', 5, true);


--
-- TOC entry 5443 (class 0 OID 0)
-- Dependencies: 286
-- Name: movimentacao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.movimentacao_id_seq', 1, false);


--
-- TOC entry 5444 (class 0 OID 0)
-- Dependencies: 227
-- Name: nota_fiscal_eletronica_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.nota_fiscal_eletronica_id_seq', 1, false);


--
-- TOC entry 5445 (class 0 OID 0)
-- Dependencies: 229
-- Name: nota_fiscal_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.nota_fiscal_item_id_seq', 1, false);


--
-- TOC entry 5446 (class 0 OID 0)
-- Dependencies: 231
-- Name: nota_fiscal_xml_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.nota_fiscal_xml_id_seq', 1, false);


--
-- TOC entry 5447 (class 0 OID 0)
-- Dependencies: 250
-- Name: ordem_producao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.ordem_producao_id_seq', 1, false);


--
-- TOC entry 5448 (class 0 OID 0)
-- Dependencies: 284
-- Name: origem_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.origem_id_seq', 1, false);


--
-- TOC entry 5449 (class 0 OID 0)
-- Dependencies: 235
-- Name: pedido_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.pedido_id_seq', 1, false);


--
-- TOC entry 5450 (class 0 OID 0)
-- Dependencies: 247
-- Name: plano_especie_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.plano_especie_id_seq', 1, false);


--
-- TOC entry 5451 (class 0 OID 0)
-- Dependencies: 260
-- Name: plano_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.plano_id_seq', 5, true);


--
-- TOC entry 5452 (class 0 OID 0)
-- Dependencies: 280
-- Name: preco_ppa_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.preco_ppa_id_seq', 5, true);


--
-- TOC entry 5453 (class 0 OID 0)
-- Dependencies: 274
-- Name: produto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.produto_id_seq', 1, false);


--
-- TOC entry 5454 (class 0 OID 0)
-- Dependencies: 233
-- Name: projeto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.projeto_id_seq', 11, true);


--
-- TOC entry 5455 (class 0 OID 0)
-- Dependencies: 252
-- Name: registrar_problema_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.registrar_problema_id_seq', 1, false);


--
-- TOC entry 5456 (class 0 OID 0)
-- Dependencies: 237
-- Name: status_pedido_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.status_pedido_id_seq', 1, false);


--
-- TOC entry 5457 (class 0 OID 0)
-- Dependencies: 258
-- Name: talhao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.talhao_id_seq', 5, true);


--
-- TOC entry 5458 (class 0 OID 0)
-- Dependencies: 282
-- Name: tipo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tipo_id_seq', 1, false);


--
-- TOC entry 5459 (class 0 OID 0)
-- Dependencies: 254
-- Name: tipo_problema_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tipo_problema_id_seq', 1, false);


--
-- TOC entry 5460 (class 0 OID 0)
-- Dependencies: 239
-- Name: venda_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.venda_id_seq', 1, false);


--
-- TOC entry 5098 (class 2606 OID 16567)
-- Name: area area_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.area
    ADD CONSTRAINT area_pkey PRIMARY KEY (id);


--
-- TOC entry 5120 (class 2606 OID 16661)
-- Name: armazem armazem_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.armazem
    ADD CONSTRAINT armazem_pkey PRIMARY KEY (id);


--
-- TOC entry 5088 (class 2606 OID 16520)
-- Name: associado_item_pedido associado_item_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado_item_pedido
    ADD CONSTRAINT associado_item_pedido_pkey PRIMARY KEY (id);


--
-- TOC entry 5068 (class 2606 OID 16426)
-- Name: associado associado_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado
    ADD CONSTRAINT associado_pkey PRIMARY KEY (id);


--
-- TOC entry 5106 (class 2606 OID 16607)
-- Name: atividade atividade_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade
    ADD CONSTRAINT atividade_pkey PRIMARY KEY (id);


--
-- TOC entry 5104 (class 2606 OID 16597)
-- Name: canteiro canteiro_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.canteiro
    ADD CONSTRAINT canteiro_pkey PRIMARY KEY (id);


--
-- TOC entry 5114 (class 2606 OID 16635)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 5066 (class 2606 OID 16416)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 5062 (class 2606 OID 16396)
-- Name: despesa despesa_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT despesa_pkey PRIMARY KEY (id);


--
-- TOC entry 5064 (class 2606 OID 16406)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 5116 (class 2606 OID 16645)
-- Name: especie especie_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.especie
    ADD CONSTRAINT especie_pkey PRIMARY KEY (id);


--
-- TOC entry 5122 (class 2606 OID 16669)
-- Name: estoque_atual estoque_atual_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.estoque_atual
    ADD CONSTRAINT estoque_atual_pkey PRIMARY KEY (id);


--
-- TOC entry 5084 (class 2606 OID 16500)
-- Name: forma_pagamento forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.forma_pagamento
    ADD CONSTRAINT forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 5086 (class 2606 OID 16510)
-- Name: item_pedido item_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT item_pedido_pkey PRIMARY KEY (id);


--
-- TOC entry 5110 (class 2606 OID 16622)
-- Name: material material_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_pkey PRIMARY KEY (id);


--
-- TOC entry 5130 (class 2606 OID 16701)
-- Name: movimentacao movimentacao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT movimentacao_pkey PRIMARY KEY (id);


--
-- TOC entry 5070 (class 2606 OID 16436)
-- Name: nota_fiscal_eletronica nota_fiscal_eletronica_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_eletronica
    ADD CONSTRAINT nota_fiscal_eletronica_pkey PRIMARY KEY (id);


--
-- TOC entry 5072 (class 2606 OID 16444)
-- Name: nota_fiscal_item nota_fiscal_item_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_item
    ADD CONSTRAINT nota_fiscal_item_pkey PRIMARY KEY (id);


--
-- TOC entry 5074 (class 2606 OID 16454)
-- Name: nota_fiscal_xml nota_fiscal_xml_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_xml
    ADD CONSTRAINT nota_fiscal_xml_pkey PRIMARY KEY (id);


--
-- TOC entry 5092 (class 2606 OID 16541)
-- Name: ordem_producao ordem_producao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ordem_producao
    ADD CONSTRAINT ordem_producao_pkey PRIMARY KEY (id);


--
-- TOC entry 5128 (class 2606 OID 16693)
-- Name: origem origem_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.origem
    ADD CONSTRAINT origem_pkey PRIMARY KEY (id);


--
-- TOC entry 5078 (class 2606 OID 16474)
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (id);


--
-- TOC entry 5112 (class 2606 OID 16764)
-- Name: atividade_has_material pk_atividade_material; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade_has_material
    ADD CONSTRAINT pk_atividade_material PRIMARY KEY (material_id, atividade_id);


--
-- TOC entry 5108 (class 2606 OID 16760)
-- Name: atividade_canteiro pk_canteiro_atividade; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade_canteiro
    ADD CONSTRAINT pk_canteiro_atividade PRIMARY KEY (canteiro_id, atividade_id);


--
-- TOC entry 5090 (class 2606 OID 16528)
-- Name: plano_especie plano_especie_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano_especie
    ADD CONSTRAINT plano_especie_pkey PRIMARY KEY (id);


--
-- TOC entry 5102 (class 2606 OID 16587)
-- Name: plano plano_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano
    ADD CONSTRAINT plano_pkey PRIMARY KEY (id);


--
-- TOC entry 5124 (class 2606 OID 16677)
-- Name: preco_ppa preco_ppa_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.preco_ppa
    ADD CONSTRAINT preco_ppa_pkey PRIMARY KEY (id);


--
-- TOC entry 5118 (class 2606 OID 16653)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 5076 (class 2606 OID 16464)
-- Name: projeto projeto_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.projeto
    ADD CONSTRAINT projeto_pkey PRIMARY KEY (id);


--
-- TOC entry 5094 (class 2606 OID 16549)
-- Name: registrar_problema registrar_problema_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.registrar_problema
    ADD CONSTRAINT registrar_problema_pkey PRIMARY KEY (id);


--
-- TOC entry 5080 (class 2606 OID 16482)
-- Name: status_pedido status_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.status_pedido
    ADD CONSTRAINT status_pedido_pkey PRIMARY KEY (id);


--
-- TOC entry 5100 (class 2606 OID 16577)
-- Name: talhao talhao_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.talhao
    ADD CONSTRAINT talhao_pkey PRIMARY KEY (id);


--
-- TOC entry 5126 (class 2606 OID 16685)
-- Name: tipo tipo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipo
    ADD CONSTRAINT tipo_pkey PRIMARY KEY (id);


--
-- TOC entry 5096 (class 2606 OID 16557)
-- Name: tipo_problema tipo_problema_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tipo_problema
    ADD CONSTRAINT tipo_problema_pkey PRIMARY KEY (id);


--
-- TOC entry 5082 (class 2606 OID 16492)
-- Name: venda venda_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT venda_pkey PRIMARY KEY (id);


--
-- TOC entry 5154 (class 2606 OID 16765)
-- Name: area fk_area_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.area
    ADD CONSTRAINT fk_area_associado FOREIGN KEY (associado_id) REFERENCES public.associado(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5131 (class 2606 OID 16703)
-- Name: despesa fk_associado_despesa; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.despesa
    ADD CONSTRAINT fk_associado_despesa FOREIGN KEY (associado_id) REFERENCES public.associado(id);


--
-- TOC entry 5145 (class 2606 OID 16752)
-- Name: associado_item_pedido fk_associado_item_pedido_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado_item_pedido
    ADD CONSTRAINT fk_associado_item_pedido_associado FOREIGN KEY (associado_id) REFERENCES public.associado(id);


--
-- TOC entry 5146 (class 2606 OID 16747)
-- Name: associado_item_pedido fk_associado_item_pedido_item_pedido; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado_item_pedido
    ADD CONSTRAINT fk_associado_item_pedido_item_pedido FOREIGN KEY (item_pedido_id) REFERENCES public.item_pedido(id);


--
-- TOC entry 5134 (class 2606 OID 16910)
-- Name: nota_fiscal_eletronica fk_associado_nota_fiscal_eletronica; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_eletronica
    ADD CONSTRAINT fk_associado_nota_fiscal_eletronica FOREIGN KEY (associado_id) REFERENCES public.associado(id);


--
-- TOC entry 5162 (class 2606 OID 16810)
-- Name: atividade_has_material fk_atividade_material_atividade; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade_has_material
    ADD CONSTRAINT fk_atividade_material_atividade FOREIGN KEY (atividade_id) REFERENCES public.atividade(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5163 (class 2606 OID 16805)
-- Name: atividade_has_material fk_atividade_material_material; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade_has_material
    ADD CONSTRAINT fk_atividade_material_material FOREIGN KEY (material_id) REFERENCES public.material(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5159 (class 2606 OID 16795)
-- Name: atividade_canteiro fk_canteiro_atividade_atividade; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade_canteiro
    ADD CONSTRAINT fk_canteiro_atividade_atividade FOREIGN KEY (atividade_id) REFERENCES public.atividade(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5160 (class 2606 OID 16790)
-- Name: atividade_canteiro fk_canteiro_atividade_canteiro; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.atividade_canteiro
    ADD CONSTRAINT fk_canteiro_atividade_canteiro FOREIGN KEY (canteiro_id) REFERENCES public.canteiro(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5158 (class 2606 OID 16785)
-- Name: canteiro fk_canteiro_plano; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.canteiro
    ADD CONSTRAINT fk_canteiro_plano FOREIGN KEY (plano_id) REFERENCES public.plano(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5133 (class 2606 OID 16905)
-- Name: associado fk_endereco_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.associado
    ADD CONSTRAINT fk_endereco_associado FOREIGN KEY (endereco_id) REFERENCES public.endereco(id);


--
-- TOC entry 5132 (class 2606 OID 16900)
-- Name: cliente fk_endereco_cliente; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT fk_endereco_cliente FOREIGN KEY (endereco_id) REFERENCES public.endereco(id);


--
-- TOC entry 5164 (class 2606 OID 16815)
-- Name: especie fk_especie_categoria; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.especie
    ADD CONSTRAINT fk_especie_categoria FOREIGN KEY (categoria_id) REFERENCES public.categoria(id);


--
-- TOC entry 5166 (class 2606 OID 16835)
-- Name: estoque_atual fk_estoque_armazem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.estoque_atual
    ADD CONSTRAINT fk_estoque_armazem FOREIGN KEY (armazem_id) REFERENCES public.armazem(id);


--
-- TOC entry 5167 (class 2606 OID 16825)
-- Name: estoque_atual fk_estoque_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.estoque_atual
    ADD CONSTRAINT fk_estoque_associado FOREIGN KEY (associado_id) REFERENCES public.associado(id);


--
-- TOC entry 5168 (class 2606 OID 16830)
-- Name: estoque_atual fk_estoque_produto; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.estoque_atual
    ADD CONSTRAINT fk_estoque_produto FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 5143 (class 2606 OID 16737)
-- Name: item_pedido fk_item_pedido_pedido; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT fk_item_pedido_pedido FOREIGN KEY (pedido_id) REFERENCES public.pedido(id);


--
-- TOC entry 5144 (class 2606 OID 16742)
-- Name: item_pedido fk_item_pedido_produto; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.item_pedido
    ADD CONSTRAINT fk_item_pedido_produto FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 5161 (class 2606 OID 16800)
-- Name: material fk_material_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT fk_material_associado FOREIGN KEY (associado_id) REFERENCES public.associado(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5170 (class 2606 OID 16865)
-- Name: movimentacao fk_movimentacao_armazem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT fk_movimentacao_armazem FOREIGN KEY (armazem_id) REFERENCES public.armazem(id);


--
-- TOC entry 5171 (class 2606 OID 16855)
-- Name: movimentacao fk_movimentacao_origem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT fk_movimentacao_origem FOREIGN KEY (origem_id) REFERENCES public.origem(id);


--
-- TOC entry 5172 (class 2606 OID 16860)
-- Name: movimentacao fk_movimentacao_produto; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT fk_movimentacao_produto FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 5173 (class 2606 OID 16850)
-- Name: movimentacao fk_movimentacao_tipo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.movimentacao
    ADD CONSTRAINT fk_movimentacao_tipo FOREIGN KEY (tipo_id) REFERENCES public.tipo(id);


--
-- TOC entry 5135 (class 2606 OID 16915)
-- Name: nota_fiscal_item fk_nota_fiscal_eletronica_nota_fiscal_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_item
    ADD CONSTRAINT fk_nota_fiscal_eletronica_nota_fiscal_item FOREIGN KEY (nota_fiscal_eletronica_id) REFERENCES public.nota_fiscal_eletronica(id);


--
-- TOC entry 5137 (class 2606 OID 16925)
-- Name: nota_fiscal_xml fk_nota_fiscal_eletronica_nota_fiscal_xml; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_xml
    ADD CONSTRAINT fk_nota_fiscal_eletronica_nota_fiscal_xml FOREIGN KEY (id) REFERENCES public.nota_fiscal_eletronica(id);


--
-- TOC entry 5151 (class 2606 OID 16885)
-- Name: ordem_producao fk_ordem_producao_plano; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.ordem_producao
    ADD CONSTRAINT fk_ordem_producao_plano FOREIGN KEY (plano_id) REFERENCES public.plano(id);


--
-- TOC entry 5138 (class 2606 OID 16717)
-- Name: pedido fk_pedido_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fk_pedido_associado FOREIGN KEY (associado_id) REFERENCES public.associado(id);


--
-- TOC entry 5139 (class 2606 OID 16712)
-- Name: pedido fk_pedido_projeto; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT fk_pedido_projeto FOREIGN KEY (projeto_id) REFERENCES public.projeto(id);


--
-- TOC entry 5149 (class 2606 OID 16880)
-- Name: plano_especie_has_atividade fk_plano_atividade_atividade; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano_especie_has_atividade
    ADD CONSTRAINT fk_plano_atividade_atividade FOREIGN KEY (atividade_id) REFERENCES public.atividade(id);


--
-- TOC entry 5150 (class 2606 OID 16875)
-- Name: plano_especie_has_atividade fk_plano_atividade_especie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano_especie_has_atividade
    ADD CONSTRAINT fk_plano_atividade_especie FOREIGN KEY (plano_especie_id) REFERENCES public.plano_especie(id);


--
-- TOC entry 5156 (class 2606 OID 16780)
-- Name: plano fk_plano_especie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano
    ADD CONSTRAINT fk_plano_especie FOREIGN KEY (especie_id) REFERENCES public.especie(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5147 (class 2606 OID 16870)
-- Name: plano_especie fk_plano_especie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano_especie
    ADD CONSTRAINT fk_plano_especie FOREIGN KEY (especie_id) REFERENCES public.especie(id);


--
-- TOC entry 5148 (class 2606 OID 16840)
-- Name: plano_especie fk_plano_especie_especie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano_especie
    ADD CONSTRAINT fk_plano_especie_especie FOREIGN KEY (especie_id) REFERENCES public.especie(id);


--
-- TOC entry 5157 (class 2606 OID 16775)
-- Name: plano fk_plano_talhao; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.plano
    ADD CONSTRAINT fk_plano_talhao FOREIGN KEY (talhao_id) REFERENCES public.talhao(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5169 (class 2606 OID 16845)
-- Name: preco_ppa fk_preco_ppa_especie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.preco_ppa
    ADD CONSTRAINT fk_preco_ppa_especie FOREIGN KEY (especie_id) REFERENCES public.especie(id);


--
-- TOC entry 5165 (class 2606 OID 16820)
-- Name: produto fk_produto_especie; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT fk_produto_especie FOREIGN KEY (especie_id) REFERENCES public.especie(id);


--
-- TOC entry 5136 (class 2606 OID 16920)
-- Name: nota_fiscal_item fk_produto_nota_fiscal_item; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.nota_fiscal_item
    ADD CONSTRAINT fk_produto_nota_fiscal_item FOREIGN KEY (produto_id) REFERENCES public.produto(id);


--
-- TOC entry 5152 (class 2606 OID 16890)
-- Name: registrar_problema fk_registrar_problema_ordem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.registrar_problema
    ADD CONSTRAINT fk_registrar_problema_ordem FOREIGN KEY (ordem_producao_id) REFERENCES public.ordem_producao(id);


--
-- TOC entry 5153 (class 2606 OID 16895)
-- Name: registrar_problema fk_registrar_problema_tipo; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.registrar_problema
    ADD CONSTRAINT fk_registrar_problema_tipo FOREIGN KEY (tipo_problema_id) REFERENCES public.tipo_problema(id);


--
-- TOC entry 5155 (class 2606 OID 16770)
-- Name: talhao fk_talhao_area; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.talhao
    ADD CONSTRAINT fk_talhao_area FOREIGN KEY (area_id) REFERENCES public.area(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 5140 (class 2606 OID 16732)
-- Name: venda fk_venda_associado; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT fk_venda_associado FOREIGN KEY (associado_id) REFERENCES public.associado(id);


--
-- TOC entry 5141 (class 2606 OID 16722)
-- Name: venda fk_venda_pedido; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT fk_venda_pedido FOREIGN KEY (pedido_id) REFERENCES public.pedido(id);


--
-- TOC entry 5142 (class 2606 OID 16727)
-- Name: venda fk_venda_projeto; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.venda
    ADD CONSTRAINT fk_venda_projeto FOREIGN KEY (projeto_id) REFERENCES public.projeto(id);


-- Completed on 2025-11-17 18:41:45

--
-- PostgreSQL database dump complete
--

\unrestrict 5bBjahkqJIa2TwZPh2j65653asng518cmdMvPRCz7uJeTYhzUSvsPRfbdMC3NtX

