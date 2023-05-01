--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- Name: books; Type: TABLE; Schema: public; Owner: a1
--

CREATE TABLE public.books (
    id integer NOT NULL,
    title text NOT NULL,
    author text DEFAULT 'Неизвестен'::text NOT NULL,
    active boolean DEFAULT true
);


-- ALTER TABLE public.books OWNER TO a1;

--
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: a1
--

CREATE SEQUENCE public.books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.books_id_seq OWNER TO a1;

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: a1
--

ALTER SEQUENCE public.books_id_seq OWNED BY public.books.id;


--
-- Name: books_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.books_people (
    id integer NOT NULL,
    id_books integer,
    id_people integer
);


ALTER TABLE public.books_people OWNER TO postgres;

--
-- Name: books_people_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.books_people_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.books_people_id_seq OWNER TO postgres;

--
-- Name: books_people_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.books_people_id_seq OWNED BY public.books_people.id;


--
-- Name: bookspeople; Type: TABLE; Schema: public; Owner: a1
--

CREATE TABLE public.bookspeople (
    id integer NOT NULL,
    id_people integer,
    id_books integer,
    active boolean DEFAULT true
);


-- ALTER TABLE public.bookspeople OWNER TO a1;

--
-- Name: bookspeople_id_seq; Type: SEQUENCE; Schema: public; Owner: a1
--

CREATE SEQUENCE public.bookspeople_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.bookspeople_id_seq OWNER TO a1;

--
-- Name: bookspeople_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: a1
--

ALTER SEQUENCE public.bookspeople_id_seq OWNED BY public.bookspeople.id;


--
-- Name: eco1; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eco1 (
    id integer NOT NULL,
    region text NOT NULL
);


ALTER TABLE public.eco1 OWNER TO postgres;

--
-- Name: eco1_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.eco1_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.eco1_id_seq OWNER TO postgres;

--
-- Name: eco1_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.eco1_id_seq OWNED BY public.eco1.id;


--
-- Name: eco2; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.eco2 (
    id integer NOT NULL,
    level text NOT NULL
);


ALTER TABLE public.eco2 OWNER TO postgres;

--
-- Name: eco2_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.eco2_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.eco2_id_seq OWNER TO postgres;

--
-- Name: eco2_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.eco2_id_seq OWNED BY public.eco2.id;


--
-- Name: people; Type: TABLE; Schema: public; Owner: a1
--

CREATE TABLE public.people (
    id integer NOT NULL,
    lastname text NOT NULL,
    firstname text NOT NULL,
    patronymic text DEFAULT 'Нет'::text,
    active boolean DEFAULT true
);


-- ALTER TABLE public.people OWNER TO a1;

--
-- Name: peoples_id_seq; Type: SEQUENCE; Schema: public; Owner: a1
--

CREATE SEQUENCE public.peoples_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.peoples_id_seq OWNER TO a1;

--
-- Name: peoples_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: a1
--

ALTER SEQUENCE public.peoples_id_seq OWNED BY public.people.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    id integer NOT NULL,
    role_ text NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: a1
--

CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(40) NOT NULL,
    login character varying(40) NOT NULL,
    password_ text NOT NULL,
    role_ text,
    active boolean DEFAULT true
);


-- ALTER TABLE public.users OWNER TO a1;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: a1
--

CREATE SEQUENCE public.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


-- ALTER TABLE public.users_id_seq OWNER TO a1;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: a1
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: users_people; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_people (
    id integer NOT NULL,
    email text NOT NULL,
    pass text NOT NULL,
    rule text NOT NULL
);


ALTER TABLE public.users_people OWNER TO postgres;

--
-- Name: users_people_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_people_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_people_id_seq OWNER TO postgres;

--
-- Name: users_people_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_people_id_seq OWNED BY public.users_people.id;


--
-- Name: books id; Type: DEFAULT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.books ALTER COLUMN id SET DEFAULT nextval('public.books_id_seq'::regclass);


--
-- Name: books_people id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books_people ALTER COLUMN id SET DEFAULT nextval('public.books_people_id_seq'::regclass);


--
-- Name: bookspeople id; Type: DEFAULT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.bookspeople ALTER COLUMN id SET DEFAULT nextval('public.bookspeople_id_seq'::regclass);


--
-- Name: eco1 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eco1 ALTER COLUMN id SET DEFAULT nextval('public.eco1_id_seq'::regclass);


--
-- Name: eco2 id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eco2 ALTER COLUMN id SET DEFAULT nextval('public.eco2_id_seq'::regclass);


--
-- Name: people id; Type: DEFAULT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.people ALTER COLUMN id SET DEFAULT nextval('public.peoples_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: users_people id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_people ALTER COLUMN id SET DEFAULT nextval('public.users_people_id_seq'::regclass);


--
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: a1
--

COPY public.books (id, title, author, active) FROM stdin;
1	Золотая Рыбка	А.С.Пушкин	t
2	Война и Мир	Л.Н.Толстой	t
5	Колобок	Неизвестен	t
7	Лебедь, рак и щука	Крылов	t
8	Упырь	Кто-то	t
9	Кот в сапогах	Шрек	t
6	Белые ночи	Достоевский Ф.М.	t
3	Мцыри	М.Ю.Лермонтов	t
10	Упырь	Кто-то	t
\.


--
-- Data for Name: books_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.books_people (id, id_books, id_people) FROM stdin;
\.


--
-- Data for Name: bookspeople; Type: TABLE DATA; Schema: public; Owner: a1
--

COPY public.bookspeople (id, id_people, id_books, active) FROM stdin;
2	1	3	t
12	3	5	f
13	\N	\N	f
14	\N	\N	f
15	3	1	f
16	\N	\N	f
17	\N	\N	f
18	2	3	f
19	3	3	t
20	4	6	t
6	6	6	f
3	3	3	t
21	2	5	f
22	3	9	t
9	9	9	t
23	9	7	t
24	7	7	f
26	7	7	f
25	7	7	f
27	8	8	t
28	8	8	f
29	8	8	f
\.


--
-- Data for Name: eco1; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.eco1 (id, region) FROM stdin;
2	msk
3	msk
5	Tatarstan
6	Tatarstan
7	Tatarstan
8	Tatarstan
1	no msk
9	Камень
10	Камень
4	444
\.


--
-- Data for Name: eco2; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.eco2 (id, level) FROM stdin;
3	1
4	183
5	Save
6	del
7	Save
8	del
9	Up
1	Up
\.


--
-- Data for Name: people; Type: TABLE DATA; Schema: public; Owner: a1
--

COPY public.people (id, lastname, firstname, patronymic, active) FROM stdin;
1	Жирнов	Алексей	Евгеньевич	t
2	Желонкина	Марина	Андреевна	t
4	Муркина	Светлана	Анатольевна	t
5	Муркина	Светлана	Анатольевна	t
6	Муркина	Светлана	Анатольевна	t
3	Жирнов	Алексей	Евгеньевич	t
7	Аннa	Комарова	Нет	t
10	Балбес	Бал	Бес	t
8	Балбес	Бал	Бес	t
9	Анна	Комарова	Нет	t
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (id, role_) FROM stdin;
1	admin
2	user
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: a1
--

COPY public.users (id, username, login, password_, role_, active) FROM stdin;
1	probnik	weww1	a123	user	t
3	джони депп	11ква	1234	user	t
0	balon	balon	balon	user	t
4	prova	jiji	111	user	t
5	admin	admin_god	111	admin	t
6	don	don	don	user	t
19	kim	kim	kim	user	t
20	Moli	moli__	09	user	t
21	mool	mooli	mm	user	t
22	miii	ninim	11	user	t
23	fifi	fifi	11	user	t
26	ququq	ququq	111	user	t
27	loooo	looo	looo	user	t
7	grisha	grisha	44	user	t
8	dima	dima	44	user	t
9	los	los	los	user	t
18	lol	looool	lol	user	t
12	11	11	11	user	t
14	monika	monika	13	user	t
11	slo	slo	slo	user	t
16	zzzlo	zzzzzlo	lo	user	t
17	gnom	gnom	321	user	t
10	grinya	gbr17	2023	admin	t
28	Dominic	DomToretto	family	user	t
29	username	login	pass	user	t
30	Milisa	milisa	moon	user	t
31	Korgi	korgi	$2a$12$chqSWTOueM/Vpe.4IfQ7quTMv/GOa6bBhjNTt7BqPanqvpSmS7ISu	user	t
32	Gena_boss	boss	$2a$12$mytd9S8F4YOq4l0IcdmVNui3wmoq3WTlxz0eKg.GJTFoyEzuuIicy	admin	t
\.


--
-- Data for Name: users_people; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_people (id, email, pass, rule) FROM stdin;
1	user@user	user	user
2	darlin@mail.ru	dasha	user
3	admin@admin	admin	admin
4	kapriza17@bk.ru	1221	user
\.


--
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: a1
--

SELECT pg_catalog.setval('public.books_id_seq', 10, true);


--
-- Name: books_people_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.books_people_id_seq', 1, false);


--
-- Name: bookspeople_id_seq; Type: SEQUENCE SET; Schema: public; Owner: a1
--

SELECT pg_catalog.setval('public.bookspeople_id_seq', 29, true);


--
-- Name: eco1_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.eco1_id_seq', 11, true);


--
-- Name: eco2_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.eco2_id_seq', 9, true);


--
-- Name: peoples_id_seq; Type: SEQUENCE SET; Schema: public; Owner: a1
--

SELECT pg_catalog.setval('public.peoples_id_seq', 10, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: a1
--

SELECT pg_catalog.setval('public.users_id_seq', 32, true);


--
-- Name: users_people_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_people_id_seq', 4, true);


--
-- Name: books_people books_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.books_people
    ADD CONSTRAINT books_people_pkey PRIMARY KEY (id);


--
-- Name: books books_pkey; Type: CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: bookspeople bookspeople_pkey; Type: CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.bookspeople
    ADD CONSTRAINT bookspeople_pkey PRIMARY KEY (id);


--
-- Name: eco1 eco1_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eco1
    ADD CONSTRAINT eco1_pkey PRIMARY KEY (id);


--
-- Name: eco2 eco2_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.eco2
    ADD CONSTRAINT eco2_pkey PRIMARY KEY (id);


--
-- Name: people peoples_pkey; Type: CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT peoples_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: roles roles_role__key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_role__key UNIQUE (role_);


--
-- Name: users users_login_key; Type: CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_login_key UNIQUE (login);


--
-- Name: users_people users_people_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_people
    ADD CONSTRAINT users_people_email_key UNIQUE (email);


--
-- Name: users_people users_people_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_people
    ADD CONSTRAINT users_people_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: bookspeople bookspeople_id_books_fkey; Type: FK CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.bookspeople
    ADD CONSTRAINT bookspeople_id_books_fkey FOREIGN KEY (id_books) REFERENCES public.books(id);


--
-- Name: bookspeople bookspeople_id_people_fkey; Type: FK CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.bookspeople
    ADD CONSTRAINT bookspeople_id_people_fkey FOREIGN KEY (id_people) REFERENCES public.people(id);


--
-- Name: users users_role__fkey; Type: FK CONSTRAINT; Schema: public; Owner: a1
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_role__fkey FOREIGN KEY (role_) REFERENCES public.roles(role_);


--
-- PostgreSQL database dump complete
--

